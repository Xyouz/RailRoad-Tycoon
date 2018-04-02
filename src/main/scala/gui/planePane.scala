package planePane

import town._
import plane._
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import updatable._
import newTrainDialog._
import moneyAlert._
import gui._
import dotTrain._
import scalafx.Includes._
import setRouteDialog._
import scalafx.scene.paint.Color._


class PlanePane(master : MainGame) extends TitledPane() with Updatable() {
  text = "Avions"
  maxWidth = 250


  // def newTrainWindow(): Unit = {
  //   val dialog = new newTrainDialog(master)
  //
  //   val res = dialog.showAndWait()
  //   res match {
  //     case Some(NewTrainOk(name, town, engine)) => {
  //       // create a new train and update the ComboBox used to selectTrain
  //       try {
  //         var newTrain = master.game.addTrain(name, town, engine, List(new Wagon("L",500)))
  //         addTrainToComboBox(newTrain)
  //         master.addToBeDrawn(new CircTrain(newTrain))
  //         select.getSelectionModel().select(newTrain)
  //       }
  //       catch {
  //         case NotEnoughMoneyException(msg) => {
  //           val alert = new MoneyAlert(master, msg)
  //           alert.showAndWait()
  //         }
  //       }
  //     }
  //     case _ => ()
  //   }
  // }


  val nameLabel = new Label()
  val engineLabel = new Label()
  val circuitLabel = new TextArea(){
    wrapText = true
    editable = false
  }

  var selectedTrain = None : Option[Plane]

  val routeButton = new Button(){
    text = "Itinéraire"
    // onAction = { ae =>
    //   selectedTrain match {
    //     case None => ()
    //     case Some(train) => {
    //       val dialog = new setRouteDialog(master,train)
    //
    //       val res = dialog.showAndWait()
    //       res match {
    //         case _ => ()
    //       }
    //     }
    //   }
    // }
  }

  val newTrainButton = new Button(){
    text = "New Plane"
    // onAction = handle {newTrainWindow()}
  }


  val select : ComboBox[Plane] = new ComboBox[Plane](){
    onAction = {ae =>
      selectedTrain match {
        case None => ()
        case Some(train) => train.color = DarkCyan
      }
      selectedTrain = Some(value.value)
      value.value.color = Cyan
      update()
    }
  }

  val grid = new GridPane(){
    vgap = 10
    //padding = Insets(20,100,10,10)

    add(newTrainButton, 0, 0)
    add(select, 0, 1)
    add(nameLabel, 0, 2)
    add(engineLabel, 0, 3)
    add(circuitLabel, 0, 4)
    add(routeButton, 0, 5)
  }

  def circuitToString(circuit : Array[Town]) = {
    var maxN = circuit.length
    var str = ""
    for (t <- 0 until maxN){
      if (t == 0){
        str = str + circuit(t).toString()
      }
      else {
        str = str + " -> " + circuit(t).toString()
      }
    }
    str
  }

  override def update() = {
    selectedTrain match {
      case None => {
        nameLabel.visible = false
        engineLabel.visible = false
        circuitLabel.visible = false
        routeButton.visible = false
        select.visible = false
      }
      case Some(plane) => {
        nameLabel.text = s"  ${plane.toString()}  "
        engineLabel.text = s"Moteur : ${plane.engine}"
        circuitLabel.text = circuitToString(plane.route)
        nameLabel.visible = true
        engineLabel.visible = true
        circuitLabel.visible = true
        routeButton.visible = true
        select.visible = true
      }
    }
  }

  private def addTrainToComboBox( t : Plane) = {
    select += t
  }

  content = grid

}