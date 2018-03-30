package newTrainDialog

import gui._
import model._
import train._
import town._
import dotTrain._
import trainEngine._
import scalafx.Includes._
import scalafx.scene.layout._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.image.{Image, ImageView}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._

case class NewTrainOk(name : String, town : Town, engine : TrainEngine)

// use to create an interactive window in order to create new trains
class newTrainDialog(val master : MainGame)
                   extends Dialog[NewTrainOk]() {
  val townList = master.game.townList
  val engineList = master.game.trainEngineList
  initOwner(master)
  title = "Création d'un nouveau train"
  headerText = "Vous vous apprétez à inaugurer un nouveau train"
  graphic = new ImageView(this.getClass.getResource("/gui/locomotive.png").toString)

  val createButtonType = new ButtonType("Créer", ButtonData.OKDone)
  this.dialogPane().buttonTypes = Seq(createButtonType, ButtonType.Cancel)

  val engine = new ComboBox(engineList)
  engine.getSelectionModel().selectFirst()

  //val echoSpeed = new Label(){text <== StringProperty(speed.value.toString())}

  val trainName = new TextField(){
    promptText = "Name"
  }

  val townToStart = new ComboBox(townList)
  townToStart.getSelectionModel().selectFirst()

  val grid = new GridPane(){
    hgap = 10
    vgap = 10
    padding = Insets(20,100,10,10)

    add(new Label("Name:"), 0, 0)
    add(trainName, 1, 0)
    add(new Label("Engine:"), 0, 1)
    add(engine, 1, 1)
    add(new Label("Launch town:"),0,2)
    add(townToStart, 1, 2)
  }

  val createButton = this.dialogPane().lookupButton(createButtonType)
  createButton.disable = true

  trainName.text.onChange { (_, _, newValue) =>
    createButton.disable = newValue.trim().isEmpty
   }

  this.dialogPane().content = grid

  Platform.runLater(trainName.requestFocus())

  this.resultConverter = {
    dialogButton =>
      if (dialogButton == createButtonType) {
        NewTrainOk(trainName.text(), townToStart.value.value, engine.value.value)
      }
      else {
        null
      }
  }

}
