package com.vladimirbaklan.utils

import org.slf4j.{Logger, LoggerFactory}

trait Logging {
  protected val log: Logger = LoggerFactory.getLogger(getClass)
}
