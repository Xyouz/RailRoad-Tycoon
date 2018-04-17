package cargo

import stuff._
import town._


case class EmptyCargo() extends Exception() {

}

/** The class "Cargo" is useful to load and to unload some goods into a city, basically the destination of the train.
 * It is extended in the class "Wagon"
*/

class Cargo (val typeOfLoad : String, val maxLoad : Double) {
  override def toString() = {typeOfLoad }
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
