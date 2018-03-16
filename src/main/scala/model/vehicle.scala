package vehicle

import point.Point
import town.Town
import engine.Engine

abstract class Vehicle( val name : String, val engine : Engine){
  override def toString() = {name}
  val maxLoad = engine.maxLoad
  var position = new Point(-10, 10)
  var nstep = 0
  var distance = -1.0
  var destination = {-1} // the destination's ID
  var nextDestination = {-1}
  var route = Array[Town]()
  def update() = {
    distance += engine.getSpeed(load)
    distance
  }
  def resetDistance() = {distance = 0}
  def getDestination() = {destination}
  def setRoute(newRoute : Array[Town]) = {
    route = newRoute
    nstep = newRoute.length
    if (nstep > 0){
        nextDestination = route(0).getID()
    }
  }
  def unload(t: Town) = {}
  def setDestination(town : Town) = {destination = town.getID()}
  def setPosition(p : Point) = {position = p}
  var loading =  0 // number of passengers in the train.
  var load = 0 // the weight to carry
  def setLoading( l : Int ) = { loading = l;  load = l}
}
