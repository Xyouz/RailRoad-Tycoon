package charts

import scala.math.Numeric
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import gui.MainGame
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import infoPane._
import scalafx.scene.paint.Color._
import scalafx.beans.property._
import scalafx.Includes._
import scalafx.scene.layout._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.image.{Image, ImageView}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Alert.AlertType



trait giveValue {
  def value() : (Double,Double)
}

class ChartLine(val getValue : giveValue, val xAxis : NumberAxis, val yAxis : NumberAxis) extends LineChart(xAxis, yAxis){

  var indicator = true

  var minGraph = Double.PositiveInfinity
  var maxGraph = 0.0

  var minDerived = Double.PositiveInfinity
  var maxDerived = 0.0

  var frequencyUpd : Int  = 30
  val serie = new XYChart.Series[Number,Number](){
    name = "Value"
  }
  var time = 0
  val derivedSerie = new XYChart.Series[Number, Number](){
    name = "Derived value"
  }

  var (xs, ys) = getValue.value()

  def swapGraphs () = {
    if (indicator) {
      this.data.value(0).node.value.visible = ! indicator
      this.data.value(1).node.value.visible = indicator
      yAxis.autoRanging = false
      yAxis.upperBound = maxDerived*1.5
      yAxis.lowerBound = minDerived*1.3
   }
   else {
     this.data.value(1).node.value.visible = indicator
     this.data.value(0).node.value.visible = ! indicator
     yAxis.autoRanging = false
     yAxis.upperBound = maxGraph*1.5
     yAxis.lowerBound = minGraph*1.3
   }
    indicator = ! indicator
  }


  this.getData.add(serie)
  this.getData.add(derivedSerie)
  legendVisible = false
  this.setCreateSymbols(false)
  maxHeight = 200
  minWidth = 300


  def maxValueD (valueAdded: Double, derivedValueAdded: Double) {
    if (valueAdded > maxGraph) {
      maxGraph = valueAdded
    }
    if (derivedValueAdded > maxDerived) {
      maxDerived = derivedValueAdded
    }
  }

  def minValueD (valueAdded: Double, derivedValueAdded: Double) {
    if (valueAdded < minGraph) {
      minGraph = valueAdded
    }
    if (derivedValueAdded < minDerived) {
      minDerived = derivedValueAdded
    }
  }


  def update() {
    time += 1
    if (time%frequencyUpd == 0){
      var (x,y) = getValue.value()
      serie.getData().add(XYChart.Data(x,y))
      derivedSerie.getData().add(XYChart.Data(((x+xs)/2), ( (ys-y) / (xs-x)) ) )
      minValueD(y, ((ys-y) / (xs-x)) )
      maxValueD(y, ((ys-y) / (xs-x)) )
      xs = x
      ys = y
    }
  }

}
