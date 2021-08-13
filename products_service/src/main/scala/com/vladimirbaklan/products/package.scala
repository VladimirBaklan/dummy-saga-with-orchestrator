package com.vladimirbaklan

package object products {
  object ProductStatus extends Enumeration {
    type ProductStatus = Value

    val Available: Value = Value("available")
    val SoldOut: Value = Value("sold_out")
  }
}
