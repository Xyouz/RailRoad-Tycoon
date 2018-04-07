package wagon

import stuff._
import cargo._
import train._
import town._

class Wagon(typeOfLoad : String, maxLoad : Double) extends Cargo(typeOfLoad, maxLoad){
  var content : Option[Stuff] = None
  var destination : Option[Town] = None
  def load(toLoad : Stuff) = {
    content = Some(toLoad)
  }
  def unload() = {
    content match {
      case None => throw new Exception
      case Some(loading) => {
        content = None
        loading
      }
    }
  }
}
