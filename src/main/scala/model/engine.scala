package engine

/** This abstract class enables us to add different kind of engines that run the planes or the trains.
*/

abstract class Engine(val name : String, val maxSpeed : Double, val maxLoad : Double, val maxRange : Double, val price : Double, val priceByKm : Double){
  override def toString() = {name}
  def getSpeed(load : Double) : Double
}
