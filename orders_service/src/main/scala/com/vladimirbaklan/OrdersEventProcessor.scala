package com.vladimirbaklan

import com.vladimirbaklan.common.EntitiesConstants
import com.vladimirbaklan.eventsbus.{BaseEvent, EventProcessor, EventProducer, EventType, SagaType}
import com.vladimirbaklan.orders.{OrderStatus, OrdersService}
import com.vladimirbaklan.utils.Logging

class OrdersEventProcessor(ordersService: OrdersService, producer: EventProducer) extends EventProcessor with Logging {
  override def process(event: BaseEvent): Unit = {
    event.eventType match {
      case EventType.CreateOrder if event.attributes.contains(EntitiesConstants.ProductId) =>
        ordersService.createOrder(Integer.parseInt(event.attributes(EntitiesConstants.ProductId)), OrderStatus.Created) match {
          case Right(orderId) =>
            producer.send(BaseEvent(
              eventType = EventType.CreateOrderSuccess,
              attributes = Map(EntitiesConstants.OrderId -> orderId.toString, EntitiesConstants.ProductId -> event.attributes(EntitiesConstants.ProductId)),
              sagaType = SagaType.OrderSaga))
          case Left(error) =>
            producer.send(BaseEvent(
              eventType = EventType.CreateOrderFailure,
              attributes = Map("reason" -> error.message, EntitiesConstants.ProductId -> event.attributes(EntitiesConstants.ProductId)),
              sagaType = SagaType.OrderSaga))
        }
      case EventType.RevertCreateOrder if event.attributes.contains(EntitiesConstants.OrderId) =>
        ordersService.updateStatus(Integer.parseInt(event.attributes(EntitiesConstants.OrderId)), OrderStatus.Failed) match {
          case Right(_) =>
            producer.send(BaseEvent(
              eventType = EventType.RevertCreateOrderSuccess,
              attributes = Map(EntitiesConstants.OrderId -> event.attributes(EntitiesConstants.OrderId)),
              sagaType = SagaType.OrderSaga))
          case Left(error) =>
            producer.send(BaseEvent(
              eventType = EventType.RevertCreateOrderFailure,
              attributes = Map("reason" -> error.message, EntitiesConstants.OrderId -> event.attributes(EntitiesConstants.OrderId)),
              sagaType = SagaType.OrderSaga))
        }
      case _ => log.warn(s"Invalid event received: $event")
    }
  }
}
