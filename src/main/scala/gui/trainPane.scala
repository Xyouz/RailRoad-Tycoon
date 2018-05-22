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
import cargo._
import scalafx.scene.control.ScrollPane.ScrollBarPolicy
import charts._
import switchCharts._

/** This class handles the trains on the graphical interface (so the player can see them).
*/

class TrainPane(master : MainGame) extends TitledPane() with Updatable() {
  text = "Trains"
  maxWidth = 250

  var selectedTrain = None : Option[Train]

  def newTrainWindow(): Unit = {
    val dialog = new newTrainDialog(master)

    val res = dialog.showAndWait()
    res match {
      case Some(NewTrainOk(name, town, engine)) => {
        // create a new train and update the ComboBox used to selectTrain
        try {
          var newTrain = master.game.addTrain(name, town, engine)
          addTrainToComboBox(newTrain)
          master.addToBeDrawn(newTrain)
          select.getSelectionModel().select(newTrain)
          chart.reeinitialize()
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

  var time = 0
  object getLoad extends giveValue {
    val freq = 50
    var average = 0.0
    def initialize = {
      average = 0.0
    }
    def value = {
      time += 1
      selectedTrain match {
        case Some(train) => {
          if (time % freq == 0){
            val res = (average + train.weight)/freq
            average = 0
            Some((time - freq / 2, res))
          }
          else {
            average += train.weight
            None
          }
        }
        case None => None
      }
    }
    def forceValue = {
      time += 1
      selectedTrain match {
        case Some(train) => {
          average = train.weight * (time % freq)
          (time , train.weight)
        }
        case None => (0,0)
      }
    }
  }

  val chart = new SwitchCharts(getLoad)
  chart.title("Poids du train")


  val nameLabel = new Label()
  val engineLabel = new Label()
  val circuitLabel = new TextArea(){
    wrapText = true
    editable = false
  }
  val desiredLoad = new Slider(0,1,0){
    onMouseReleased = {ae =>
      selectedTrain match {
        case None => ()
        case Some(t) => t.desiredLoad = value.value
      }
    }
  }

  val feedbackSlider = new Label {
    text <== desiredLoad.value.asString("Targeted load : %02.1f")
  }


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

  val wagonText = new TextArea(){
    editable = false
    wrapText = true
  }

  val select : ComboBox[Train] = new ComboBox[Train](){
    onAction = {ae =>
      selectedTrain match {
        case None => ()
        case Some(train) => train.color = DarkCyan
      }
      selectedTrain = Some(value.value)
      desiredLoad.min = value.value.maxLoad*0.05
      desiredLoad.max = value.value.maxLoad*0.9
      desiredLoad.value = value.value.desiredLoad
      value.value.color = Cyan
      chart.reeinitialize()
      update()
    }
  }



  val grid = new GridPane(){
    maxWidth = 300
    vgap = 10
    hgap = 10
    add(new Label(""),0,0)
    add(new GridPane(){
      hgap = 10
      add(newTrainButton,0,0)
      add(select,1,0)
    },1,0)
    add(nameLabel, 1, 1)
    add(engineLabel, 1, 2)
    add(new Label("Wagons :"), 1, 3)
    add(wagonText,1,4)
    add(feedbackSlider,1,5)
    add(desiredLoad,1,6)
    add(chart,1,7)
    add(routeButton, 1,8)
    add(circuitLabel, 1, 9)
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

  def wagonsToString(listOfWagon : List[Cargo]) = {
    if (listOfWagon.length == 0){
      ""
    }
    else{
      var res = ""
      for (w <- listOfWagon){
        res = res + w.toString + " | "
      }
      res.substring(0,res.length - 2)
    }
  }

  override def update() = {
    selectedTrain match {
      case None => {
        nameLabel.visible = false
        engineLabel.visible = false
        circuitLabel.visible = false
        routeButton.visible = false
        select.visible = false
        desiredLoad.visible = false
        feedbackSlider.visible = false
        chart.visible = false
      }
      case Some(train) => {
        nameLabel.text = s"  ${train.toString()}  "
        engineLabel.text = s"Moteur : ${train.engine}"
        wagonText.text = wagonsToString(train.listOfWagon)
        circuitLabel.text = circuitToString(train.route)
        nameLabel.visible = true
        engineLabel.visible = true
        circuitLabel.visible = true
        routeButton.visible = true
        select.visible = true
        desiredLoad.visible = true
        feedbackSlider.visible = true
        chart.visible = true
        chart.update()
      }
    }
  }

  def addTrainToComboBox( t : Train) = {
    select += t
  }

  content = new ScrollPane(){
    maxWidth = 325
    hbarPolicy = ScrollBarPolicy.Never
    vbarPolicy = ScrollBarPolicy.Always
    maxHeight = 500
    content = grid
  }


}
