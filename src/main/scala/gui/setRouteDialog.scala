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

case class OkRoute(circuit : Array[Town])

// use to create an interactive window in order to create new trains
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



  val grid = new GridPane(){
    hgap = 10
    vgap = 10
    padding = Insets(20,100,10,10)

    add(new Label("Prochaine ville:"), 0, 0)
    add(towns, 0, 1)
    add(new Label("Circuit:"),1,0)
    add(feedbackText, 1,1)
  }

  this.dialogPane().content = grid

  Platform.runLater(towns.requestFocus())

  this.resultConverter = {
    dialogButton =>
      if (dialogButton == createButtonType) {
        OkRoute(circuit.toArray[Town])
      }
      else {
        null
      }
  }

}
