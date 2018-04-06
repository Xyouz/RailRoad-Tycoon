package dotVehicle

import updatable._
import scalafx.Includes._
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import vehicle.Vehicle

class CircVehicle(val vehicle : Vehicle) extends Circle with Updatable{
  radius = 4
  update()

  override def update() = {
    var p = vehicle.position
    centerX = p.x_coord()
    centerY = p.y_coord()
    fill = vehicle.color
  }
}
