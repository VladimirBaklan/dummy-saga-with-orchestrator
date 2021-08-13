package com.vladimirbaklan.utils

import com.vladimirbaklan.common.BaseJsonProtocol
import org.json4s.JValue

import scala.util.Try

object JsonUtils {
  def parseJson(value: String): JValue = {
    BaseJsonProtocol.parseJson(value)
  }

  def fromJValue[T: Manifest](value: JValue): T = {
    BaseJsonProtocol.fromJValue(value)
  }

  def fromJson[T: Manifest](value: String): T = {
    BaseJsonProtocol.readJson[T](value)
  }

  def tryFromJson[T: Manifest](value: String): Try[T] = {
    Try(BaseJsonProtocol.readJson[T](value))
  }

  def toJson[T <: AnyRef](v: T): String = {
    BaseJsonProtocol.writeJson[T](v)
  }
}
