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
import scala.math._



/** This class enables us to draw the charts and their derived charts. It is used in switchCharts,
 *  and in infoPane and cityPane, because it is where we use charts.

*/


trait giveValue {
  def value() : Option[(Double,Double)]
  def forceValue() : (Double, Double)
}

class ChartLine(val getValue : giveValue, val xAxis : NumberAxis, val yAxis : NumberAxis) extends LineChart(xAxis, yAxis){

  var indicator = false

  var minGraph = Double.PositiveInfinity
  var maxGraph = Double.NegativeInfinity

  var minDerived = Double.PositiveInfinity
  var maxDerived = Double.NegativeInfinity

  var tickNumber = 5

  var serie = new XYChart.Series[Number,Number](){
    name = "Value"
  }

  var derivedSerie = new XYChart.Series[Number, Number](){
    name = "Derived value"
  }

  var (xs, ys) = getValue.forceValue()
  yAxis.autoRanging = false

  def swapGraphs () = {
    indicator = ! indicator
    this.data.value(0).node.value.visible = ! indicator
    this.data.value(1).node.value.visible = indicator
    scale()
  }

  def scale() = {
    if (indicator) {
      yAxis.upperBound = maxDerived + 0.05*math.abs(maxDerived)
      yAxis.lowerBound = minDerived - 0.05*math.abs(minDerived)
   }
   else {
     yAxis.upperBound = maxGraph + 0.05*math.abs(maxGraph)
     yAxis.lowerBound = minGraph - 0.05*math.abs(minGraph)
   }
   yAxis.tickUnit = (yAxis.upperBound.value - yAxis.lowerBound.value)/ tickNumber
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

  def reeinitialize(){
    forceUpdate()
    serie.getData().remove(0,serie.getData().size())
    derivedSerie.getData().remove(0,derivedSerie.getData().size())
    minGraph = Double.PositiveInfinity
    maxGraph = Double.NegativeInfinity
    minDerived = Double.PositiveInfinity
    maxDerived = Double.NegativeInfinity
  }

  def update() {
    var v = getValue.value()
    v match {
      case Some((x,y)) => {
        serie.getData().add(XYChart.Data(x,y))
        derivedSerie.getData().add(XYChart.Data(((x+xs)/2), ( (ys-y) / (xs-x)) ) )
        minValueD(y, ((ys-y) / (xs-x)) )
        maxValueD(y, ((ys-y) / (xs-x)) )
        xs = x
        ys = y
      }
      case None => ()
    }
    scale()
  }


  private def forceUpdate() {
    var (x,y) = getValue.forceValue()
    xAxis.lowerBound = x
    serie.getData().add(XYChart.Data(x,y))
    derivedSerie.getData().add(XYChart.Data(((x+xs)/2), ( (ys-y) / (xs-x)) ) )
    minValueD(y, ((ys-y) / (xs-x)) )
    maxValueD(y, ((ys-y) / (xs-x)) )
    xs = x
    ys = y
    scale()
  }

}
