package com.vladimirbaklan.eventsbus

import akka.Done
import akka.actor.ActorSystem
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.Materializer
import akka.stream.scaladsl.Sink
import com.vladimirbaklan.eventsbus.EventsHandler.EventsHandlerProperties
import com.vladimirbaklan.utils.{JsonUtils, Logging}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class EventsHandler(processor: EventProcessor, properties: EventsHandlerProperties)(implicit actorSystem: ActorSystem, materializer: Materializer, executionContext: ExecutionContext) extends Logging {

  private lazy val Settings = ConsumerSettings(actorSystem, new StringDeserializer, new StringDeserializer)
    .withBootstrapServers(properties.bootstrapServers)
    .withGroupId(properties.groupId)

  private lazy val Source = Consumer.committableSource(Settings, Subscriptions.topics(properties.topic))

  def start: Future[Done] = {
    log.info("Starting events handler")
    Source.map { message =>
      JsonUtils.tryFromJson[BaseEvent](message.record.value()) match {
        case Success(value) => processor.process(value)
        case Failure(exception) =>
          log.error(s"Can't parse event. Message: $message, exception: $exception")
      }
    }.runWith(Sink.ignore)
  }
}

object EventsHandler {

  case class EventsHandlerProperties(bootstrapServers: String, topic: String, groupId: String)

}