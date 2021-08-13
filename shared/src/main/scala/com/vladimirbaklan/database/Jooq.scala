package com.vladimirbaklan.database

import java.lang.{Boolean => JBoolean, Long => JLong}
import java.sql.Timestamp
import java.time.{LocalDate, LocalDateTime}

import com.vladimirbaklan.database.Jooq.TableField
import org.jooq._
import org.jooq.impl.DSL.name
import org.jooq.impl.{SQLDataType, TableImpl}

object Jooq {

  type TableField[T] = org.jooq.TableField[Record, T]

  type RecordMapper[T] = org.jooq.RecordMapper[Record, T]
}


class JooqTable(tableName: String, aliased: Option[Table[Record]] = None) extends TableImpl[Record](name(tableName), null, aliased.orNull) {

  def createIntegerField(fieldName: String): TableField[Integer] = createField(name(fieldName), SQLDataType.INTEGER)

  def createBigIntField(fieldName: String): TableField[JLong] = createField(name(fieldName), SQLDataType.BIGINT)

  def createVarcharField(fieldName: String): TableField[String] = createField(name(fieldName), SQLDataType.VARCHAR)

  def createTextField(fieldName: String): TableField[String] = createField(name(fieldName), SQLDataType.CLOB)

  def createTimestampField(fieldName: String): TableField[Timestamp] = createField(name(fieldName), SQLDataType.TIMESTAMP)

  def createLocalDateField(fieldName: String): TableField[LocalDate] = createField(name(fieldName), SQLDataType.LOCALDATE)

  def createLocalDateTimeField(fieldName: String): TableField[LocalDateTime] = createField(name(fieldName), SQLDataType.LOCALDATETIME)

  def createBooleanField(fieldName: String): TableField[JBoolean] = createField(name(fieldName), SQLDataType.BOOLEAN)
}