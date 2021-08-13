package com.vladimirbaklan

import com.vladimirbaklan.eventsbus.{BaseEvent, EventProcessor}
import com.vladimirbaklan.sagas.SagasFactory
import com.vladimirbaklan.utils.Logging

class OrchestratorEventProcessor(factory: SagasFactory) extends EventProcessor with Logging {
  def process(event: BaseEvent): Unit = factory.processSagaMessage(event)
}
