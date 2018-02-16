import scala.math._

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
