package circTown

import scalafx.Includes._
import scalafx.application.{JFXApp}
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import sendTrainDialog._
import model._
import town._


class CircTown(val master : JFXApp.PrimaryStage,val game : Game, val town : Town) extends Circle{
  centerX = town.position().x_coord()
  centerY = town.position().y_coord()
  radius = town.population() / 5
  onMouseClicked = handle {sendTrain(town)}
  fill = Orange

  // Used to initialise a window to send trains out of a city
  def sendTrain(startTown: Town): Unit = {
    val dialogWindow= new SendTrainDialog(master, game, startTown)
    val res = dialogWindow.showAndWait()
    res match {
      case _ => ()
    }
  }
}
