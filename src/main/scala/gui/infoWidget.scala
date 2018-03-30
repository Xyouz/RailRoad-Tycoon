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


    panes = List(
      informationPane,
      new CityPane(game.townList),
      trainPane
    )
    override def update() = {
      informationPane.update(game.money)
      trainPane.update()
    }
}
