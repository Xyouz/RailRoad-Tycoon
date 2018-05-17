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
import wagon._

import scalafx.Includes._
import updatable._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Circle,Rectangle,Line,Polygon}
import scalafx.scene._
import scalafx.beans.property.{DoubleProperty, StringProperty}
import scalafx.animation.AnimationTimer
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.image.{Image, ImageView}
import scalafx.geometry.{Insets, Pos}
import scalafx.geometry._
import scalafx.event._
import scalafx.event.EventHandler
import scalafx.scene.input.ScrollEvent
import scalafx.scene.input.MouseEvent
import zoom.Zoom

/** Here is the function that handles the graphical user interface.
 * Through most of the functions we define, it actually draws all the interface,
 * making it corresponds to the maps.
 * It also deals with the ticks of the game.
*/


class MainGame(val game: Game) extends JFXApp.PrimaryStage {
    stage =>

    val zoom = new Zoom(width, height)
    val (maxX, minX, maxY, minY) = game.bounds()
    zoom.maxX = (maxX + 25)
    zoom.minX = (minX - 25)
    zoom.maxY = (maxY + 25)
    zoom.minY = (minY - 25)

    def townToCircle(town : Town) : CircTown = {
      new CircTown(town, zoom)
    }

    def roadToLine(road : Road) : LineRoad = {
      new LineRoad(stage, road, zoom)
    }

    val infoWidget = new InfoWidget(stage, game)




    title.value = "Roolraid Tycoan"
    width = 1000
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



    var toBeDrawn = edgeRoads ++ nodeTowns ++// townsLabel ++
      Seq(infoWidget//,
          // new UpdatableButton(){
          //   text = "MONEY"
          //   layoutX <== stage.width-width
          //   layoutY = 25
          //   onAction = {ae => game.money = 10000000d}
          // }
          , new UpdatableButton(){
            text = "<"
            onAction = {handle(zoom.translate(-25,0))}
            layoutX = 500
            layoutY = 25
          }
          , new UpdatableButton(){
            text = ">"
            onAction = {handle(zoom.translate(25,0))}
            layoutX = 525
            layoutY = 25
          }
          , new UpdatableButton(){
            text = "/\\"
            onAction = {handle(zoom.translate(0,25))}
            layoutX = 512
            layoutY = 0
          }
          , new UpdatableButton(){
            text = "\\/"
            onAction = {handle(zoom.translate(0,-25))}
            layoutX = 512
            layoutY = 50
          }
        )


    def addToBeDrawn(newItem : scalafx.scene.Node with updatable.Updatable) = {
      toBeDrawn = toBeDrawn :+ newItem
    }

    scene = new Scene{
      onScroll = (event: ScrollEvent) => {
        if (event.eventType == ScrollEvent.Scroll) {
          val multiplier = if (event.isControlDown) 5 else 1
          val delta = 1 -multiplier*(event.getDeltaY / 800)

      zoom.zoomFactor(delta)
      }
    }

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
