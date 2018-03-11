package trainEngine

import engine._

class TrainEngine(name : String, maxSpeed : Double, maxLoad : Double )
    extends Engine(name, maxSpeed, maxLoad){
<<<<<<< HEAD
  def getSpeed(load : Double) = {
=======
  def getSpeed(load) = {
>>>>>>> 7a9c60d948fdd5306d9e5aebf02013ca4ddd1939
    maxSpeed * (max(0,maxLoad - load))/maxLoad
  }
}
