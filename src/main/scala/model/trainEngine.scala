package trainEngine

import engine._
import scala.math._

/** It extends the abstract class "Engine".
 * It also takes into account the influence of the weight of the loading on the speed of the train.
*/
case class TrainEngineData(name : String, maxSpeed : Double, maxLoad : Double , electric : Boolean, price : Double, priceByKm : Double)

class TrainEngine(name : String, maxSpeed : Double, maxLoad : Double , val electric : Boolean, price : Double, priceByKm : Double)
    extends Engine(name, maxSpeed, maxLoad, Double.PositiveInfinity, price, priceByKm){
  def this(cc : TrainEngineData) = {
    this(cc.name,cc.maxSpeed, cc.maxLoad , cc.electric , cc.price , cc.priceByKm )
  }
  def toData = {
    new TrainEngineData(name,maxSpeed, maxLoad, electric, price, priceByKm)
  }

  def getSpeed(load : Double) = {
    maxSpeed * (max(0,maxLoad - load))/maxLoad
  }
}
