package charts

import scala.math.Numeric
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import gui.MainGame
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import infoPane._

trait giveValue {
  def value() : (Double,Double)
}

class ChartLine(val getValue : giveValue) extends LineChart(new NumberAxis(),new NumberAxis()){

  // xAxis = new NumberAxis()
  // this.xAxis.label = "Number of Month"
  // yAxis = new NumberAxis()

  var frequencyUpd : Int  = 1
  val serie = new XYChart.Series[Number,Number](){
    name = "Value"
  }
  var time = 0
  val derivedSerie = new XYChart.Series[Number, Number](){
    name = "Derived value"
  }
  var (xs, ys) = getValue.value()


  this.getData.add(serie)
  this.getData.add(derivedSerie)
  legendVisible = false
  this.setCreateSymbols(false)
  maxHeight = 200
  minWidth = 300


  def update() {
    time += 1
    if (time%frequencyUpd == 0){
      var (x,y) = getValue.value()
      serie.getData().add(XYChart.Data(x,y))
      derivedSerie.getData().add(XYChart.Data(((x+xs)/2), (((ys-y) / (xs-x)) )))
      xs = x
      ys = y
    }
  }

}
