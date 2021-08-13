package com.vladimirbaklan


import com.vladimirbaklan.common.EntitiesConstants
import com.vladimirbaklan.eventsbus.{BaseEvent, EventProcessor, EventProducer, EventType, SagaType}
import com.vladimirbaklan.products.{ProductStatus, ProductsService}
import com.vladimirbaklan.utils.Logging

class ProductsEventProcessor(productsService: ProductsService, producer: EventProducer) extends EventProcessor with Logging {
  override def process(event: BaseEvent): Unit = {
    event.eventType match {
      case EventType.BuyProduct if event.attributes.contains(EntitiesConstants.ProductId) =>
        productsService.updateProductStatus(Integer.parseInt(event.attributes(EntitiesConstants.ProductId)), ProductStatus.SoldOut) match {
          case Right(_) =>
            producer.send(BaseEvent(
              eventType = EventType.BuyProductSuccess,
              attributes = Map(EntitiesConstants.ProductId -> event.attributes(EntitiesConstants.ProductId)),
              sagaType = SagaType.OrderSaga))
          case Left(error) =>
            producer.send(BaseEvent(
              eventType = EventType.BuyProductFailure,
              attributes = Map("reason" -> error.message, EntitiesConstants.ProductId -> event.attributes(EntitiesConstants.ProductId)),
              sagaType = SagaType.OrderSaga))
        }
      case EventType.RevertBuyProduct if event.attributes.contains(EntitiesConstants.ProductId) =>
        productsService.updateProductStatus(Integer.parseInt(event.attributes(EntitiesConstants.ProductId)), ProductStatus.Available) match {
          case Right(_) =>
            producer.send(BaseEvent(
              eventType = EventType.RevertBuyProductSuccess,
              attributes = Map(EntitiesConstants.ProductId -> event.attributes(EntitiesConstants.ProductId)),
              sagaType = SagaType.OrderSaga))
          case Left(error) =>
            producer.send(BaseEvent(
              eventType = EventType.RevertBuyProductFailure,
              attributes = Map("reason" -> error.message, EntitiesConstants.ProductId -> event.attributes(EntitiesConstants.ProductId)),
              sagaType = SagaType.OrderSaga))
        }
      case _ => log.warn(s"Invalid event received: $event")
    }
  }
}
