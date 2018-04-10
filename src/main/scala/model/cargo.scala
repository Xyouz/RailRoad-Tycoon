package cargo

import stuff._
import town._

abstract class Cargo (val typeOfLoad : String, val maxLoad : Double) {
  def kindOfLoad() = {typeOfLoad}
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
