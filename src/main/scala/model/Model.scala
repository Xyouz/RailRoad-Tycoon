
package model

import model.town
import model.road
import model.point
import model.train
import scala.math._


// Eventually a class to launch a game.
class Game()
{
  var goodsList = List(new Goods("lunettes",55), new Goods("chats",8),List(new Goods("lunettes",55), new Goods("chats",8)),
                      new Goods("diamond",55), new Goods("dogs",8), new Goods("paintit",55), new Goods("black",8) )
  val townList = Seq[Town](new Town(0, "Bordeaux", 258, List(new Goods("Toto",42)), new Point(275,225)) ,
      new Town(1, "Paris", 500, List(new Goods("Toto",42)), new Point(800,200)) ,
      new Town(2, "Marseille", 350, List(new Goods("Toto",42)), new Point(500,550)) ,
      new Town(3, "Lyon", 350, List(new Goods("Toto",42)), new Point(120,450)),
      new Town(4, "Toulouse", 400, List(new Goods("Toto",42)),new Point(700,500)),
      new Town(5, "Rennes", 200, List(new Goods("Toto",42)),new Point(650,110)),
      new Town(6, "Clermont-Ferrand", 250, List(new Goods("Toto",42)),new Point(900,400)),
      new Town(7, "Nancy", 150, List(new Goods("Toto",42)),new Point(200,80)),
      new Town(8, "Angoulême", 42, List(new Goods("Toto",42)),new Point(842,42)),
      new Town(9, "Nice", 200, List(new Goods("Toto",42)),new Point(425,320)),
      new Town(10, "Strasbourg", 250, List(new Goods("Toto",42)),new Point(900,600)))

  var roadList = Seq[Road](new Road(townList(5),townList(9)),
    new Road(townList(7),townList(3)),
    new Road(townList(9),townList(1)),
    new Road(townList(3),townList(7)),
    new Road(townList(0),townList(3)),
    new Road(townList(5),townList(8)),
    new Road(townList(1),townList(2)),
    new Road(townList(6),townList(10)),
    new Road(townList(7),townList(10)),
    new Road(townList(1),townList(10)),
    new Road(townList(10),townList(0)),
    new Road(townList(4),townList(10)),
    new Road(townList(5),townList(4)),
    new Road(townList(3),townList(2)),
    new Road(townList(7),townList(1)),
    new Road(townList(9),townList(3)),
    new Road(townList(1),townList(4)),
    new Road(townList(2),townList(10)))

  var nbOfTown = townList.length

  var money = 0.0
  def deltaMoney(delta : Double) = {money = money + delta}

  //we need to define a function to find the shortest path between two towns, not necessarilly assuming that the graph of the towns is connex. We chose the algorithm of Floyd-Warshall.
  def shortestPath(towns : Seq[Town], roads : Seq[Road]) : Array[Array[(Road,Town,Double)]] = {
    val nt = towns.length
    val nr = roads.length
    val inf = Int.MaxValue
    val matrix = Array.fill[(Int, Int, Double)](nt,nt)((-2,-2,inf))

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
            matrix(i)(j) = (r,k,d)
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
