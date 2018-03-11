package vehicle

import point.Point
import town.Town
import engine.Engine

abstract class Vehicle( val name : String, val engine : Engine){
  override def toString() = {name}
  val maxLoad = engine.maxLoad
  var position = new Point(-10, 10)
  var distance = -1.0
  var destination = {-1} // the destination's ID
  def update() = {
    distance += engine.getSpeed(load)
    distance
  }
  def resetDistance() = {distance = 0}
  def getDestination() = {destination}
  def setDestination(town : Town) = {destination = town.getID()}
  def setPosition(p : Point) = {position = p}
  var loading =  0 // number of passengers in the train.
  var load = 0 // the weight to carry
  def setLoading( l : Int ) = { loading = l;  load = l}
}
