package trainEngine

import engine._
import scala.math._

/** It extends the abstract class "Engine".
 * It also takes into account the influence of the weight of the loading on the speed of the train.
*/

class TrainEngine(name : String, maxSpeed : Double, maxLoad : Double , val electric : Boolean, price : Double, priceByKm : Double)
    extends Engine(name, maxSpeed, maxLoad, Double.PositiveInfinity, price, priceByKm){
  def getSpeed(load : Double) = {
    maxSpeed * (max(0,maxLoad - load))/maxLoad
  }
}
