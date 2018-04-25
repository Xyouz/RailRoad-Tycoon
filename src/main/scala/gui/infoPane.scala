package infoPane

import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import gui.MainGame
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}

class InfoPane(val master : MainGame) extends TitledPane() {
  text = "Informations générales"
  var timeCounter = 0
  var moneyLabel = new Label(){
    text = "Argent : "
  }
  var timeLabel = new Label(){
    text = s"Date : ${timeCounter}"
  }

  val xAxis = new NumberAxis()
  xAxis.label = "Number of Month"
  val yAxis = new NumberAxis()

  val lineChart = LineChart(xAxis, yAxis)

  val serieMoney = new XYChart.Series[Number,Number]()

  lineChart.getData.add(serieMoney)
  lineChart.title = "Stock Monitoring, 2010"
  lineChart.legendVisible = false
  lineChart.setCreateSymbols(false)
//  val moneyOverTime =

  val exitButton = new Button(){
        text = "Au revoir"
        onAction = { ae => master.close() }
  }

  val grid = new GridPane(){
    hgap = 10
    vgap = 10
    padding = Insets(20,100,10,10)

    add(timeLabel, 0, 0)
    add(moneyLabel, 0, 1)
    add(lineChart, 0, 2)
    add(exitButton, 0, 3)
  }

  content = grid

  def update(newMoney : Double) = {
    timeCounter += 1
    moneyLabel.text = f"Argent : ${newMoney}%2.2f"
    timeLabel.text = s"Date : ${timeCounter}"
    if ((timeCounter % 5) == 0) {
      serieMoney.getData().add(XYChart.Data[Number,Number](timeCounter, newMoney))
    }
  }
}
