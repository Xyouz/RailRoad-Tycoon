import gui._
import model._

import scalafx.Includes._
import scalafx.application.JFXApp


object Program extends JFXApp
{
    val game = new Game()
    game.init()
    stage = new MainGame(game)
}
