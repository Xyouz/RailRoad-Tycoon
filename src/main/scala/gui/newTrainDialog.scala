package newTrainDialog

import model._
import train._
import town._
import scalafx.Includes._
import scalafx.scene.layout._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.image.{Image, ImageView}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._

case class Result(cochonou : Unit)

class newTrainDialog(val master : JFXApp.PrimaryStage,
                     val game : Game)
                   extends Dialog[Result]() {
  initOwner(master)
  title = "Création d'un nouveau train"
  headerText = "Vous vous apprétez à inaugurer un nouveau train"
  graphic = new ImageView(this.getClass.getResource("/gui/locomotive.png").toString)

  val createButtonType = new ButtonType("Créer", ButtonData.OKDone)
  this.dialogPane().buttonTypes = Seq(createButtonType, ButtonType.Cancel)

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

  val createButton = this.dialogPane().lookupButton(createButtonType)
  //createButton.disable = true

  this.dialogPane().content = grid

  Platform.runLater(trainName.requestFocus())

  this.resultConverter = {
    dialogButton =>
      if (dialogButton == createButtonType) {Result(townToStart.value.value.welcomeTrain(new Train(speed.value.toDouble,trainName.text())))}
      else Result(())
  }

}
