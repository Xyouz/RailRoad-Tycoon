import gui.MainGame
import model.Game
import xmlParsing._

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.stage.FileChooser
import scalafx.stage.FileChooser.ExtensionFilter

object Program extends JFXApp {
  // val loader = new XMLParser("src/main/resources/maps/debug_map.xml")
  // val game = loader.getGame()
  // stage = new MainGame(game)

  stage = new JFXApp.PrimaryStage {
    title.value = "Hello Stage"
    width = 600
    height = 450
    scene = new Scene {
      content = Seq(
        new Label("Roolraid Tycoan"),
        new Button("Paf"){
          onAction = {ae => {
            val fileChooser = new FileChooser(){
              title = "Choisissez une carte."
            }
            val mapFile = fileChooser.showOpenDialog(stage)
          }
        }},
        new Button("Pif")
      )
    }
  }
}
