package circVehicle

import updatable._
import scalafx.Includes._
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import vehicle.Vehicle
import zoom.Zoom

/** This class turns the different vehicles into point to enable the player to see where are those vehicles on the interface.
*/

class CircVehicle(val vehicle : Vehicle, val zoom : Zoom) extends Circle with Updatable{
  radius = 4
  update()

  override def update() = {
    val p = vehicle.position
    val (x,y) = zoom.transform(p.x,p.y)
    centerX = x
    centerY = y
    fill = vehicle.color
  }
}
