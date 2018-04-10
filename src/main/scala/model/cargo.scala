package cargo

import stuff._
import town._

case class EmptyCargo() extends Exception() {

}

class Cargo (val typeOfLoad : String, val maxLoad : Double) {
  def kindOfLoad() = {typeOfLoad}
  var content : Option[Stuff] = None
  var destination : Option[Town] = None
  def load(toLoad : Stuff) = {
    content = Some(toLoad)
  }
  def unload() = {
    content match {
      case None => throw new EmptyCargo()
      case Some(loading) => {
        content = None
        loading
      }
    }
  }
}
