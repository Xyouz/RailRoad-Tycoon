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
import model._

import fileWriter._

object SaveUtil {
    def saveGame(game : Game, path : String) = {
      val gameSave = JsonUtil.toJson(game)
      Writer.write(gameSave,s"${path}/game.json")
    }

    def saveTrains(trainList : Seq[Train], path : String) = {
      var c = 0
      Writer.createFolder(s"${path}/trains")
      for (train <- trainList){
        val trainSave = JsonUtil.toJson(train)
        Writer.write(trainSave,s"${path}/trains/train${c}.json")
        c += 1
      }
    }

    def savePlanes(planeList : Seq[Plane], path : String) = {
      var c = 0
      Writer.createFolder(s"${path}/planes")
      for (plane <- planeList){
        val planeSave = JsonUtil.toJson(plane)
        Writer.write(planeSave,s"${path}/planes/plane${c}.json")
        c += 1
      }
    }

    def saveTowns(townList : Seq[Town], path : String) = {
      var c = 0
      Writer.createFolder(s"${path}/towns")
      for (town <- townList){
        val townSave = JsonUtil.toJson(town)
        Writer.write(townSave,s"${path}/towns/town${c}.json")
        c += 1
      }
    }
  }

object JsonUtil {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def toJson(value: Map[Symbol, Any]): String = {
    toJson(value map { case (k,v) => k.name -> v})
  }


  def toJson(value : Town) : String = {
    toJson(value.toData)
  }

  def toJson(value : List[Any]) : String = {
    if (value.length == 0){
      "[]"
    }
    else{
      var res = "["
      for (item <- value) {
        res = res + toJson(item) +","
      }
      res.substring(0,res.length-1) + "]"
    }
  }

  def toJson(value : Seq[Any]) : String = {
    if (value.length == 0){
      "[]"
    }
    else{
      var res = "["
      for (item <- value) {
        res = res + toJson(item) +","
      }
      res.substring(0,res.length-1) + "]"
    }
  }

  def toJson(value : Game) : String = {
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
