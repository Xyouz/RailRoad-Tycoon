package infoWidget

import infoPane._
import cityPane._
import trainPane._
import scalafx.Includes._
import scalafx.scene.control._
import updatable._
import model._
import train._
import gui._
import scalafx.application.{JFXApp, Platform}



class InfoWidget(val master : MainGame, val game : Game) extends Accordion() with Updatable{
    maxWidth = 250
    val informationPane = new InfoPane()

    val trainPane = new TrainPane(master)

    val cityPane = new CityPane(game.townList)
    panes = List(
      informationPane,
      cityPane,
      trainPane
    )
    override def update() = {
      informationPane.update(game.money)
      cityPane.update()
      trainPane.update()
    }
}
