
package model

import town._
import road._
import point._
import train._
import good._
import trainEngine._
import planeEngine._
import scala.math._


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
    new Road(townList(5),townList(8)),
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

  var nbOfTown = townList.length

  var trainList = Seq[Train]()
  def addTrain(train : Train) = {trainList += train}

  var money = 0.0
  def deltaMoney(delta : Double) = {money = money + delta}

  //we need to define a function to find the shortest path between two towns, not necessarilly assuming that the graph of the towns is connex. We chose the algorithm of Floyd-Warshall.
  def shortestPath(towns : Seq[Town], roads : Seq[Road]) : Array[Array[(Road,Town,Double)]] = {
    val nt = towns.length
    val nr = roads.length
    val inf = Double.PositiveInfinity
    val matrix = Array.fill[(Int, Int, Double)](nt,nt)((-1,-1,inf))

     // a function that maps the first two integers of the tuples with the road and the town that respectively correspond to those numbers.
    def maps(mat1 : Array[ Array[(Int, Int, Double)]]) : Array[Array[(Road, Town, Double)]] = {
      val mat2 = Array.ofDim[(Road,Town, Double)](nt,nt)
      for (i <- 0 until nt) {
        for (j <- 0 until nt) {
          mat2(i)(j) = (roads(max(0,mat1(i)(j)._1)), towns(max(0,mat1(i)(j)._2)), mat1(i)(j)._3 )
        }
      }
      mat2
    }

    for (i <- 0 until nt) {
      matrix(i)(i) = (0,i,0)
    }

    for (i <- 0 until nr) {
      var j = roads(i).getEnd.getID()
      var k = roads(i).getStart.getID()
      var l = roads(i).length
      matrix(j)(k) = (i,k,l)
      matrix(k)(j) = (i,j,l)
    }


    //Algorithm of Floyd-warshall itself

    for (k <- 0 until nt){
      for (i <- 0 until nt){
        for (j <- 0 until nt){
          if (matrix(i)(j)._3 > matrix(i)(k)._3 + matrix(k)(j)._3) {
            var d = matrix(i)(k)._3 + matrix(k)(j)._3
            var r = matrix(i)(k)._1
            var n = matrix(i)(k)._2
            matrix(i)(j) = (r,n,d)
          }
        }
      }
    }
    maps(matrix)
  }

  val dispatchMatrix = shortestPath(townList, roadList)
   // List of trains and the ID of the town they are currently in
  var trainsOnTransit = List[(Train, Int)]()

  def trainToBeDispatched(train : Train, localState : Int) = {
    trainsOnTransit = (train, localState) :: trainsOnTransit
  }

  // townID : current town where the train is.
  def dispatchTrain(train : Train, townID : Int) = {
    if (train.getDestination()== townID) {
      townList(townID).welcomeTrain(train)
      }
    else {
      train.resetDistance();
      var info = dispatchMatrix(townID)(train.getDestination())
      println(s"${train.toString()} redispatché vers ${info._2}")
      (info._1).launchTrain(train,info._2)
    }
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
