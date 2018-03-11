package vehicle

import point.Point
import town.Town

abstract class Vehicle(val speed : Double, val name : String){
  override def toString() = {name}
  var position = new Point(-10, 10)
  var distance = -1.0
  var destination = {-1} // the destination's ID
  def update() = {
    distance += speed
    distance
  }
  def resetDistance() = {distance = 0}
  def getDestination() = {destination}
  def setDestination(town : Town) = {destination = town.getID()}
  def setPosition(p : Point) = {position = p}
  var loading =  0 // number of passengers in the train.
  def setLoading( l : Int ) = { loading = l}
}
