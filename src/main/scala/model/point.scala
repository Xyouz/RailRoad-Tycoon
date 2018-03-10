package point

import scala.math.{pow, sqrt}

// A class to represent 2D points
class Point(var x : Double,var y : Double){
  def x_coord():Double = {x}
  def y_coord():Double = {y}
  override def toString() = s"x : $x ; y : $y"
  def print():Unit = {println(s"x : $x ; y : $y\n")}
  def +(that: Point) = {new Point(x + that.x, y + that.y)}  // `this` might be useful
  def -(that: Point) = {new Point(x - that.x, y - that.y)}
  def scale(scalar : Double) = {new Point(scalar * x, scalar * y)}
  def norm() = {this.distance(new Point(0,0))}
  def distance(p: Point): Double = {
    sqrt(pow(x-p.x_coord(),2) + pow(y-p.y_coord(),2))
  }
  def normalize()= {
    if (norm() != 0) { this.scale(1/norm()) }
    this
  }
}
