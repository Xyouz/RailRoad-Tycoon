package circTown


import updatable._
import scalafx.Includes._
import scalafx.application.{JFXApp}
import scalafx.scene.shape.{Circle}
import scalafx.scene.effect.Glow
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{Stops, LinearGradient}
import sendTrainDialog._
import model._
import town._
import scalafx.scene.shape.StrokeType.Outside
import zoom.Zoom
/** This class represents the towns by points on the interface.
*/


class CircTown(val town : Town, val zoom : Zoom) extends Circle with Updatable{
  update()
  stroke = White opacity 1
  strokeWidth = 0
  strokeType = Outside
  fill = Orange
  if (town.hasAirport){
    fill = new LinearGradient(endX = 0,
              stops = Stops(Orange, MidnightBlue))
  }

  override def update() = {
    val (x,y) = zoom.transform(town.position().x_coord(),town.position().y_coord())
    centerX = x
    centerY = y
    radius = 8
    if (town.isHub) {
      strokeWidth = 3
    }
    else {
      strokeWidth = 0
    }
  }

}
