
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


// Eventually a class to launch a game.
class Game()
{
  val trainEngineList = List(new TrainEngine("Electric 2000", 15, 150, true, 11), new TrainEngine("Escargot", 5, 75, false,11))

  val planeEngineList = List(new PlaneEngine("TurboJet 42", 35, 50,11))

  var goodsList = List(new Good("lunettes",55), new Good("chats",8),List(new Good("lunettes",55), new Good("chats",8)),
                      new Good("diamond",55), new Good("dogs",8), new Good("paintit",55), new Good("black",8) )

  val townList = Seq[Town](
      new Town(0, "Bordeaux", 258, List(new Good("Toto",42)), new Point(275,600)) ,
      new Town(1, "Paris", 450, List(new Good("Toto",42)), new Point(600,300)) ,
      new Town(2, "Marseille", 350, List(new Good("Toto",42)), new Point(450,500)) ,
      new Town(3, "Lyon", 350, List(new Good("Toto",42)), new Point(120,450)),
      new Town(4, "Toulouse", 400, List(new Good("Toto",42)),new Point(775,150)),
      new Town(5, "Rennes", 200, List(new Good("Toto",42)),new Point(350,90)),
      new Town(6, "Clermont-Ferrand", 250, List(new Good("Toto",42)),new Point(920,400)),
      new Town(7, "Nancy", 150, List(new Good("Toto",42)),new Point(300,300)),
      new Town(8, "Angoulême", 42, List(new Good("Toto",42)),new Point(42,42)),
      new Town(9, "Nice", 200, List(new Good("Toto",42)),new Point(200,220)),
      new Town(10, "Strasbourg", 250, List(new Good("Toto",42)),new Point(880,600)))

  var roadList = Seq[Road](
    new Road(townList(5),townList(9)),
    new Road(townList(9),townList(1)),
    new Road(townList(3),townList(7)),
    new Road(townList(0),townList(3)),
    new Road(townList(1),townList(2)),
    // new Road(townList(5),townList(8)),
    new Road(townList(6),townList(10)),
    new Road(townList(1),townList(10)),
    new Road(townList(0),townList(10)),
    new Road(townList(4),townList(10)),
    new Road(townList(5),townList(4)),
    new Road(townList(3),townList(2)),
    new Road(townList(7),townList(1)),
    new Road(townList(9),townList(3)),
    new Road(townList(1),townList(4)),
    new Road(townList(2),townList(10)))

  var railmap = new RailMap(townList, roadList)

  var nbOfTown = townList.length

  var trainList = Seq[Train]()

  def addTrain(name : String, town : Town, engine : TrainEngine) = {
    if (money < engine.price){
      throw new NotEnoughMoneyException("train")
    }
    val newTrain = new Train(name,engine)
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
    train.resetDistance();
    var info = railmap.dispatchMatrix(townID)(train.getDestination())
    (info._1).launchTrain(train,info._2)
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
