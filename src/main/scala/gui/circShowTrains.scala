package circShowTrains

import scalafx.Includes._
import scalafx.application.{JFXApp}
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import sendTrainDialog._
import model._
import town._


class CircShowTrain(val master : JFXApp.PrimaryStage,val game : Game, val town : Town) extends Circle{
  centerX = town.position().x_coord()
  centerY = town.position().y_coord()
  fill = Red
  update()

  def update(){
    var delta = 0
    if (town.hasTrains()) {delta = 5}
    radius = town.population() / 5 + delta
  }
}
