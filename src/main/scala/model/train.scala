package train

import vehicle.Vehicle
import trainEngine.TrainEngine
import town._
import wagon._

//a class to represent trains
class Train(name : String, engine : TrainEngine, listOfWagon : List[Wagon]) extends Vehicle(name, engine){
  def wagons() = {listOfWagon}

  override def unload(t : Town) = {
    t.pop = t.pop + loading
    loading = 0
    val numberOfWagons = listOfWagon.length
    for (i <- 0 until numberOfWagons){
      if (listOfWagon(i).content != None) {
        try {
          var unloaded = listOfWagon(i).unload()
          t.receiveStuff(unloaded)
        }
        catch {
          case _ : Exception  => {}
        }

      }
    }
  }
}
