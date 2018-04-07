package infoPane

import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets


class InfoPane() extends TitledPane() {
  text = "Informations générales"
  var timeCounter = 0
  var moneyLabel = new Label(){
    text = "Argent : "
  }
  var timeLabel = new Label(){
    text = s"Date : ${timeCounter}"
  }

  val grid = new GridPane(){
    hgap = 10
    vgap = 10
    padding = Insets(20,100,10,10)

    add(timeLabel, 0, 0)
    add(moneyLabel, 0, 1)
  }

  content = grid

  def update(newMoney : Double) = {
    timeCounter += 1
    moneyLabel.text = f"Argent : ${newMoney}%2.2f"
    timeLabel.text = s"Date : ${timeCounter}"
  }
}
