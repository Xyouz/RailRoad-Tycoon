package vehicle

import point.Point
import town.Town
import engine.Engine
import scalafx.scene.paint.Color._

abstract class Vehicle( val name : String, val engine : Engine){
  override def toString() = {name}
  val maxLoad = engine.maxLoad
  var position = new Point(-10000, 10000)
  var nstep = 0
  var stepCount = 0
  var distance = -1.0
  var destination = {-1} // the destination's ID
  var nextDest = {-1}
  var route = Array[Town]()
  var color = DarkCyan
  def update() = {
    distance += engine.getSpeed(load)/2
    distance
  }
  def resetDistance() = {distance = 0}
  def getDestination() = {destination}
  def setRoute(newRoute : Array[Town]) = {
    route = newRoute
    nstep = newRoute.length
    if (nstep > 0){
        nextDest = route(0).getID()
    }
    stepCount = 0
  }
  def unload(t: Town) = {
    println("/!  fonction vehicle.unload à écrire")
  }
  def nextDestination() = {
    destination = nextDest
    nextDest = route((stepCount + 1) % nstep).getID()
    stepCount += 1
  }
  def setDestination(town : Town) = {destination = town.getID()}
  def setPosition(p : Point) = {position = p}
  var loading =  0 // number of passengers in the train.
  var load = 0 // the weight to carry
  def setLoading( l : Int ) = { loading = l;  load = l}
}
