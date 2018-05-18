package trainPane

import town._
import train._
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import updatable._
import newTrainDialog._
import moneyAlert._
import gui._
import scalafx.Includes._
import setRouteDialog._
import scalafx.scene.paint.Color._
import wagon._

/** This class handles the trains on the graphical interface (so the player can see them).
*/

class TrainPane(master : MainGame) extends TitledPane() with Updatable() {
  text = "Trains"
  maxWidth = 250


  def newTrainWindow(): Unit = {
    val dialog = new newTrainDialog(master)

    val res = dialog.showAndWait()
    res match {
      case Some(NewTrainOk(name, town, engine, wagons)) => {
        // create a new train and update the ComboBox used to selectTrain
        try {
          var newTrain = master.game.addTrain(name, town, engine, wagons)
          addTrainToComboBox(newTrain)
          master.addToBeDrawn(newTrain)
          select.getSelectionModel().select(newTrain)
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

  var selectedTrain = None : Option[Train]

  val routeButton = new Button(){
    text = "Itinéraire"
    onAction = { ae =>
      selectedTrain match {
        case None => ()
        case Some(train) => {
          val dialog = new setRouteDialog(master,train)

          val res = dialog.showAndWait()
          res match {
            case Some(OkRoute(circuit,longHaulValue)) => {
              if (circuit.length >= 2){
                train.setRoute(circuit)
                train.longHaul = longHaulValue
                if (train.distance <= 0){
                  master.game.trainsOnTransit = master.game.trainsOnTransit :+ (train,train.getDestination())
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
    text = "New Train"
    onAction = handle {newTrainWindow()}
  }


  val select : ComboBox[Train] = new ComboBox[Train](){
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
      case Some(train) => {
        nameLabel.text = s"  ${train.toString()}  "
        engineLabel.text = s"Moteur : ${train.engine}"
        circuitLabel.text = circuitToString(train.route)
        nameLabel.visible = true
        engineLabel.visible = true
        circuitLabel.visible = true
        routeButton.visible = true
        select.visible = true
      }
    }
  }

  private def addTrainToComboBox( t : Train) = {
    select += t
  }

  content = grid

}
