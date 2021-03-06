package plane

import vehicle.Vehicle
import planeEngine.PlaneEngine
import town._
import cargo._
import stuff._
import model._
import point._


/** This class extends the class "Vehicles".
 * A plane can be launch only between cities that have an airport.
 * The speed depends on the weigth of what it carries.
 * A plane can trnsport passengers and only one kind of goods.
 * The player earn money when the plane arrive in a city.
*/

case class PlaneData(name : String, engine : PlaneEngine, route : Array[Int], desiredLoad : Double, longHaul : Boolean)


class Plane(name : String, engine : PlaneEngine, val game : Game) extends Vehicle(name, engine){
  def toData = {
    new PlaneData(name, engine, route.map(_.getID), desiredLoad, longHaul)
  }

  var hold : Option[Cargo] = None
  var flying = false
  var flightBriefing = Array[Town]()
  var begin : Town = new Town(42,"Test",42,new Point(42,42))
  var end : Town = new Town(42,"Test",42,new Point(42,42))
  var endHop = new Town(42,"Test",42,new Point(42,42))
  var beginHop = new Town(42,"Test",42,new Point(42,42))
  var step = 0
  var nbStep = 42
  def maximalLoad() = {engine.maxLoad}

  def hasLoad = {
    hold != None
  }

  def getHold = {
    hold match {
      case None => throw new EmptyCargo()
      case Some(c) => c
    }

  }

  override def weight() = {
    hold match {
      case Some(c) => 0.5 + c.weight
      case None => 0.5
    }
  }

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

  def someTownInRoute(t : Option[Town]) = {
    t match{
      case None => false
      case Some(t) => route.exists(_ == t)
    }
  }

  override def update() = {
    if (flying) {
      distance += engine.getSpeed(load)/2
      game.deltaMoney(-distance * engine.priceByKm / 5)
      position = beginHop.pos + (endHop.pos - beginHop.pos).normalize().scale(distance)
      if (distance >= (endHop.pos - beginHop.pos).norm()){
        if (step >= nbStep-1){
          setCurrentTown(Some(end))
          distance = -1.0
          flying = false
          try {
            val bd = getHold.destination == Some(end)
            val bi = getHold.inHub == Some(end)
            val bo = getHold.outHub == Some(end)
            if (hasLoad && (bd || ((bi || bo)&& !someTownInRoute(getHold.destination)))){
              getHold.from match{
                case None => ()
                case Some(t) => {
                  if (bd){
                    game.deltaMoney(end.distanceTo(t)*50)
                  }
                }
              }
              end.cargosInTown = getHold +: end.cargosInTown
              hold = None
            }
            end.loadPlane(this)
          }
          catch {
            case EmptyCargo() => {
              if (hasLoad){
                end.cargosInTown = getHold +: end.cargosInTown
              }
              hold = None
              end.loadPlane(this)
            }
          }
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
    distance
  }

  override def unload(t : Town) = {
  }


}
