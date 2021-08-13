package com.vladimirbaklan.eventsbus

trait EventProcessor {
  def process(event: BaseEvent): Unit
}