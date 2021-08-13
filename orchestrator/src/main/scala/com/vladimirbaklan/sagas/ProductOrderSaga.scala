package com.vladimirbaklan.sagas

import com.vladimirbaklan.eventsbus.{BaseEvent, EventProducer, EventType, SagaType}
import com.vladimirbaklan.utils.Logging

/*
* Success flow: BUY PRODUCT -> CREATE ORDER
* Rollback flow: ROLLBACK ORDER -> ROLLBACK PRODUCT
* */

class ProductOrderSaga (productsProducer: EventProducer, ordersProducer: EventProducer) extends BaseSaga with Logging {

  override def start(event: BaseEvent): Unit = {
    productsProducer.send(BaseEvent(eventType = EventType.BuyProduct,  attributes = Map("productId" -> event.attributes("productId")), sagaType = SagaType.OrderSaga))
  }

  override def rollback(event: BaseEvent): Unit = {
    event.eventType match {
      case EventType.BuyProductFailure =>
        log.error("Failed to buy product")
      case EventType.CreateOrderFailure =>
        ordersProducer.send(BaseEvent(eventType = EventType.RevertBuyProduct, sagaType = SagaType.OrderSaga, attributes = Map("productId" -> event.attributes("productId"))))
    }
  }

  override def nextStep(event: BaseEvent): Unit = {
    event.eventType match {
      case EventType.BuyProductSuccess =>
        productsProducer.send(BaseEvent(eventType = EventType.CreateOrder, sagaType = SagaType.OrderSaga, attributes = Map("productId" -> event.attributes("productId"))))
      case EventType.CreateOrderSuccess =>
        log.info(s"Order successfully created. Event: $event")
    }
  }

  // It exists to make the flow easier. For the demo project I don't want to process it, so just log as error
  override def unprocessableFailures(event: BaseEvent): Unit = {
    log.error(s"UNPROCESSABLE_FAILURE - $event")
  }

  override def process(event: BaseEvent): Unit = {
    event.eventType match {
      case EventType.StartSaga => start(event)
      case EventType.BuyProductSuccess | EventType.CreateOrderSuccess => nextStep(event)
      case EventType.BuyProductFailure | EventType.CreateOrderFailure => rollback(event)
      case EventType.RevertBuyProductFailure | EventType.RevertCreateOrderFailure => unprocessableFailures(event)
    }
  }

}
