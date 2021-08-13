package com.vladimirbaklan.products

import com.vladimirbaklan.database.Jooq.TableField
import com.vladimirbaklan.database.JooqTable
import com.vladimirbaklan.products.ProductStatus.ProductStatus
import org.jooq.DSLContext

trait ProductsDao {
  def updateStatus(productId: Int, status: ProductStatus): Unit
}

class JooqProductsDao(jooq: DSLContext) extends ProductsDao {
  private val pt = new ProductsTable()

  override def updateStatus(productId: Int, status: ProductStatus): Unit = {
    jooq.update(pt)
      .set(pt.status, status.toString)
      .where(pt.productId.eq(productId))
      .execute()
  }
}

class ProductsTable extends JooqTable("products") {
  val productId: TableField[Integer] = createIntegerField("product_id")
  val status: TableField[String] = createVarcharField("status")
  val title: TableField[String] = createVarcharField("title")
}