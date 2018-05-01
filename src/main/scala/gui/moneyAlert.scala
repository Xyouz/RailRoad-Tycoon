package moneyAlert

import scalafx.scene.control.Alert.AlertType
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.Alert
import scalafx.Includes._
import gui._


/** A class which extends exceptions and which is used when the player doesn't have enough money
*/

case class NotEnoughMoneyException(message: String) extends Exception(message)


/** A class used to make alert window popup when the player doesn't have enough money
*/

class MoneyAlert(val master : MainGame , problem : String) extends Alert(AlertType.Warning) {
  initOwner(master)
  // alertType = AlertType.Warning
  title = "Plus de pognon!!!"
  problem match{
    case "train" => {
      headerText = "Vous manquez d'argent pour créer un nouveau train."
      contentText = "La création du train n'a pas été possible."
    }
    case "plane" => {
      headerText = "Vous manquez d'argent pour créer un nouvel avion."
      contentText = "La création de l'avion n'a pas été possible."
    }
  }
}
