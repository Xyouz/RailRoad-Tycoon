package planeEngine

import engine._
import scala.math._

/** It extends the abstract class "Engine".
 * It also takes into account the influence of the weight of the loading on the speed.
*/

case class PlaneEngineData(name : String, maxSpeed : Double, maxLoad : Double, maxRange : Double, price : Double , priceByKm : Double)

class PlaneEngine( name : String, maxSpeed : Double, maxLoad : Double, maxRange : Double, price : Double , priceByKm : Double)
    extends Engine(name, maxSpeed, maxLoad, maxRange, price, priceByKm){

  def this(cc : PlaneEngineData) = {
    this(cc.name ,cc.maxSpeed ,cc.maxLoad ,cc.maxRange ,cc.price ,cc.priceByKm )
  }

  def toData = {
    new PlaneEngineData(name , maxSpeed , maxLoad , maxRange , price , priceByKm)
  }

  val minSpeed = 10  // the minimal speed at which a plane can fly
  def getSpeed(load : Double) = {
    var s = maxSpeed * (max(0,maxLoad - load))/maxLoad
    if (s >= minSpeed) {s}
    else {0}
  }
}
