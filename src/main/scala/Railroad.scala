import gui.MainGame
import model.Game
import xmlParsing._

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.stage.FileChooser
import scalafx.stage.FileChooser.ExtensionFilter
import java.io.File

import xmlAlert.XMLAlert

object Program extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    master =>
    title.value = "Roolraid Tycoan"
    width = 450
    height = 300
    scene = new Scene {
      content = Seq(
        new Label("Roolraid Tycoan"){
          layoutX <== (master.width - width)/2
          layoutY = 40
        },
        new Button("Choix d'une carte"){
          layoutX <== (master.width - width)/2
          layoutY = 90
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
          layoutY = 140
          onAction = {
            ae => {
              val loader = new XMLParser(new File("src/main/resources/maps/debug_map.xml"))
              val game = loader.getGame()
              stage = new MainGame(game)
            }
          }
        }
      )
    }
  }
}
