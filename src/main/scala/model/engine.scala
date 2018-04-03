package engine

abstract class Engine(val name : String, val maxSpeed : Double, val maxLoad : Double, val maxRange : Double, var price : Int ){
  override def toString() = {name}
  def getSpeed(load : Double) : Double
}
