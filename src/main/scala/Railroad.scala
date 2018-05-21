import gui.MainGame
import model.Game
import xmlParsing._

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.stage.{FileChooser,DirectoryChooser}
import scalafx.stage.FileChooser.ExtensionFilter
import java.io.File
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{Stops, LinearGradient}
import scalafx.scene.text.Text

import saveLoader._
import xmlAlert.XMLAlert

object Program extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    master =>
    title.value = "Roolraid Tycoan"
    width = 600
    height = 300
    scene = new Scene {
      content = Seq(
        new Label("Roolraid Tycoan"){
          layoutX <== (master.width - width)/2
          layoutY = 40
          style = "-fx-font-size: 48pt"
          fill = new LinearGradient(
            endX = 0,
            stops = Stops(Cyan, DodgerBlue)
          )
          effect = new DropShadow {
            color = DodgerBlue
            radius = 25
            spread = 0.25
          }
        },
        new Button("Choix d'une carte"){
          layoutX <== (master.width - width)/2
          layoutY = 140
          onAction = {
            ae => {
              val fileChooser = new FileChooser(){
                initialFileName = "src/main/resources/maps/"
                title = "Choisissez une carte."
                extensionFilters ++= Seq(new ExtensionFilter("XML Map", "*.xml"))
              }
              val mapFile = fileChooser.showOpenDialog(stage)
              mapFile match {
                case null => ()
                case file : java.io.File => {
                  try {
                    val loader = new XMLParser(file)
                    val game = loader.getGame()
                    stage = new MainGame(game)
                  }
                  catch {
                    case XMLError(msg) => {
                      val alert = new XMLAlert(master, msg)
                      alert.showAndWait()
                    }
                  }
                }
              }
            }
          }
        },
        new Button("Nouvelle partie"){
          layoutX <== (master.width - width)/2
          layoutY = 190
          onAction = {
            ae => {
              val loader = new XMLParser(new File("src/main/resources/maps/debug_map.xml"))
              val game = loader.getGame()
              stage = new MainGame(game)
            }
          }
        },
        new Button("Charger une sauvegarde"){
          layoutX <== (master.width - width)/2
          layoutY = 240
          onAction = {
            ae => {
              val dirChooser = new DirectoryChooser(){
                initialDirectory = new File("saves/")
                title = "Choisissez une un dossier contenant la sauvegarde."
              }
              val mapFile = dirChooser.showDialog(stage)
              mapFile match {
                case null => ()
                case file : File => {
                  stage = SaveLoader.load(file.getAbsolutePath)
                }
              }
            }
          }
        }
      )
    }
  }
}
