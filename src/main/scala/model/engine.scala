package engine

abstract class Engine(val name : String, val maxSpeed : Double, val maxLoad : Double ){
  override def toString() = {name}
  def getSpeed(load : Double) : Double
}
