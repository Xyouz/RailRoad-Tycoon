package gui

import model._
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Circle,Rectangle,Line}
import scalafx.beans.property.DoubleProperty


class MainGame(val game: Game) extends JFXApp.PrimaryStage
  {
    def townToCircle(Town : Town) : Circle = {
      new Circle {
        val town = Town
        centerX = town.position().x_coord()
        centerY = town.position().y_coord()
        radius = town.population() / 5
        onMouseClicked = { ae => println(s"$town.getName() :");
                                  println(s"   $town.population()");
                                town.incrPop()}
        }
    }

    title.value = "Roolraid Tycoan"
    width = 800
    height = 600
    scene = new Scene{
      fill = LightGreen
      val nodeTowns = game.towns().map(townToCircle(_))
      content = nodeTowns
    }
  }
