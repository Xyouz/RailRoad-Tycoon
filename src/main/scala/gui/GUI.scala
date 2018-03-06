package gui

import model._
import scalafx.Includes._
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

    // Used to create a window to send trains out of a city
    def sendTrain(startTown: Town): Unit = {

        case class Result(cochonou : Unit)
        // Create the custom dialog window.
        val dialog = new Dialog[Result]() {
          initOwner(stage)
          title = s"Expédition d'un train depuis ${startTown}"
          headerText = "TCHOU!TCHOU! C'est l'heure du départ!"
          //graphic = new ImageView(this.getClass.getResource("locomotive.png").toString)
        }

        val createButtonType = new ButtonType("C'est parti!", ButtonData.OKDone)
        dialog.dialogPane().buttonTypes = Seq(createButtonType, ButtonType.Cancel)


        val train = new ComboBox(startTown.railwayStation)


        val townToGo = new ComboBox(game.towns())

        var maxpassengers = ((startTown.pop)/2).toInt
        val loadings = new Slider(0,maxpassengers,0)

        val grid = new GridPane()
        {
          hgap = 10
          vgap = 10
          padding = Insets(20,100,10,10)

          add(new Label("Train:"), 0, 0)
          add(train, 1, 0)
          add(new Label("Destination:"), 0, 1)
          add(townToGo, 1, 1)
          add(new Label("Number of passengers:"), 0, 2)
          add(loadings, 1, 2)
          }

        val createButton = dialog.dialogPane().lookupButton(createButtonType)
        //createButton.disable = true



        dialog.dialogPane().content = grid

        Platform.runLater(train.requestFocus())

        // this function is to be used only to bypass scala limitation when
        // returning a Result
        def leaveTrain() = {val choosenTrain = train.value.value
        val choosenGoal = townToGo.value.value
        val load = loadings.value.toInt
        if (startTown.goodbyeTrain(choosenTrain))
        { game.deltaMoney(load*100.0)
          choosenTrain.setLoading(load)
          startTown.deltaPopulation(-load)
          choosenTrain.setDestination(choosenGoal)
          game.trainToBeDispatched(choosenTrain, startTown.getID())}
      }

        dialog.resultConverter = {dialogButton =>
          if (dialogButton == createButtonType)
          {Result(leaveTrain()) }
          else Result(())
        }

        val res = dialog.showAndWait()
        res match {
          case _ => ()
        }
    }

    def townToCircle(Town : Town) : Circle = {
      new Circle {
        val town = Town
        centerX = town.position().x_coord()
        centerY = town.position().y_coord()
        radius = town.population() / 5
        onMouseClicked = handle {sendTrain(town)}
        fill = Orange
        }

    }

    def showTrainCircle(Town : Town) : Circle =
      {
        new Circle{
        val town = Town
        centerX = town.position().x_coord()
        centerY = town.position().y_coord()
        radius = town.population() / 5 + 10
        fill =  Red
        }
      }

    def roadToLine(Road : Road) : Line = {
      new Line {
        val road = Road
        startX = road.getStart().position().x_coord()
        startY = road.getStart().position().y_coord()
        endX = road.getEnd().position().x_coord()
        endY = road.getEnd().position().y_coord()
        strokeWidth = 8
        onMouseClicked = {ae => new Alert(AlertType.Information) {
                                    initOwner(stage)
                                    title = "Route"
                                    headerText = s"Voici quelques informations à propos de la route reliant ${road.begin} à ${road.end}: "
                                    contentText = s"Actuellement ${road.numberOfTrains()} trains circulent sur cette voie"
                                  }.showAndWait()}
      }
    }

    def pointToSmallCircle(point : Point) : Circle =
      {
        new Circle{
        radius = 10
        centerX = point.x_coord()
        centerY = point.y_coord()
        fill = DarkCyan} }

    title.value = "Roolraid Tycoan"
    width = 1000
    height = 700
    //content = new Button("Hell World")
    scene = new Scene{
      fill = LightGreen

      val nodeTowns = game.towns().map(townToCircle(_))
      val edgeRoads = game.roads().map(roadToLine(_))
      var townsWithTrains = (game.towns().filter(t => !(t.hasTrains()))).map(showTrainCircle(_))

      // update what is drawn on the screen
      def drawScene() =
        {
      val nodeTowns = game.towns().map(townToCircle(_))
      val edgeRoads = game.roads().map(roadToLine(_))
      var townsWithTrains = (game.towns().filter(t => !(t.hasTrains()))).map(showTrainCircle(_))

      var trains = Seq[Circle]()

      for (roads <- game.roads())
      {
        trains = roads.getTrainsPos().map(pointToSmallCircle(_)) ++trains
      }

      content = edgeRoads ++ trains ++
          townsWithTrains ++ nodeTowns ++ Seq(new Button("New Train"){
          onAction = handle {newTrainWindow()};
          layoutX <== stage.width-width; layoutY = 0},
          new Button("Au revoir"){onAction = { ae => stage.close() };layoutX <== stage.width-width; layoutY = 25},
          //new Button("Monde de merde"){layoutX <== stage.width-width; layoutY = 50},
          new Label(s"Argent : ${game.money}€"){layoutX <== stage.width-width -25; layoutY <== stage.height - 75})
      }


      // tick every 1/10 of a second
      var (lastTick : Long) = 0
      val updateTick = AnimationTimer (t => {
        if ((t-lastTick)>=100000000){  // Allow to choose the duration
          lastTick = t                  // between two updates
          game.update()
          drawScene()}
      })
      updateTick.start()


      // use to create an interactive window in order to create new trains
      def newTrainWindow(): Unit = {

          case class Result(cochonou : Unit)
          // Create the custom dialog window.
          val dialog = new Dialog[Result]() {
            initOwner(stage)
            title = "Création d'un nouveau train"
            headerText = "Vous vous apprétez à inaugurer un nouveau train"
            graphic = new ImageView(this.getClass.getResource("locomotive.png").toString)
          }

          val createButtonType = new ButtonType("Créer", ButtonData.OKDone)
          dialog.dialogPane().buttonTypes = Seq(createButtonType, ButtonType.Cancel)


          val speed = new Slider(1,10,5)

          //val echoSpeed = new Label(){text <== StringProperty(speed.value.toString())}

          val trainName = new TextField()
          {
            promptText = "Name"
          }

          val townToStart = new ComboBox(game.towns())

          val grid = new GridPane()
          {
            hgap = 10
            vgap = 10
            padding = Insets(20,100,10,10)

            add(new Label("Name:"), 0, 0)
            add(trainName, 1, 0)
            add(new Label("Speed:"), 0, 1)
            add(speed, 1, 1)
            //add(echoSpeed,1,2)
            add(new Label("Launch town:"),0,2)
            add(townToStart, 1, 2)
          }

          val createButton = dialog.dialogPane().lookupButton(createButtonType)
          //createButton.disable = true



          dialog.dialogPane().content = grid

          Platform.runLater(trainName.requestFocus())

          dialog.resultConverter = {dialogButton =>
            if (dialogButton == createButtonType) {Result(townToStart.value.value.welcomeTrain(new Train(speed.value.toDouble,trainName.text())))}
            else Result(())
          }

          val res = dialog.showAndWait()
          res match {
            case _ => ()
          }
      }
      }
  }
