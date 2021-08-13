package com.vladimirbaklan

package object orders {
  object OrderStatus extends Enumeration {
    type OrderStatus = Value

    val Created: Value = Value("created")
    val Failed: Value = Value("failed")
  }
}
