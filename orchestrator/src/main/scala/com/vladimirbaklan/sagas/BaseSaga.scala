package com.vladimirbaklan.sagas

import com.vladimirbaklan.eventsbus.BaseEvent

trait BaseSaga {
  def process(event: BaseEvent): Unit

  def start(event: BaseEvent): Unit

  def rollback(event: BaseEvent): Unit

  def nextStep(event: BaseEvent): Unit

  def unprocessableFailures(event: BaseEvent): Unit
}
