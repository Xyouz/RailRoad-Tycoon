package train

import vehicle.Vehicle
import trainEngine.TrainEngine

import wagon._
//a class to represent trains
class Train(name : String, engine : TrainEngine, listOfWagon : List[Wagon]) extends Vehicle(name, engine){

}
