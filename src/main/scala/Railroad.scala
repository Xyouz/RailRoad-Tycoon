import gui.MainGame
import model.Game

import scalafx.Includes._
import scalafx.application.JFXApp


object Program extends JFXApp {
  val game = new Game()
  stage = new MainGame(game)
}
