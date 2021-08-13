package com.vladimirbaklan.eventsbus

import com.vladimirbaklan.eventsbus.EventType.EventType
import com.vladimirbaklan.eventsbus.SagaType.SagaType

case class BaseEvent(eventType: EventType, sagaType: SagaType, attributes: Map[String, String])

object EventType extends Enumeration {
  type EventType = Value

  val DemoEvent: Value = Value("demo")
  val StartSaga: Value = Value("start_saga")

  // Products Service
  val BuyProduct: Value = Value("buy_product")
  val RevertBuyProduct: Value = Value("revert_buy_product")
  val BuyProductSuccess: Value = Value("buy_product_success")
  val BuyProductFailure: Value = Value("buy_product_failure")
  val RevertBuyProductSuccess: Value = Value("revert_buy_product_success")
  val RevertBuyProductFailure: Value = Value("revert_buy_product_failure")

  // Orders Service
  val CreateOrder: Value = Value("create_order")
  val RevertCreateOrder: Value = Value("revert_create_order")
  val CreateOrderSuccess: Value = Value("create_order_success")
  val CreateOrderFailure: Value = Value("create_order_failure")
  val RevertCreateOrderSuccess: Value = Value("revert_create_order_success")
  val RevertCreateOrderFailure: Value = Value("revert_create_order_failure")
}

object SagaType extends Enumeration {
  type SagaType = Value

  val OrderSaga: Value = Value("order_saga")
}
