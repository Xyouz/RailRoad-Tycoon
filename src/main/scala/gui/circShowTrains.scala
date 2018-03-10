package circShowTrains

import updatable._
import scalafx.application.{JFXApp}
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import model._
import town._


class CircShowTrain(val master : JFXApp.PrimaryStage,val game : Game, val town : Town) extends Circle with Updatable {
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
