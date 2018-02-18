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
    val pop : Int,
    val listofgoods :List[(String, Int)],
    val leaving_roads : List[(Town)],
    val coming_roads : List[(Town)],
    var pos : Point) {
      def position() : Point={pos}
      def Update_town (val coming: Train) {}
    }


class Train(val speed : Double){
    val passengers : Int
    val goodies : List[Goods]
    /*var loaded: Int = 0
    def loading(): Unit = {loaded = (0.1*passengers).toInt}*/
}

object Program
{
  def main(args: Array[String])= {
    val bordeaux = new Town("Bordeaux", 132, List(("bananes", 12), ("mures", 32)), new Point(4,5) )
    val agen = new Town("Agen", 132, List(("bananes", 12), ("mures", 32)), new Point(0,5) )
    val t = new Train(bordeaux, agen,1)
    val r = new Road(bordeaux, agen,Array(bordeaux.position(),agen.position()))
    val p = r.position(2.5)
    p._1.print()
	 }
}

method Update_town (val coming: Train) {
   


class Game
{
  val town1 = new Town( "Town1", 258, List(("lunettes",55), ("chats",8)), new Point(1,2))
  val town2 = new Town( "Town2", 562, List(("diamond",55), ("dogs",8)), new Point(1,22))
  val town3 = new Town( "Town3", 654, List(("paintit",55), ("black",8)), new Point(12,2))
  val town4 = new Town( "Town4", 156, List(("your",55), ("woman",8)), new Point(5,6))
  
  
 