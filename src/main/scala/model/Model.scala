package model

import town._
import road._
import point._
import train._
import good._
import trainEngine._
import planeEngine._
import scala.math._
import railMap._
import airportNetwork._
import moneyAlert._
import wagon._
import plane._
import cargo._


// Eventually a class to launch a game.
class Game(){

  val trainEngineList = List(new TrainEngine("Electric 2000", 15, 150, true, 11,1), new TrainEngine("Escargot", 5, 75, false,11,1.25))

  val planeEngineList = List(new PlaneEngine("TurboJet 42", 10, 50,175,11,1))

  var townList = Seq[Town]()
  var roadList = Seq[Road]()
  var railMap = new RailMap(townList, roadList)
  val airports = new AirportNetwork(this)

  def loadMap(towns : Seq[Town], roads : Seq[Road]) = {
    townList = towns
    roadList = roads
    railMap = new RailMap(townList, roadList)
  }

  var nbOfTown = townList.length

  var trainList = Seq[Train]()
  var planeList = Seq[Plane]()

  def addTrain(name : String, town : Town, engine : TrainEngine, wagons : List[Wagon]) = {
    if (money < engine.price){
      throw new NotEnoughMoneyException("train")
    }
    val newTrain = new Train(name,engine, wagons, this)
    newTrain.setDestination(town)
    money -= engine.price
    town.welcomeTrain(newTrain)
    trainList = trainList :+ newTrain
    newTrain
  }

  def addPlane(name : String, town : Town, engine : PlaneEngine, hold : Cargo) = {
    if (money < engine.price){
      throw new NotEnoughMoneyException("plane")
    }
    val newPlane = new Plane(name,engine, hold, this)
    newPlane.setDestination(town)
    newPlane.nextDest = town.getID
    money -= engine.price
    town.welcomePlane(newPlane)
    planeList = planeList :+ newPlane
    newPlane
  }

  var money = 10000000.0
  def deltaMoney(delta : Double) = {money = money + delta; println(delta)}


  // List of trains and the ID of the town they are currently in
  var trainsOnTransit = List[(Train, Int)]()
  var trainsToBeOnTransit = List[(Train,Int)]()

  def trainToBeDispatched(train : Train, localState : Int) = {
    trainsOnTransit = (train, localState) :: trainsOnTransit
  }
  // townID : current town where the train is.
  def dispatchTrain(train : Train, townID : Int) = {
    if (train.getDestination()== townID) {
      train.unload(townList(townID))
      townList(townID).loadTrain(train)
      train.nextDestination()
      }

    train.resetDistance()
    var road = railMap.nextRoad(townID, train.getDestination())
    var distance = railMap.distanceFromTo(townID, train.getDestination())
    if (distance * train.engine.priceByKm <= money) {
      road.launchTrain(train, townList(townID))
      money -= distance * train.engine.priceByKm
    }
    else {
      trainsToBeOnTransit = (train, townID) +: trainsToBeOnTransit
    }
  }



  def update() = {
    def trainMappingFun(t : (Train, Int)) = {
      dispatchTrain(t._1, t._2)
    }
    for (road <- roadList) {
      trainsOnTransit = trainsOnTransit ++ road.update()
    }
    planeList.map(_.update())
    trainsOnTransit.map(trainMappingFun(_))
    trainsOnTransit = trainsToBeOnTransit
    trainsToBeOnTransit = List[(Train,Int)]()
    townList.map(_.update())
  }

  def towns() = {townList}
  def roads() = {roadList}
}
