package circShowTrains

import updatable._
import scalafx.Includes._
import scalafx.application.{JFXApp}
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import model._
import town._

/** This class represents the moving trains by points, so the player can actually see the trains on the interface.
*/

class CircShowTrain(val town : Town) extends Circle with Updatable {
  centerX = town.position().x_coord()
  centerY = town.position().y_coord()
  fill = Red
  var delta = 0
  update()

  override def update() = {
    delta = 0
    if (town.hasTrains()) {delta = 5}
    radius = town.population() / 5 + delta
  }
}
