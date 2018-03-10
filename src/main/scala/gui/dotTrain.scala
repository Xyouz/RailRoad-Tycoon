package dotTrain

import updatable._
import scalafx.Includes._
import scalafx.application.{JFXApp}
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import sendTrainDialog._
import model._
import town._
import train._

class CircTrain(val train : Train) extends Circle with Updatable{
  radius = 10
  fill = DarkCyan
  update()

  override def update() = {
    var p = train.position
    centerX = p.x_coord()
    centerY = p.y_coord()
  }
}