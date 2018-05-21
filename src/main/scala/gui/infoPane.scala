package infoPane

import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import gui.MainGame
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import scalafx.stage.DirectoryChooser
import charts._
import stuff._
import cargo._
import saveUtils._
import town._
import point._
import train._
import model._
import java.io.File

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
      val dirChooser = new DirectoryChooser(){
        initialDirectory = new File("saves/")
        title = "Emplacement de la sauvegarde"
      }
      val mapFile = dirChooser.showDialog(master)
      mapFile match {
        case null => ()
        case file : File => {
          val path = file.getAbsolutePath
          SaveUtil.saveGame(master.game, path)
          SaveUtil.saveTowns(master.game.townList, path)
          SaveUtil.saveTrains(master.game.trainList, path)
          SaveUtil.savePlanes(master.game.planeList, path)
        }
      }
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
    add(new GridPane(){
      hgap = 10
      add(exitButton,0,0)
      add(saveButton,1,0)},0,3)
  }

  content = grid


  def update(newMoney : Double) = {
    timeCounter += 1
    moneyLabel.text = f"Argent : ${newMoney}%2.2f"
    timeLabel.text = s"Date : ${timeCounter}"
    chartMoney.update()
  }
}
