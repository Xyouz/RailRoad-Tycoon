package setRouteDialog

import gui._
import model._
import train._
import town._
import railMap._
import scalafx.Includes._
import scalafx.scene.layout._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.image.{Image, ImageView}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._

case class OkRoute(circuit : Array[Town], longHaul : Boolean)

/** is used to create an interactive window in order to create new trains
*/

class setRouteDialog(val master : MainGame, val train : Train)
                   extends Dialog[OkRoute]() {
  val game = master.game
  initOwner(master)
  title = "Choix d'un circuit"
  headerText = s"Veuillez indiquer le circuit à suivre par ${train}"
  graphic = new ImageView(this.getClass.getResource("/gui/locomotive2.png").toString)

  val createButtonType = new ButtonType("Ok", ButtonData.OKDone)
  this.dialogPane().buttonTypes = Seq(createButtonType, ButtonType.Cancel)

  var circuit = Seq[Town]()

  def circuitToString() = {
    var str = ""
    for (t <- circuit){
      str = str + " -> " + t.toString()
    }
    str
  }

  val feedbackText = new TextArea(){
    wrapText = true
    editable = false
  }

  val towns = new ListView(game.railMap.connectedComponent(game.townList(train.getDestination))){
    selectionModel().selectedItem.onChange {
      (_, _ , newValue ) => {
        circuit = circuit :+ newValue
        feedbackText.text = circuitToString()
      }
    }
  }
  towns.setPrefHeight(100)

  val hubs = new ListView(game.railMap.connectedComponent(game.townList(train.getDestination)).filter(_.isHub)){
    visible = false
    selectionModel().selectedItem.onChange {
      (_, _ , newValue ) => {
        circuit = circuit :+ newValue
        feedbackText.text = circuitToString()
      }
    }
  }
  hubs.setPrefHeight(100)

  val longHaul = new RadioButton("Liaison inter-hub?"){
    onAction = {
      ae => {
        circuit = Seq[Town]()
        feedbackText.text = ""
        hubs.visible = selected.value
        towns.visible = ! selected.value
      }
    }
  }

  val grid = new GridPane(){
    hgap = 10
    vgap = 10
    padding = Insets(20,100,10,10)
    add(longHaul,0,0)
    add(new Label("Prochaine ville:"), 0, 1)
    add(towns, 0, 2)
    add(hubs, 0, 2)
    add(new Label("Circuit:"),1,1)
    add(feedbackText, 1,2)

  }

  this.dialogPane().content = grid

  Platform.runLater(towns.requestFocus())

  this.resultConverter = {
    dialogButton =>
      if (dialogButton == createButtonType) {
        OkRoute(circuit.toArray[Town],longHaul.selected.value)
      }
      else {
        null
      }
  }

}
