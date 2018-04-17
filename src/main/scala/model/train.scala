package train

import vehicle.Vehicle
import trainEngine.TrainEngine
import town._
import wagon._
import infoPane._
import model._


/**This class represents the trains, it extends the class "Vehicle", except here, we must also deal
 with the different loadings and the different wagons.
*/

class Train(name : String, engine : TrainEngine, listOfWagon : List[Wagon], val game : Game ) extends Vehicle(name, engine){
  def wagons() = {listOfWagon}
  override def unload(t : Town) = {
    t.pop = t.pop + loading
    loading = 0
    val numberOfWagons = listOfWagon.length
    for (i <- 0 until numberOfWagons){
      if (listOfWagon(i).content != None) {
        try {
          var unloaded = listOfWagon(i).unload()
          game.deltaMoney(t.receiveStuff(unloaded))
          listOfWagon(i).content = None
        }
        catch {
          case _ : Exception  => {}
        }

      }
    }
  }
}
