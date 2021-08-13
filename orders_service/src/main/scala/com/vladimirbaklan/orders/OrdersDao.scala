package com.vladimirbaklan.orders

import com.vladimirbaklan.database.Jooq.TableField
import com.vladimirbaklan.database.JooqTable
import com.vladimirbaklan.orders.OrderStatus.OrderStatus
import org.jooq.DSLContext

trait OrdersDao {
  def updateStatus(orderId: Int, status: OrderStatus): Unit

  def createOrder(status: OrderStatus, productId: Int): Int
}

class JooqOrdersDao(jooq: DSLContext) extends OrdersDao {
  private val ot = new OrdersTable()

  override def updateStatus(orderId: Int, status: OrderStatus): Unit = {
    jooq.update(ot)
      .set(ot.status, status.toString)
      .where(ot.orderId.eq(orderId))
      .execute()
  }

  override def createOrder(status: OrderStatus, productId: Int): Int = {
    jooq.insertInto(ot)
      .set(ot.status, status.toString)
      .set(ot.productId, new Integer(productId))
      .returningResult(ot.orderId)
      .fetchOne()
      .value1()
  }
}

class OrdersTable extends JooqTable("orders") {
  val orderId: TableField[Integer] = createIntegerField("order_id")
  val status: TableField[String] = createVarcharField("status")
  val productId: TableField[Integer] = createIntegerField("product_id")
}