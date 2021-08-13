package com.vladimirbaklan.orders

import com.vladimirbaklan.common.ServiceResult
import com.vladimirbaklan.common.ServiceResult._
import com.vladimirbaklan.orders.OrderStatus.OrderStatus

import scala.util.Try

trait OrdersService {
  def updateStatus(orderId: Int, status: OrderStatus): ServiceResult[Unit]

  def createOrder(productId: Int, status: OrderStatus): ServiceResult[Int]
}

class DefaultOrdersService (dao: OrdersDao) extends OrdersService {
  override def updateStatus(orderId: Int, status: OrderStatus): ServiceResult[Unit] =
    fromTry(Try(dao.updateStatus(orderId, status)))

  override def createOrder(productId: Int, status: OrderStatus): ServiceResult[Int] =
    fromTry(Try(dao.createOrder(status, productId)))
}