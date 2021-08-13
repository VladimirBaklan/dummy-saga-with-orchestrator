package com.vladimirbaklan

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import com.vladimirbaklan.database.DatabaseWiring
import com.vladimirbaklan.eventsbus.EventProducer.EventProducerProperties
import com.vladimirbaklan.eventsbus.{EventProducer, EventsHandler}
import com.vladimirbaklan.eventsbus.EventsHandler.EventsHandlerProperties
import com.vladimirbaklan.orders.{DefaultOrdersService, JooqOrdersDao}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class Application extends App {
  lazy val Config = ConfigFactory.load()
  lazy val EventBusConfig = Config.getConfig("eventbus")

  implicit val system: ActorSystem = ActorSystem.create("octopus")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  val ordersService = new DefaultOrdersService(new JooqOrdersDao(DatabaseWiring.Jooq))
  val bootstrapServers = EventBusConfig.getString("bootstrapServers")
  val topic = EventBusConfig.getString("topic")

  val producerProperties = EventProducerProperties(bootstrapServers = bootstrapServers, topic = topic)
  val consumerProperties = EventsHandlerProperties(bootstrapServers = bootstrapServers, topic = topic, groupId = "mgid")
  val handler = new EventsHandler(new OrdersEventProcessor(ordersService, new EventProducer(producerProperties)), consumerProperties)
  handler.start
}
