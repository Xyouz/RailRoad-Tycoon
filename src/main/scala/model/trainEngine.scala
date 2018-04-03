package trainEngine

import engine._
import scala.math._


class TrainEngine(name : String, maxSpeed : Double, maxLoad : Double , val electric : Boolean, price : Int)
    extends Engine(name, maxSpeed, maxLoad, Double.PositiveInfinity, price){
  def getSpeed(load : Double) = {
    maxSpeed * (max(0,maxLoad - load))/maxLoad
  }
}
