package gui

import model._
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox,VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Circle,Rectangle,Line}
import scalafx.beans.property.DoubleProperty
import scalafx.animation.AnimationTimer
import scalafx.scene.control.{Button, Label}

class MainGame(val game: Game) extends JFXApp.PrimaryStage
  { stage =>
    def townToCircle(Town : Town) : Circle = {
      new Circle {
        val town = Town
        centerX = town.position().x_coord()
        centerY = town.position().y_coord()
        radius = town.population() / 5
        onMouseClicked = { ae => println(s"${town.getName()} :");
                                  println(s"   ${town.population()}");
                                town.incrPop();
                              radius = town.population() / 5}
        fill <== when(hover) choose {Yellow} otherwise Orange
        }
    }

    def roadToLine(Road : Road) : Line = {
      new Line {
        val road = Road
        startX = road.getStart().position().x_coord()
        startY = road.getStart().position().y_coord()
        endX = road.getEnd().position().x_coord()
        endY = road.getEnd().position().y_coord()
      }
    }


    title.value = "Roolraid Tycoan"
    width = 800
    height = 600
    //content = new Button("Hell World")
    scene = new Scene{
      fill = LightGreen

      val nodeTowns = game.towns().map(townToCircle(_))
      val edgeRoads = game.roads().map(roadToLine(_))

      var (lastTick : Long) = 0
      val updateTick = AnimationTimer (t => {
        if ((t-lastTick)>=1000000000){  // Allow to choose the duration
          lastTick = t                  // between two updates
          println(s"${t/1000000000}")}
      })
      updateTick.start()

      content = edgeRoads ++ nodeTowns ++ Seq(new Button("Hell World"),new Button("Au revoir"){onAction = { ae => stage.close() }}/*,new Button("Monde de merde")*/)

  }
}
