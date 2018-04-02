package plane

import vehicle.Vehicle
import planeEngine.PlaneEngine
import town._
import cargo._

class Box(typeOf : String, maxLoad : Double) extends Cargo(typeOf, maxLoad) {}

//a class to represent planes
class Plane(name : String, engine : PlaneEngine, hold : Box) extends Vehicle(name, engine){
  override def unload(t : Town) = {
    t.pop += loading
  }
}
