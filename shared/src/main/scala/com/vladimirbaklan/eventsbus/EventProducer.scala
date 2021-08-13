package com.vladimirbaklan.eventsbus

import java.util.Properties
import java.util.concurrent.Future

import com.vladimirbaklan.eventsbus.EventProducer.EventProducerProperties
import com.vladimirbaklan.utils.JsonUtils
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer

class EventProducer (properties: EventProducerProperties) {
  private val KafkaProps = {
    val props = new Properties()
    props.put("bootstrap.servers", properties.bootstrapServers)
    props.put("key.serializer", classOf[StringSerializer].getName)
    props.put("value.serializer", classOf[StringSerializer].getName)
    props
  }

  private lazy val Producer = new KafkaProducer[String, String](KafkaProps)

  def send(event: BaseEvent): Future[RecordMetadata] = {
    Producer.send(new ProducerRecord[String, String](properties.topic, JsonUtils.toJson(event)))
  }
}

object EventProducer {
  case class EventProducerProperties(bootstrapServers: String, topic: String)
}