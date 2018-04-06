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
  var endHop = new Town(42,"Test",42,new Point(42,42))
  var beginHop = new Town(42,"Test",42,new Point(42,42))
  var step = 0
  var nbStep = 42

  def startFly(beginTown : Town, endTown : Town) = {
    // if (!(begin.hasAirport && end.hasAirport)) {
    //   println(s"${begin}   ${end}")
    //   throw new NoAirportException()
    // }
    // else {
      flying = true
      begin = beginTown
      end = endTown
      flightBriefing = game.airports.getBriefing(begin,end,engine.maxRange)
      step = 1
      nbStep = flightBriefing.length
      beginHop = flightBriefing(0)
      if (nbStep == 1){
        endHop = flightBriefing(0)
      }
      else {
        endHop = flightBriefing(1)
      }
      distance = 0
      currentTown = None
    // }
  }

  override def update() = {
    if (flying) {
      distance += engine.getSpeed(load)/2
      position = beginHop.pos + (endHop.pos - beginHop.pos).normalize().scale(distance)
      if (distance >= (endHop.pos - beginHop.pos).norm()){
        println(step)
        if (step >= nbStep-1){
          setCurrentTown(Some(end))
          distance = -1.0
          flying = false
          step = 0
          getCurrentTown.receiveStuff(hold.getStuff())
          getCurrentTown.loadPlane(this)
          nextDestination()
          startFly(getCurrentTown,game.townList(destination))
        }
        else {
          step += 1
          distance = 0
          beginHop = endHop
          endHop = flightBriefing(step)
        }
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
