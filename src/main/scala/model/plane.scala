package plane

import vehicle.Vehicle
import planeEngine.PlaneEngine
import town._
import cargo._
import stuff._
import model._
import point._

class Box(typeOf : String, maxLoad : Double) extends Cargo(typeOf, maxLoad) {
  def getStuff() = {
    new Stuff(typeOf, 1.0)
  }
}

//a class to represent planes
class Plane(name : String, engine : PlaneEngine, hold : Box, val game : Game) extends Vehicle(name, engine){
  println("la classe avion a encore acces à game et a perdu la gestion des exceptions")
  var flying = false
  var flightBriefing = Array[Town]()
  var begin : Town = new Town(42,"Test",42,new Point(42,42))
  var end : Town = new Town(42,"Test",42,new Point(42,42))

  def startFly(beginTown : Town, endTown : Town) = {
    // if (!(begin.hasAirport && end.hasAirport)) {
    //   println(s"${begin}   ${end}")
    //   throw new NoAirportException()
    // }
    // else {
      flying = true
      begin = beginTown
      end = endTown
      distance = 0
      currentTown = None
    // }
  }

  override def update() = {
    if (flying) {
      distance += engine.getSpeed(load)/2
      position = begin.pos + (end.pos - begin.pos).normalize().scale(distance)
      if (distance >= (end.pos - begin.pos).norm()){
        setCurrentTown(Some(end))
        distance = -1.0
        flying = false
        getCurrentTown.receiveStuff(hold.getStuff())
        getCurrentTown.loadPlane(this)
        nextDestination()
        startFly(getCurrentTown,game.townList(destination))
        println("TODO : prendre en compte les multiTrajets")
      }
    }
    // if (route.length <= distance ){
    //   getCurrentTown.receiveStuff(hold.getStuff())
    //   getCurrentTown.loadPlane(this)
    //   nextDestination()
    //   startFly(getCurrentTown,game.townList(destination))
    //   println("TODO : reecrire plane.update pour avoir des escales")
    // }
    distance
  }

  override def unload(t : Town) = {
    t.pop += loading
  }


}
