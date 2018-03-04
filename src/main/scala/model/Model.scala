
package model

import scala.math._
import scalafx.beans.property.{ DoubleProperty}



// A class to represent 2D points
class Point(var x : Double,var y : Double){
  def x_coord():Double = {x}
  def y_coord():Double = {y}
  def print():Unit = {println(s"x : $x ; y : $y\n")}
  def +(that: Point) = {new Point(x + that.x, y + that.y)}  // `this` might be useful
  def -(that: Point) = {new Point(x - that.x, y - that.y)}
  def scale(scalar : Double) = {new Point(scalar * x, scalar * y)}
  def distance(p: Point): Double = {
    sqrt(pow(x-p.x_coord(),2) + pow(y-p.y_coord(),2))
  }
  def normalize{
    val norm = this.distance(new Point(0,0))
    if (norm != 0) { x = x / norm ; y = y / norm }
  }
}


// A class to represent road between two Towns
class Road(val begin : Town,val end : Town){
  val length = begin.position.distance(end.position)
  var trainsAB = Seq[Train]()
  var trainsBA = Seq[Train]()
  def getStart() = {begin}
  def getEnd() = {end}
  def update() =
    {
      trainsAB.map(_.update())
      trainsBA.map(_.update())
      for (i <- 0 until trainsAB.length)
      {
        if (trainsAB(i).getDestination()== getEnd())
        {
          trainsAB(getEnd()).welcomeTrain(trainsAB(i))
        }
        else
        {
          train.resetDistance();
          (dispatchMatrix(getEnd())(trainsAB(i).getDestination())._1).launchTrain(trainsAB(i),dispatchMatrix(getEnd())(trainsAB(i).getDestination())._2)
        }
      }
  }

  def launchTrain(train : Train, destination : Town) =
    {
      if (destination == end)
        {
          trainsAB :+ train
        }
      else
        {
          trainsBA :+ train
        }
    }
}

//a class to specify the goods that are available : it returns their name and their price.
class Goods (val namegoods : String, val pricetag : Double ){}



// a class to implement the towns of the graphs with information on the name, the population, their wealth and methods to update them when a train come over
class Town(val id : Int,
    val name: String,
    var pop : Int,
    val listofgoods :List[Goods],
    // val leaving_roads : List[(Town)],
    // val coming_roads : List[(Town)],
    var pos : Point)
    {
      def getID() : Int = {id}
      def getName() : String = {name}
      def position() : Point={pos}
      def population() : Int={pop}
      def incrPop() = {pop = pop+50}
      def update(){
        pop += 20
      }
      def welcomeTrain(train : Train) = {}
    }



//a class to represent the train, for a train, we need to know its speed, its destination, and we need a way to update its information
class Train(val speed : Double, val name : String){
    var distanceOnRoad : Double = -1
    var destination = -1 // L'ID de la destination
    def update() = { if (distanceOnRoad >= 0) {distanceOnRoad += speed};
                      distanceOnRoad}
    def resetDistance() = {distanceOnRoad = 0}
    def getDestination() = {destination}
    def getName() = {name}
}


// Eventually a class to launch a game.
class Game()
{
  val townList = Seq[Town](new Town(1, "Town1", 258, List(new Goods("lunettes",55), new Goods("chats",8)), new Point(300,150)) ,
      new Town(2, "Town2", 562, List(new Goods("diamond",55), new Goods("dogs",8)), new Point(100,200)) ,
      new Town(3, "Town3", 654, List(new Goods("paintit",55), new Goods("black",8)), new Point(500,400)) ,
      new Town(4, "Town4", 156, List(new Goods("your",55), new Goods("uranus",8)), new Point(120,450)))
  var roadList = Seq[Road](new Road(townList(1),townList(2)) ,
      new Road(townList(2),townList(3)))

  val nbOfTown = townList.length

//we need to define a function to find the shortest path between two towns, not necessarilly assuming that the graph of the towns is connex. We chose the algorithm of Floyd-Warshall.
def shortestPath(towns : Seq[Town], roads : Seq[Road]) : Array[Array[(Road,Town,Double)]] =
  {
    val nt = towns.length
    val nr = roads.length
    val inf = Int.MaxValue
    val matrix = Array.fill[(Int, Int, Double)](nt,nt)((-2,-2,inf))

     // a function that maps the first two integers of the tuples with the road and the town that respectively correspond to those numbers.
    def maps(mat1 : Array[ Array[(Int, Int, Double)]]) : Array[Array[(Road, Town, Double)]] = {
      val mat2 = Array.ofDim[(Road,Town, Double)](nt,nt)
      for (i <- 0 until nt) {
        for (j <- 0 until nt) {
          mat2(i)(j) = (roads(mat1(i)(j)._1), towns(mat1(i)(j)._2), mat1(i)(j)._3 )
        }
      }
      mat2
    }

    for (i <- 0 until nt) {matrix(i)(i) = (0,i,0)}

    //Algorithm of Floyd-warshall itself

    for (k <- 0 to nt){
      for (i <- 0 to nt){
        for (j <- 0 to nt){
          if (matrix(i)(j)._3 > matrix(i)(k)._3 + matrix(k)(j)._3) {
            matrix(i)(j) = matrix(i)(j).copy(_3 = matrix(i)(k)._3 + matrix(k)(j)._3)
          }
        }
      }
    }
    maps(matrix)
    }

  val dispatchMatrix = shortestPath(townList, roadList)

  // townID : current town where the train is.
  def dispatchTrain(train : Train, townID : Int) =
    {
        if (train.getDestination()== townID)
        {
          townList(townID).welcomeTrain(train)
        }
        else
        {
          train.resetDistance();
          (dispatchMatrix(townID)(train.getDestination())._1).launchTrain(train,dispatchMatrix(townID)(train.getDestination())._2)
        }
    }

  def update() =
  {
    var trainsOnArrival = roadList.map(_.update())
    townList.map(_.update())
  }

  def towns() = {townList}
  def roads() = {roadList}
}
