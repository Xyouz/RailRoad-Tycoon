package infoWidget

import infoPane._
import cityPane._
import trainPane._
import planePane._
import scalafx.Includes._
import scalafx.scene.control._
import updatable._
import model._
import train._
import gui._
import scalafx.application.{JFXApp, Platform}

/** This class make the graphical interface more "user-friendly".
 * The idea is that the player choose the category (town, train, plane...) they want to explore.
*/

class InfoWidget(val master : MainGame, val game : Game) extends Accordion() with Updatable{
    maxWidth = 250
    val informationPane = new InfoPane(master)

    val trainPane = new TrainPane(master)

    val cityPane = new CityPane(game.townList)

    val planePane = new PlanePane(master)

    panes = List(
      informationPane,
      cityPane,
      trainPane,
      planePane
    )
    override def update() = {
      informationPane.update(game.money)
      cityPane.update()
      trainPane.update()
      planePane.update()
    }
}
