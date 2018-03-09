package train

import town._

//a class to represent the train, for a train, we need to know its speed, its destination, and we need a way to update its information
class Train(val speed : Double, val name : String){
  override def toString() = {name}
  var distanceOnRoad : Double = -1
  var destination = -1 // L'ID de la destination
  def update() = { {distanceOnRoad += speed};
                    distanceOnRoad}
  def resetDistance() = {distanceOnRoad = 0}
  def getDestination() = {destination}
  def setDestination(town : Town) = {destination = town.getID()}
  def getName() = {name}
  var loading =  0 // number of passengers in the train.
  def setLoading( l : Int ) = { loading = l}
}
