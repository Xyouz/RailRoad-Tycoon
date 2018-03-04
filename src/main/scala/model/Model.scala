
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
class Road(val begin : Town,val end : Town){//,val route : Array[Point]){
  // changer la distance pour prendre en compte la forme
  val length = begin.position.distance(end.position)
  var trainsAB = Seq[Train]()
  var trainsBA = Seq[Train]()
  def getStart() = {begin}
  def getEnd() = {end}
  // def position(done : Double) : (Point, Point) = {
  //   if (done >= length) {(end.position, (new Point(0,0)))}
  //   else if (done <= 0) {(begin.position, (new Point(0,0)))}
  //   else
  //   {
  //     var dist : Double = 0
  //     var index : Int = 0
  //     while (index < route.size && dist <= done){
  //       dist = dist + route(index).distance(route(index + 1))
  //       index += 1
  //     }
  //     index -= 1
  //     var remaining = done - dist + route(index).distance(route(index + 1))
  //     val vec = (route(index+1)-route(index))
  //     vec.normalize
  //     (route(index)+vec.scale(remaining),vec)
  //   }
  // }
  def update() =
    {
      trainsAB.map(_.update())
      trainsBA.map(_.update())
    }
  def launchTrain(train : Train, destination : Int) =
    {
      if (destination == end.getID())
        {
          trainsAB :+ train
        }
      else
        {
          trainsBA :+ train
        }
    }
}


class Goods (val namegoods : String, val pricetag : Double ){}


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
      /*def Update_town(coming:Train) {
        // Town.pop = Town.pop + Train.passengers
        // Town.listofgoods = Town.listofgoods++Train.goodies
      }*/
      def update(){
        pop += 20
      }
      def welcomeTrain(train : Train) = {}
    }


class Train(val speed : Double){
    var distanceOnRoad : Double = -1
    var destination = -1 // L'ID de la destination
    def update() = { if (distanceOnRoad >= 0) {distanceOnRoad += speed};
                      distanceOnRoad}
    def resetDistance() = {distanceOnRoad = 0}
    def getDestination() = {destination}
    //val passengers : Int
    //val goodies : List[Goods]
    /*var loaded: Int = 0
    def loading(): Unit = {loaded = (0.1*passengers).toInt}*/
}




class Game()
{
  val townList = Seq[Town](new Town(1, "Town1", 258, List(new Goods("lunettes",55), new Goods("chats",8)), new Point(300,150)) ,
      new Town(2, "Town2", 562, List(new Goods("diamond",55), new Goods("dogs",8)), new Point(100,200)) ,
      new Town(3, "Town3", 654, List(new Goods("paintit",55), new Goods("black",8)), new Point(500,400)) ,
      new Town(4, "Town4", 156, List(new Goods("your",55), new Goods("woman",8)), new Point(120,450)))
  var roadList = Seq[Road](new Road(townList(1),townList(2),Array(townList(1).position(),townList(2).position())) ,
      new Road(townList(2),townList(3),Array(townList(2).position(),townList(3).position())))

  val nbOfTown = townList.length

def shortestPath(towns : Seq[Town], roads : = roadList : Seq[Road]) : Array[(Int,Int,Double)] =
  {
    val nt = towns.length
    val nr = roads.length
    val matrix = Array.fill[(Int,Int,Double)](n,n)((-2,-2,Double.MaxValue))

    for (i <- O until nt) {matrix(i)(i) = (-1,-1,0)}

    for (i <- 0 until nr) {var r = roads(i)} // to be continued
  }

  val dispatchMatrix = Array.ofDim[(Int,Int,Double)](nbOfTown, nbOfTown)  // Appliquer Djiekstra ou autre pour obtenir une matrice
                           // qui puisse nous permettre de savoir où aller chaque case,
                           // le numéro de la route et la destination suivante
                           // type matrix of int*int
                           // routeID,nextTownID

  // townID : ville où le train se trouve actuellement
  def dispatchTrain(train : Train, townID : Int) =
    {
        if (train.getDestination()== townID)
        {
          townList(townID).welcomeTrain(train)
        }
        else
        {
          train.resetDistance()
          roadList(dispatchMatrix(townID)(train.getDestination())._1).launchTrain(train,dispatchMatrix(townID)(train.getDestination())._2)
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
