package wagon

import stuff._
import cargo._
import train._


class Wagon(typeOfLoad : String, maxLoad : Double) extends Cargo(typeOfLoad, maxLoad){
  def kindOfLoad() = {typeOfLoad}
  var content : Option[Stuff] = None
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
