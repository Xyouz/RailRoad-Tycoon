package newPlaneDialog

import gui._
import model._
import plane._
import town._
import dotPlane._
import planeEngine._
import scalafx.Includes._
import scalafx.scene.layout._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.image.{Image, ImageView}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._

case class NewPlaneOk(name : String, town : Town, engine : PlaneEngine)

// use to create an interactive window in order to create new trains
class newPlaneDialog(val master : MainGame)
                   extends Dialog[NewPlaneOk]() {
  val townList = (master.game.townList).filter(_.hasAirport)
  val engineList = master.game.planeEngineList
  initOwner(master)
  title = "Création d'un nouvel avion"
  headerText = "Vous vous apprétez à inaugurer un nouvel avion"
  graphic = new ImageView(this.getClass.getResource("/gui/avion.png").toString)

  val createButtonType = new ButtonType("Créer", ButtonData.OKDone)
  this.dialogPane().buttonTypes = Seq(createButtonType, ButtonType.Cancel)

  val engine = new ComboBox(engineList)
  engine.getSelectionModel().selectFirst()

  //val echoSpeed = new Label(){text <== StringProperty(speed.value.toString())}

  val planeName = new TextField(){
    promptText = "Name"
  }

  val townToStart = new ComboBox(townList)
  townToStart.getSelectionModel().selectFirst()

  val grid = new GridPane(){
    hgap = 10
    vgap = 10
    padding = Insets(20,100,10,10)

    add(new Label("Name:"), 0, 0)
    add(planeName, 1, 0)
    add(new Label("Engine:"), 0, 1)
    add(engine, 1, 1)
    add(new Label("Launch town:"),0,2)
    add(townToStart, 1, 2)
  }

  val createButton = this.dialogPane().lookupButton(createButtonType)
  createButton.disable = true

  planeName.text.onChange { (_, _, newValue) =>
    createButton.disable = newValue.trim().isEmpty
   }

  this.dialogPane().content = grid

  Platform.runLater(planeName.requestFocus())

  this.resultConverter = {
    dialogButton =>
      if (dialogButton == createButtonType) {
        NewPlaneOk(planeName.text(), townToStart.value.value, engine.value.value)
      }
      else {
        null
      }
  }

}
