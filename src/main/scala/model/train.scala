package train

import vehicle.Vehicle
import trainEngine.TrainEngine
import town._
import cargo._
import model._


/**This class represents the trains, it extends the class "Vehicle", except here, we must also deal
 * with the different loadings and the different wagons.
*/
case class TrainData(name : String, engine : TrainEngine, route : Array[Int], desiredLoad : Double, longHaul : Boolean)

class Train(name : String, engine : TrainEngine, val game : Game ) extends Vehicle(name, engine){
  var listOfWagon = List[Cargo]()
  def wagons() = {listOfWagon}

  def toData = {
    new TrainData(name, engine, route.map(_.getID), desiredLoad, longHaul)
  }

  override def weight() = {
    listOfWagon.foldLeft(0.0)((w,w2) => w + w2.weight())
  }

  override def unload(t : Town) = {
    t.pop = t.pop + loading
    loading = 0
    var l = List[Cargo]()
    for (wagon <- listOfWagon){
      if (wagon.destination == Some(t)) {
        t.receiveCargo(wagon)
      }
      else if (wagon.outHub == Some(t)) {
        t.receiveCargo(wagon)
      }
      else {
        l = wagon +: l
      }
    }
    listOfWagon = l
  }
}
