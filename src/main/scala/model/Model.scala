
package model

import scala.math._

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
class Road(val begin : Town,val end : Town,val route : Array[Point]){
  // changer la distance pour prendre en compte la forme
  val length = begin.position.distance(end.position)
  def position(done : Double) : (Point, Point) = {
    if (done >= length) {(end.position, (new Point(0,0)))}
    else if (done <= 0) {(begin.position, (new Point(0,0)))}
    else
    {
      var dist : Double = 0
      var index : Int = 0
      while (index < route.size && dist <= done){
        dist = dist + route(index).distance(route(index + 1))
        index += 1
      }
      index -= 1
      var remaining = done - dist + route(index).distance(route(index + 1))
      val vec = (route(index+1)-route(index))
      vec.normalize
      (route(index)+vec.scale(remaining),vec)
    }
  }
}


class Goods (val namegoods : String, val pricetag : Double ){}


class Town(val name: String,
    var pop : Int,
    val listofgoods :List[Goods],
    // val leaving_roads : List[(Town)],
    // val coming_roads : List[(Town)],
    var pos : Point)
    {
      def getName() : String = {name}
      def position() : Point={pos}
      def population() : Int={pop}
      def incrPop() = {pop = pop+50}
      def Update_town (coming:Train) {
        // Town.pop = Town.pop + Train.passengers
        // Town.listofgoods = Town.listofgoods++Train.goodies
      }
    }


class Train(val speed : Double){
    //val passengers : Int
    //val goodies : List[Goods]
    /*var loaded: Int = 0
    def loading(): Unit = {loaded = (0.1*passengers).toInt}*/
}




class Game()
{
  var townList = Seq[Town]()
  var roadList = Seq[Road]()

  def init() =
  {
    townList :+  new Town( "Town1", 258, List(new Goods("lunettes",55), new Goods("chats",8)), new Point(1,2)) :+ new Town( "Town2", 562, List(new Goods("diamond",55), new Goods("dogs",8)), new Point(1,22)) :+ new Town( "Town3", 654, List(new Goods("paintit",55), new Goods("black",8)), new Point(12,2)) :+ new Town( "Town4", 156, List(new Goods("your",55), new Goods("woman",8)), new Point(5,6))
    roadList :+ new Road(townList(1),townList(2),Array(townList(1).position(),townList(2).position())) :+ new Road(townList(2),townList(3),Array(townList(2).position(),townList(3).position()))
  }

  def update() =
  {

  }

  def towns() = {townList}
  def roads() = {roadList}
}
