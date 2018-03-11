package trainEngine

import engine._

class TrainEngine(name : String, maxSpeed : Double, maxLoad : Double )
    extends Engine(name, maxSpeed, maxLoad){
  def getSpeed(load) = {
    maxSpeed * (max(0,maxLoad - load))/maxLoad
  }
}
