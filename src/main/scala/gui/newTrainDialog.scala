package newTrainDialog

import gui._
import model._
import train._
import town._
import dotTrain._
import scalafx.Includes._
import scalafx.scene.layout._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.image.{Image, ImageView}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._

case class Result(cochonou : Unit)

// use to create an interactive window in order to create new trains
class newTrainDialog(val master : MainGame,
                     val game : Game)
                   extends Dialog[Result]() {
  initOwner(master)
  title = "Création d'un nouveau train"
  headerText = "Vous vous apprétez à inaugurer un nouveau train"
  graphic = new ImageView(this.getClass.getResource("/gui/locomotive.png").toString)

  val createButtonType = new ButtonType("Créer", ButtonData.OKDone)
  this.dialogPane().buttonTypes = Seq(createButtonType, ButtonType.Cancel)

  val engine = new ComboBox(game.trainEngineList)
  engine.getSelectionModel().selectFirst()

  //val echoSpeed = new Label(){text <== StringProperty(speed.value.toString())}

  val trainName = new TextField(){
    promptText = "Name"
  }

  val townToStart = new ComboBox(game.towns())
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

  def toBeApplied() = {
    val startTown = townToStart.value.value
    val choosenEngine = engine.value.value
    val newTrain = new Train(trainName.text(),choosenEngine)
    if (game.money < choosenEngine.price){
      new Alert(AlertType.Warning) {
        initOwner(master)
        title = "Plus de pognon!!!"
        headerText = "Vous manquez d'argent pour créer un nouveau train."
        contentText = "La création du train n'a pas été possible."
      }.showAndWait()
    }
    else {
      game.addTrain(newTrain)
      game.money -= choosenEngine.price
      master.addToBeDrawn(new CircTrain(newTrain))
      startTown.welcomeTrain(newTrain)
    }
  }

  this.resultConverter = {
    dialogButton =>
      if (dialogButton == createButtonType) {Result(toBeApplied)}
      else Result(())
  }

}
