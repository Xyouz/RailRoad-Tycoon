package train

import vehicle.Vehicle
import trainEngine.TrainEngine
import town._
import cargo._
import infoPane._
import model._


/**This class represents the trains, it extends the class "Vehicle", except here, we must also deal
 * with the different loadings and the different wagons.
*/

class Train(name : String, engine : TrainEngine, val game : Game ) extends Vehicle(name, engine){
  var listOfWagon = List[Cargo]()
  def wagons() = {listOfWagon}
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
