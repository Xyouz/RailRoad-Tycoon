package circTown


import updatable._
import scalafx.Includes._
import scalafx.application.{JFXApp}
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import sendTrainDialog._
import model._
import town._


class CircTown(val master : JFXApp.PrimaryStage,val game : Game, val town : Town) extends Circle with Updatable{
  centerX = town.position().x_coord()
  centerY = town.position().y_coord()
  translateX = 500
  translateY = 300
  update()
  //onMouseClicked = handle {println(town)}
  fill = Orange

  override def update() = {
    radius = 10//town.population() / 5
  }

}
