package trainEngine

import engine._
import scala.math._


class TrainEngine(name : String, maxSpeed : Double, maxLoad : Double , val electric : Boolean)
    extends Engine(name, maxSpeed, maxLoad){
  def getSpeed(load : Double) = {
    maxSpeed * (max(0,maxLoad - load))/maxLoad
  }
}
