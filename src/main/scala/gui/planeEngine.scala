package planeEngine

import engine._

class PlaneEngine( name : String, maxSpeed : Double, maxLoad : Double )
    extends Engine(name, maxSpeed, maxLoad){
  val minSpeed = 10  // the minimal speed at which a plane can fly
  def getSpeed(load) = {
    var s = maxSpeed * (max(0,maxLoad - load))/maxLoad
    if (s >= minSpeed) {s}
    else {0}
  }
}
