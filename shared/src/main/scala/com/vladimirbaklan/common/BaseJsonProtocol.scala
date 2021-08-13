package com.vladimirbaklan.common

import com.vladimirbaklan.eventsbus.EventType
import org.json4s.JsonAST.JValue
import org.json4s.ext.EnumNameSerializer
import org.json4s.{DefaultFormats, Extraction, Formats, Serialization}

object BaseJsonProtocol {
  implicit val serialization: Serialization = org.json4s.native.Serialization
  implicit val formats: Formats = DefaultFormats + new EnumNameSerializer(EventType)

  def toJValue[T <: Any](value: T): JValue = {
    Extraction.decompose(value)
  }

  def toJValueSnakeCase[T <: Any](value: T): JValue = {
    toJValue(value).underscoreKeys
  }

  def fromJValue[T: Manifest](value: JValue): T = {
    Extraction.extract[T](value)
  }

  def writeJson[T <: AnyRef](v: T): String = {
    serialization.write(v)
  }

  def readJson[T: Manifest](v: String): T = {
    serialization.read[T](v)
  }

  def parseJson(v: String): JValue = {
    org.json4s.native.JsonMethods.parse(v, true)
  }
}
