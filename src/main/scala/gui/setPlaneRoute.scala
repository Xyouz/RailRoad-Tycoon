package setPlaneRouteDialog

import gui._
import model._
import plane._
import town._
import airportNetwork._
import scalafx.Includes._
import scalafx.scene.layout._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.image.{Image, ImageView}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._

case class OkRoute(circuit : Array[Town])

/** create an interactive window to select a route for a plane
*/

class setRouteDialog(val master : MainGame, val plane : Plane)
                   extends Dialog[OkRoute]() {
  val game = master.game
  initOwner(master)
  title = "Choix d'un circuit"
  headerText = s"Veuillez indiquer le circuit à suivre par ${plane}"
  graphic = new ImageView(this.getClass.getResource("/gui/avion.png").toString)

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

  val towns = new ListView(game.airports.connectedComponent(game.townList(plane.getDestination),plane.engine.maxRange)){
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
