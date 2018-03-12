package chooseTrainDialog

import gui._
import model._
import train._
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
class ChooseTrainDialog(val master : MainGame,
                     val game : Game)
                   extends Dialog[Result]() {
  initOwner(master)
  title = "Choix d'un train"
  headerText = "Veuillez choisir un train"
  graphic = new ImageView(this.getClass.getResource("/gui/locomotive.png").toString)

  val createButtonType = new ButtonType("Ok", ButtonData.OKDone)
  this.dialogPane().buttonTypes = Seq(createButtonType, ButtonType.Cancel)

  val train = new ComboBox(game.trainList)
  train.getSelectionModel().selectFirst()


  val grid = new GridPane(){
    hgap = 10
    vgap = 10
    padding = Insets(20,100,10,10)

    add(new Label("Train:"), 0, 0)
    add(train, 1, 0)
  }

  this.dialogPane().content = grid

  Platform.runLater(train.requestFocus())

  def toBeApplied() = {
    val choosenTrain = train.value.value
    println(choosenTrain)
  }

  this.resultConverter = {
    dialogButton =>
      if (dialogButton == createButtonType) {Result(toBeApplied)}
      else Result(())
  }

}
