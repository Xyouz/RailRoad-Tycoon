package point

import scala.math.{pow, sqrt}

/** Since we are dealing with maps in 2D;
 * this class descibes several methods to handle the relations between the towns (the points) with eah other (the distance between two towns for example).
*/

class Point(var x : Double,var y : Double){
  def x_coord():Double = {x}
  def y_coord():Double = {y}
  override def toString() = s"x : $x ; y : $y"
  def print():Unit = {println(s"x : $x ; y : $y\n")}
  def +(that: Point) = {new Point(x + that.x, y + that.y)}
  def -(that: Point) = {new Point(x - that.x, y - that.y)}
  def scale(scalar : Double) = {new Point(scalar * x, scalar * y)}
  def norm() = {this.distance(new Point(0,0))}
  def distance(p: Point): Double = {
    sqrt(pow(x-p.x_coord(),2) + pow(y-p.y_coord(),2))
  }
  def normalize()= {
    if (norm() != 0) { this.scale(1/norm()) }
    else { this }
  }
}
