package plane

import vehicle.Vehicle
import planeEngine.PlaneEngine
import town._
import cargo._
import stuff._
import model._
import point._



//a class to represent planes
class Plane(name : String, engine : PlaneEngine, hold : Cargo, val game : Game) extends Vehicle(name, engine){
  println("la classe avion a encore acces à game et a perdu la gestion des exceptions")
  var flying = false
  var flightBriefing = Array[Town]()
  var begin : Town = new Town(42,"Test",42,new Point(42,42))
  var end : Town = new Town(42,"Test",42,new Point(42,42))
  var endHop = new Town(42,"Test",42,new Point(42,42))
  var beginHop = new Town(42,"Test",42,new Point(42,42))
  var step = 0
  var nbStep = 42
  def holdings() = {hold}
  def maximalLoad() = {hold.maxLoad}

  def startFly(beginTown : Town, endTown : Town) = {
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
  }

  override def update() = {
    if (flying) {
      distance += engine.getSpeed(load)/2
      position = beginHop.pos + (endHop.pos - beginHop.pos).normalize().scale(distance)
      if (distance >= (endHop.pos - beginHop.pos).norm()){
        if (step >= nbStep-1){
          setCurrentTown(Some(end))
          distance = -1.0
          flying = false
          try {
            end.receiveStuff(hold.unload())
          }
          catch {
            case Exception => ()
          }
          end.loadPlane(this)
          nextDestination()
          // var briefing = game.airports.getBriefing(getCurrentTown,game.townList(destination),engine.maxRange)
          // if (briefing.length > 1 && (briefing(0).pos-briefing(1).pos).norm()*engine.priceByKm <= game.money) {
              startFly(getCurrentTown,game.townList(destination))
          // }
          // else {
          //   position = new Point(-1000000,-10000000)
          // }
        }
        else {
          // if((endHop.pos-flightBriefing(step+1).pos).norm()*engine.priceByKm <= game.money){
            step += 1
            distance = 0
            beginHop = endHop
            endHop = flightBriefing(step)
            game.deltaMoney(-(endHop.pos-beginHop.pos).norm() * engine.priceByKm)
          // }
          // else {
            // position = new Point(-1000000,-10000000)
          // }
        }
      }
    }
    // else {
    //   try {
    //     var briefing = game.airports.getBriefing(getCurrentTown,game.townList(destination),engine.maxRange)
    //     if (briefing.length > 1 && (briefing(0).pos-briefing(1).pos).norm()*engine.priceByKm <= game.money) {
    //       startFly(getCurrentTown,game.townList(destination))
    //     }
    //   }
    //   catch {
    //     case _ : ArrayIndexOutOfBoundsException => ()
    //   }
    // }
    distance
  }

  override def unload(t : Town) = {
    t.pop += loading
  }


}
