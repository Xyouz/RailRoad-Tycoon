package train

import vehicle.Vehicle
import trainEngine.TrainEngine
import town._
import wagon._
//a class to represent trains
class Train(name : String, engine : TrainEngine, listOfWagon : List[Wagon]) extends Vehicle(name, engine){
  override def unload(t : Town) = {
    t.pop += loading
  }
}
