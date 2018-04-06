import gui.MainGame
import model.Game
import xmlParsing._

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._


object Program extends JFXApp {
  val loader = new XMLParser("src/main/resources/maps/debug_map.xml")
  val game = loader.getGame()

  stage = new JFXApp.PrimaryStage {
    title.value = "Hello Stage"
    width = 600
    height = 450
    scene = new Scene {
      content = new Button("Paf"){
        onAction = {ae => stage = new MainGame(game)}
      }
    }
  }  
}
