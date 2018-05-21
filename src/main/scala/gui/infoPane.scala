package infoPane

import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import gui.MainGame
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import charts._
import stuff._
import cargo._
import saveUtils._
import town._
import point._
import train._

class InfoPane(val master : MainGame) extends TitledPane() {
  text = "Informations générales"
  var timeCounter = 0
  var moneyLabel = new Label(){
    text = "Argent : "
  }
  var timeLabel = new Label(){
    text = s"Date : ${timeCounter}"
  }
  val frequency = 50

  object getMoney extends giveValue {
    var time = 0
    def value= {
      time = time + frequency
      (time, master.game.money)
    }
  }

  val chartMoney = new ChartLine(getMoney)

  chartMoney.frequencyUpd = frequency

  chartMoney.title = "Argent"

  val saveButton = new Button("Save"){
    onAction = { ae =>
      val stuff = master.game.trainList(0)
      val save = JsonUtil.toJson(stuff)
    //  val stuff2 = JsonUtil.fromJson[Option[Int]](save)
      println(save)
      // println(stuff == stuff2)
    }
  }

  val exitButton = new Button(){
    text = "Au revoir"
    onAction = { ae => master.close() }
  }

  val grid = new GridPane(){
    hgap = 10
    vgap = 10
    // padding = Insets(20,100,10,10)

    add(timeLabel, 0, 0)
    add(moneyLabel, 0, 1)
    add(chartMoney,0, 2)
    add(exitButton, 0, 3)
    add(saveButton, 1, 3)
  }

  content = grid


  def update(newMoney : Double) = {
    timeCounter += 1
    moneyLabel.text = f"Argent : ${newMoney}%2.2f"
    timeLabel.text = s"Date : ${timeCounter}"
    chartMoney.update()
  }
}
