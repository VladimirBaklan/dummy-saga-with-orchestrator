package com.vladimirbaklan.sagas

import com.vladimirbaklan.eventsbus.{BaseEvent, SagaType}

class SagasFactory (productOrderSaga: ProductOrderSaga) {

  def processSagaMessage(event: BaseEvent): Unit = {
    event.sagaType match {
      case SagaType.OrderSaga => productOrderSaga.process(event)
    }
  }
}
