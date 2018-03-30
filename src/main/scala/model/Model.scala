
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
import moneyAlert._
import wagon._

// Eventually a class to launch a game.
class Game()
{

  val trainEngineList = List(new TrainEngine("Electric 2000", 15, 150, true, 11), new TrainEngine("Escargot", 5, 75, false,11))

  val planeEngineList = List(new PlaneEngine("TurboJet 42", 35, 50,11))

  var goodsList = List(new Good("lunettes",55), new Good("chats",8),List(new Good("lunettes",55), new Good("chats",8)),
                      new Good("diamond",55), new Good("dogs",8), new Good("paintit",55), new Good("black",8) )

  var townList = Seq[Town]()
  var roadList = Seq[Road]()
  var railMap = new RailMap(townList, roadList)

  def loadMap(towns : Seq[Town], roads : Seq[Road]) = {
    townList = towns
    roadList = roads
    railMap = new RailMap(townList, roadList)
  }

  var nbOfTown = townList.length

  var trainList = Seq[Train]()

  def addTrain(name : String, town : Town, engine : TrainEngine, wagons : List[Wagon]) = {
    if (money < engine.price){
      throw new NotEnoughMoneyException("train")
    }
    val newTrain = new Train(name,engine, wagons)
    newTrain.setDestination(town)
    money -= engine.price
    town.welcomeTrain(newTrain)
    println("Il faudra changer la gestion de la ville dans laquelle va le train")
    trainList = trainList :+ newTrain
    newTrain
  }

  var money = 56.0
  def deltaMoney(delta : Double) = {money = money + delta}


  // List of trains and the ID of the town they are currently in
  var trainsOnTransit = List[(Train, Int)]()

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
    road.launchTrain(train, townList(townID))
    //var info1 = railmap.dispatchMatrix(townID)(train.getDestination())
    //(info._1).launchTrain(train,info._2)
    //var info = railmap.matrixLength(townID)(train.getDestination())
  }

  def update() = {
    def mappingFun(t : (Train, Int)) = {
      dispatchTrain(t._1, t._2)
    }
    for (road <- roadList) {
      trainsOnTransit = trainsOnTransit ++ road.update()
    }
    trainsOnTransit.map(mappingFun(_))
    trainsOnTransit = List[(Train,Int)]()
    townList.map(_.update())
  }

  def towns() = {townList}
  def roads() = {roadList}
}
