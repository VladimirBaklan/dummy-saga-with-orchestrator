package com.vladimirbaklan.products

import com.vladimirbaklan.common.ServiceResult
import com.vladimirbaklan.common.ServiceResult._
import com.vladimirbaklan.products.ProductStatus.ProductStatus

import scala.util.Try

trait ProductsService {
  def updateProductStatus(productId: Int, newStatus: ProductStatus): ServiceResult[Unit]
}

class DefaultProductsService (dao: ProductsDao) extends ProductsService {
  override def updateProductStatus(productId: Int, newStatus: ProductStatus): ServiceResult[Unit] = {
    fromTry(Try(dao.updateStatus(productId, newStatus)))
  }
}
