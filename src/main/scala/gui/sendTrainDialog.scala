package sendTrainDialog

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

// A simple case class used to bypass scala limitations
case class Result(cochonou : Unit)

class SendTrainDialog(val master : JFXApp.PrimaryStage,
                      val game : Game,
                      val startTown : Town )
                   extends Dialog[Result]() {
  initOwner(master)
  title = s"Expédition d'un train depuis ${startTown}"
  headerText = "TCHOU!TCHOU! C'est l'heure du départ!"

  val createButtonType = new ButtonType("C'est parti!", ButtonData.OKDone)
  this.dialogPane().buttonTypes = Seq(createButtonType, ButtonType.Cancel)

  val train = new ComboBox(startTown.railwayStation)
  train.getSelectionModel().selectFirst()

  val townToGo = new ComboBox(game.towns().filter( _ != startTown))
  townToGo.getSelectionModel().selectFirst()

  var maxpassengers = ((startTown.pop)/2).toInt
  val loadings = new Slider(0,maxpassengers,0)

  this.dialogPane().content = new GridPane() {
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

  val createButton = this.dialogPane().lookupButton(createButtonType)
  //createButton.disable = true

  Platform.runLater(train.requestFocus())

  def leaveTrain() = {
    val choosenTrain = train.value.value
    val choosenGoal = townToGo.value.value
    val load = loadings.value.toInt
    if (startTown.goodbyeTrain(choosenTrain)) {
      game.deltaMoney(load*100.0)
      choosenTrain.setLoading(load)
      startTown.deltaPopulation(-load)
      choosenTrain.setDestination(choosenGoal)
      game.trainToBeDispatched(choosenTrain, startTown.getID())
    }
  }

  this.resultConverter = {
    dialogButton =>
      if (dialogButton == createButtonType) {Result(leaveTrain())}
      else Result(())
  }

}
