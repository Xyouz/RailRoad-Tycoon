package switchCharts

import charts._
import scalafx.Includes._
import scalafx.scene.layout._
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.image.{Image, ImageView}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}



class SwitchCharts(val valueGiven : giveValue) extends GridPane() {

  var indicator = true

  val yAxis = new NumberAxis()
  val graph = new ChartLine(valueGiven, new NumberAxis(), yAxis)
  var freqUpd = graph.frequencyUpd

  // var evolution = graph.serie
  // var derivedEvolution = graph.derivedSerie
  var time = 0
  graph.data.value(0).node.value.visible = true
  graph.data.value(1).node.value.visible = false



  val change = new RadioButton ("derived chart"){
    onAction = {
      ae => {
        graph.swapGraphs()
      }
    }
  }

  add(change, 0,0)
  add(graph, 0, 1)

  def update() {
    time += 1
    graph.update()
  }

}
