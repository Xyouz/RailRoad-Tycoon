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
import scalafx.Includes._
import setPlaneRouteDialog._
import scalafx.scene.paint.Color._
import cargo._

/** This class enables the player to create a new plane with its specificities
 * (like the itinerary, or the goods that are loaded)
*/

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
          var newPlane = master.game.addPlane(name, town, engine)
          addPlaneToComboBox(newPlane)
          master.addToBeDrawn(newPlane)
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

  val cargoLabel = new Label()
  val nameLabel = new Label()
  val engineLabel = new Label()
  val circuitLabel = new TextArea(){
    wrapText = true
    editable = false
  }
  val desiredLoad = new Slider(0,1,0){
    onMouseReleased = {ae =>
      selectedPlane match {
        case None => ()
        case Some(p) => p.desiredLoad = value.value
      }
    }
  }
  val feedbackSlider = new Label {
    text <== desiredLoad.value.asString("Targeted load : %02.1f")
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
            case Some(OkRoute(circuit,longHaul)) => {
              plane.setRoute(circuit)
              plane.longHaul = longHaul
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

  val newPlaneButton = new Button(){
    text = "New Plane"
    onAction = handle {newPlaneWindow()}
  }


  val select : ComboBox[Plane] = new ComboBox[Plane](){
    onAction = {ae =>
      selectedPlane match {
        case None => ()
        case Some(plane) => plane.color = LightCyan
      }
      selectedPlane = Some(value.value)
      desiredLoad.min = value.value.maxLoad*0.05
      desiredLoad.max = value.value.maxLoad*0.9
      desiredLoad.value = value.value.desiredLoad
      value.value.color = Aquamarine
      update()
    }
  }

  val grid = new GridPane(){
    vgap = 10

    add(new GridPane(){
      hgap = 10
      add(newPlaneButton,0,0)
      add(select,1,0)
    },0,0)
    add(nameLabel, 0, 1)
    add(engineLabel, 0, 2)
    add(cargoLabel, 0, 3)
    add(circuitLabel, 0, 4)
    add(feedbackSlider,0,5)
    add(desiredLoad,0,6)
    add(routeButton, 0,7)
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
        cargoLabel.visible = false
        engineLabel.visible = false
        circuitLabel.visible = false
        routeButton.visible = false
        select.visible = false
        feedbackSlider.visible = false
        cargoLabel.visible = false
        desiredLoad.visible = false
    }
      case Some(plane) => {
        nameLabel.text = s"  ${plane.toString()}  "
        engineLabel.text = s"Moteur : ${plane.engine}"
        if (plane.hasLoad){
          cargoLabel.text = s"Cargo : ${plane.getHold}"
        }
        else {
          cargoLabel.text = s"Cargo : Vide"
        }
        circuitLabel.text = circuitToString(plane.route)
        nameLabel.visible = true
        engineLabel.visible = true
        circuitLabel.visible = true
        routeButton.visible = true
        select.visible = true
        desiredLoad.visible = true
        feedbackSlider.visible = true
        cargoLabel.visible = true
      }
    }
  }

  def addPlaneToComboBox( t : Plane) = {
    select += t
  }

  content = grid

}
