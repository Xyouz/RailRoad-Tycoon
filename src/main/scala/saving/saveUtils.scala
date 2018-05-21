package saveUtils

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import town._
import point._
import trainEngine._
import planeEngine._
import stuff._
import cargo._
import factory._
import train._
import plane._

object JsonUtil {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def toJson(value: Map[Symbol, Any]): String = {
    toJson(value map { case (k,v) => k.name -> v})
  }


  def toJson(value : NoAirportException) : String = {
    ""
  }

  def toJson(value : Town) : String = {
    toJson(value.toData)
  }

  def toJson(value : Factory) : String = {
    toJson("factory")
  }

  def toJson(value : Plane) : String = {
    toJson(value.toData)
  }

  def toJson(value : Train) : String = {
    toJson(value.toData)
  }

  def toJson(value : Point) : String = {
    toJson(value.toData)
  }

  def toJson(value : TrainEngine) : String = {
    toJson(value.toData)
  }

  def toJson(value : PlaneEngine) : String = {
    toJson(value.toData)
  }

  def toJson(value : Cargo) : String = {
    toJson(value.toData)
  }

  def toJson(value : Stuff) : String = {
    toJson(value.toData)
  }

  def toJson(value: Any): String = {
    mapper.writeValueAsString(value)
  }

  def toMap[V](json:String)(implicit m: Manifest[V]) = fromJson[Map[String,V]](json)

  def fromJson[T](json: String)(implicit m : Manifest[T]): T = {
    mapper.readValue[T](json)
  }
}
