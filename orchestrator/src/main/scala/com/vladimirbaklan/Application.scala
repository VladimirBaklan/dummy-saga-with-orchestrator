package com.vladimirbaklan

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import com.vladimirbaklan.eventsbus.EventProducer.EventProducerProperties
import com.vladimirbaklan.eventsbus.EventsHandler.EventsHandlerProperties
import com.vladimirbaklan.eventsbus.{EventProcessor, EventProducer, EventsHandler}
import com.vladimirbaklan.sagas.{ProductOrderSaga, SagasFactory}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

object Application extends App {
  lazy val Config = ConfigFactory.load()
  lazy val EventBusConfig = Config.getConfig("eventbus")
  lazy val EventBusOrdersConfig = EventBusConfig.getConfig("orders")
  lazy val EventBusProductsConfig = EventBusConfig.getConfig("products")

  implicit val system: ActorSystem = ActorSystem.create("octopus")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  val bootstrapServers = EventBusConfig.getString("bootstrapServers")

  val productsProducerProps = EventProducerProperties(bootstrapServers = bootstrapServers, topic = EventBusProductsConfig.getString("topic"))
  val ordersProducerProps = EventProducerProperties(bootstrapServers = bootstrapServers, topic = EventBusOrdersConfig.getString("topic"))
  val consumerProps = EventsHandlerProperties(bootstrapServers = bootstrapServers, topic = EventBusConfig.getString("topic"), groupId = "mgid")

  val sagasFactory = new SagasFactory(
    productOrderSaga = new ProductOrderSaga(new EventProducer(productsProducerProps), new EventProducer(ordersProducerProps))
  )

  val handler = new EventsHandler(new OrchestratorEventProcessor(sagasFactory), consumerProps)
  handler.start
}
