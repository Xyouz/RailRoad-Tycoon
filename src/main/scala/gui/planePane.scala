package planePane

import town._
import plane._
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import updatable._
import newPlaneDialog._
import moneyAlert._
import gui._
import dotPlane._
import scalafx.Includes._
import setPlaneRouteDialog._
import scalafx.scene.paint.Color._


class PlanePane(master : MainGame) extends TitledPane() with Updatable() {
  text = "Avions"
  maxWidth = 250


  def newPlaneWindow(): Unit = {
    val dialog = new newPlaneDialog(master)

    val res = dialog.showAndWait()
    res match {
      case Some(NewPlaneOk(name, town, engine)) => {
        // create a new train and update the ComboBox used to selectTrain
        try {
          var newPlane = master.game.addPlane(name, town, engine, new Box("L",500))
          addTrainToComboBox(newPlane)
          master.addToBeDrawn(new CircPlane(newPlane))
          select.getSelectionModel().select(newPlane)
        }
        catch {
          case NotEnoughMoneyException(msg) => {
            val alert = new MoneyAlert(master, msg)
            alert.showAndWait()
          }
        }
      }
      case _ => ()
    }
  }


  val nameLabel = new Label()
  val engineLabel = new Label()
  val circuitLabel = new TextArea(){
    wrapText = true
    editable = false
  }

  var selectedPlane = None : Option[Plane]

  val routeButton = new Button(){
    text = "Itinéraire"
    onAction = { ae =>
      selectedPlane match {
        case None => ()
        case Some(plane) => {
          val dialog = new setRouteDialog(master,plane)

          val res = dialog.showAndWait()
          res match {
            case Some(OkRoute(circuit)) => {
              plane.setRoute(circuit)
              if (plane.distance <= 0){
                var briefing = master.game.airports.getBriefing(plane.getCurrentTown,circuit(0),plane.engine.maxRange)
                if (briefing.length > 1 && (briefing(0).pos-briefing(1).pos).norm()*plane.engine.priceByKm <= master.game.money) {
                  plane.startFly(plane.getCurrentTown,circuit(0))
                }
              }
            }
            case _ => {}
          }
        }
      }
    }
  }

  val newTrainButton = new Button(){
    text = "New Plane"
    onAction = handle {newPlaneWindow()}
  }


  val select : ComboBox[Plane] = new ComboBox[Plane](){
    onAction = {ae =>
      selectedPlane match {
        case None => ()
        case Some(plane) => plane.color = Aquamarine
      }
      selectedPlane = Some(value.value)
      value.value.color = Aqua
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
    selectedPlane match {
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
