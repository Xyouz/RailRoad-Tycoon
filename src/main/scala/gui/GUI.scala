package gui

import model._
import scalafx.Includes._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox,VBox,BorderPane,Priority,GridPane}
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Circle,Rectangle,Line}
import scalafx.beans.property.DoubleProperty
import scalafx.animation.AnimationTimer
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.geometry.{Insets, Pos}

class MainGame(val game: Game) extends JFXApp.PrimaryStage
  { stage =>
    def townToCircle(Town : Town) : Circle = {
      new Circle {
        val town = Town
        centerX = town.position().x_coord()
        centerY = town.position().y_coord()
        radius = town.population() / 5
        onMouseClicked = { ae =>  town.incrPop();
                                  radius = town.population() / 5;
                                   {new Alert(AlertType.Information) {
                                    initOwner(stage)
                                    title = town.name
                                    headerText = s"Voici quelques informations à propos de ${town.name}"
                                    contentText = s"Population actuelle : ${town.pop}"
                                  }.showAndWait()
                            }}
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
          println(s"${t/1000000000}")
          game.update()}
      })
      updateTick.start()



def newTrainWindow(): Unit = {

  case class Result(train : Train, firstTown : Town)

    // Create the custom dialog window.
    def dialog = new Dialog[Result] {
      initOwner(stage)
      title = "Création d'un nouveau train"
      headerText = "Vous vous aprétez à inaugurer un nouveau train"
      graphic = new ImageView(this.getClass.getResource("locomotive.png").toString)
    }

    val createButtonType = new ButtonType("Login", ButtonData.OKDone)
    dialog.dialogPane().buttonTypes = Seq(createButtonType, ButtonType.Cancel)


    val speed = new Slider(0,10,5)

    val trainName = new TextField()
    {
      promptText = "Name"
    }

    val grid = new GridPane()
    {
      hgap = 10
      vgap = 10
      padding = Insets(20,100,10,10)

      add(new Label("Name:"), 0, 0)
      add(trainName, 1, 0)
      add(new Label("Speed:"), 0, 1)
      add(speed, 1, 1)
    }


    dialog.dialogPane().content = grid

    Platform.runLater(username.rquestFocus())

    dialog.resultConverter = dialogButton =>
      if (dialogButton == createButtonType) Result(Train(speed.value.toDouble,trainName.text(),new Town()))
      else null

    val result = dialog.showAndWait()
  }


      content = edgeRoads ++ nodeTowns ++ Seq(new Button("New Train"){
        onAction = handle {newTrainWindow()};
        layoutX <== stage.width-width; layoutY = 0},
          new Button("Au revoir"){onAction = { ae => stage.close() };layoutX <== stage.width-width; layoutY = 25},
          new Button("Monde de merde"){layoutX <== stage.width-width; layoutY = 50},
          new Label(s"${game.towns()(0).population()}"){layoutX <== stage.width-width -25; layoutY <== stage.height - 75})
      }
  }
