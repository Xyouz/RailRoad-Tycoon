package planeEngine

import engine._
import scala.math._


class PlaneEngine( name : String, maxSpeed : Double, maxLoad : Double,  price :Int )
    extends Engine(name, maxSpeed, maxLoad, price){
  val minSpeed = 10  // the minimal speed at which a plane can fly
  def getSpeed(load : Double) = {
    var s = maxSpeed * (max(0,maxLoad - load))/maxLoad
    if (s >= minSpeed) {s}
    else {0}
  }
}
