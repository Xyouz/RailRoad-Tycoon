package trainEngine

import engine._

class TrainEngine(name, maxSpeed, maxLoad) extends Engine(name, maxSpeed, maxLoad){
  def getSpeed(load) = {
    maxSpeed * (max(0,maxLoad - load))/maxLoad
  }
}
