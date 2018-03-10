package gui

import town._
import road._
import point._
import train._
import model._
import circTown._
import lineRoad._
import circShowTrains._
import dotTrain._
import scalafx.Includes._
import updatable._
import newTrainDialog._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Circle,Rectangle,Line,Polygon}
import scalafx.beans.property.{DoubleProperty, StringProperty}
import scalafx.animation.AnimationTimer
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.image.{Image, ImageView}
import scalafx.geometry.{Insets, Pos}

class MainGame(val game: Game) extends JFXApp.PrimaryStage
  { stage =>

    def townToCircle(town : Town) : CircTown = {
      new CircTown(stage, game, town)
    }

    def showTrainCircle(town : Town) : CircShowTrain = {
      new CircShowTrain(town)
    }

    def roadToLine(road : Road) : LineRoad = {
      new LineRoad(stage, road)
    }

    def trainToDot(train : Train) : CircTrain = {
        new CircTrain(train)
    }

    title.value = "Roolraid Tycoan"
    width = 1000
    height = 700
    //content = new Button("Hell World")
    scene = new Scene{
      fill = LightGreen

      val nodeTowns = game.towns().map(townToCircle(_))
      val edgeRoads = game.roads().map(roadToLine(_))
      val townsWithTrains = game.towns().map(showTrainCircle(_))

      var toBeDrawn = edgeRoads ++ townsWithTrains ++
        nodeTowns ++
        Seq(new UpdatableButton(){
              text = "New Train"
              onAction = handle {newTrainWindow()}
              layoutX <== stage.width-width
              layoutY = 0
            },
            new UpdatableButton(){
              text = "Au revoir"
              onAction = { ae => stage.close() }
              layoutX <== stage.width-width
              layoutY = 25
            },
            new UpdatableLabel(){
              text = s"Argent : ${game.money}€"
              layoutX <== stage.width-width -25
              layoutY <== stage.height - 75
              override def update() = {text = s"Argent : ${game.money}€"}
            })

      content = toBeDrawn

      // update what ownis drawn on the screen
      def drawScene() = {
        toBeDrawn.map(_.update())
      }


      // tick every 1/10 of a second
      var (lastTick : Long) = 0
      val updateTick = AnimationTimer (t => {
        if ((t-lastTick)>=200000000){  // Allow to choose the duration
          lastTick = t                  // between two updates
          game.update()
          drawScene()}
      })
      updateTick.start()


      // use to create an interactive window in order to create new trains
      def newTrainWindow(): Unit = {
        val dialog = new newTrainDialog(stage,game)

        val res = dialog.showAndWait()
        res match {
          case _ => ()
        }
      }
    }
  }
