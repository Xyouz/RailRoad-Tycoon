package cargo

import stuff._
import town._


case class EmptyCargo() extends Exception() {}
case class NoDestinationError() extends Exception() {}

/** The class "Cargo" is useful to load and to unload some goods into a city, basically the destination of the train.
 * It is extended in the class "Wagon"
 * Used typeOfLoad are : Liquid, Dry, Container
 * Cargos are initially created in xmlParsing.scala
*/

class Cargo (val typeOfLoad : String, val maxLoad : Double) {
  override def toString() = {typeOfLoad }
  def kindOfLoad() = {typeOfLoad}
  var from : Option[Town] = None
  var content : Option[Stuff] = None
  var destination : Option[Town] = None
  var inHub : Option[Town] = None
  var outHub : Option[Town] = None
  def getFrom()={
    from match {
      case Some(t) => t
      case None => throw new NoDestinationError()
    }

  }
  def hasContent() = {
    content != None
  }
  def isEmpty() = {
    content == None
  }
  def hasDestination() = {
    destination != None
  }
  def getDestination() = {
    destination match {
      case Some(t) => t
      case None => throw new NoDestinationError()
    }

  }
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
