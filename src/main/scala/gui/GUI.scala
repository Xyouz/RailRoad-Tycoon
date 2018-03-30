package gui

import moneyAlert._
import chooseTrainDialog._
import town._
import road._
import point._
import train._
import model._
import circTown._
import infoPane._
import infoWidget._
import lineRoad._
import circShowTrains._
import scalafx.Includes._
import updatable._
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
import wagon._
class MainGame(val game: Game) extends JFXApp.PrimaryStage
  { stage =>

    def townToCircle(town : Town) : CircTown = {
      new CircTown(stage, game, town)
    }

    def roadToLine(road : Road) : LineRoad = {
      new LineRoad(stage, road)
    }

    val infoWidget = new InfoWidget(stage, game)




    title.value = "Roolraid Tycoan"
    width = 1250
    height = 700
    //content = new Button("Hell World")

    val nodeTowns = game.towns().map(townToCircle(_))
    val edgeRoads = game.roads().map(roadToLine(_))
    // val townsWithTrains = game.towns().map(showTrainCircle(_))
    val townsLabel = game.towns().map(
      t => new UpdatableLabel(){
        text = t.toString()
        layoutX = t.position().x_coord() + 500
        layoutY = t.position().y_coord() + 300
      })



    var toBeDrawn = edgeRoads ++ nodeTowns ++ townsLabel ++
      Seq(new UpdatableButton(){
            text = "Au revoir"
            onAction = { ae => stage.close() }
            layoutX <== stage.width-width
            layoutY = 0
          },
          infoWidget
        )


    def addToBeDrawn(newItem : scalafx.scene.Node with updatable.Updatable) = {
      toBeDrawn = toBeDrawn :+ newItem
    }

    scene = new Scene{
      fill = LightGreen

      content = toBeDrawn

      // update what is drawn on the screen
      def drawScene() = {
        toBeDrawn.map(_.update())
      }

      // tick every 1/10 of a second
      var (lastTick : Long) = 0
      val updateTick = AnimationTimer (t => {
        if ((t-lastTick)>=40000000){  // Allow to choose the duration
          lastTick = t                  // between two updates
          game.update()
          content = toBeDrawn
          drawScene()}
      })
      updateTick.start()

    }
  }
