package xmlAlert

import scalafx.scene.control.Alert.AlertType
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.Alert
import scalafx.Includes._
import gui._



// A class used to make alert window popup when the player doesn't have enough money
class XMLAlert(val master : JFXApp.PrimaryStage, val msg : String) extends Alert(AlertType.Warning) {
  initOwner(master)
  // alertType = AlertType.Warning
  title = "Erreur"
  headerText = "Une erreur est survenue lors du chargement de la map."
  contentText = s"${msg}\nNous sommes désolés de ne pas pouvoir vous en dire plus."
}
