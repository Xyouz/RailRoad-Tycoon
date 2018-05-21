package cargo

import stuff._
import town._

import saveUtils._

case class EmptyCargo() extends Exception() {}
case class NoDestinationError() extends Exception() {}

/** The class "Cargo" is useful to load and to unload some goods into a city, basically the destination of the train.
 * It is extended in the class "Wagon"
 * Used typeOfLoad are : Liquid, Dry, Container
 * Cargos are initially created in xmlParsing.scala
*/

case class CargoData(typeOfLoad : String, maxLoad : Double, from : Option[Int],
    destination : Option[Int], inHub : Option[Int], outHub : Option[Int],
    content : Option[Stuff])

class Cargo (val typeOfLoad : String, val maxLoad : Double) {

  // def this(cc : CargoData) = {
  //   this(cc.typeOfLoad,cc.maxLoad)
  //   from = cc.from
  //   content = cc.content
  //   destination = cc.destination
  //   inHub = cc.inHub
  //   outHub = cc.outHub
  // }

  def toData = {
    new CargoData(typeOfLoad, maxLoad, optionTownToInt(from), optionTownToInt(destination), optionTownToInt(inHub), optionTownToInt(outHub), content)
  }

  def optionTownToInt(t : Option[Town]) = {
    t match {
      case Some(tt) => Some(tt.getID)
      case None => None
    }

  }

  override def toString() = {
    content match{
      case Some(s) => s.toString
      case None => "Vide" 
    }
  }
  def kindOfLoad() = {typeOfLoad}
  var from : Option[Town] = None
  var content : Option[Stuff] = None
  var destination : Option[Town] = None
  var inHub : Option[Town] = None
  var outHub : Option[Town] = None

  def weight() = {
    content match {
      case None => 1.0
      case Some(s) => 1.0 + 0.1 * s.quantity
    }
  }

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
