package lineRoad

import updatable._
import town._
import road._
import scalafx.Includes._
import scalafx.application.{JFXApp}
import scalafx.scene.control._
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.shape.{Line}

class LineRoad(val master : JFXApp.PrimaryStage, val road : Road) extends Line with Updatable {
  startX = road.getStart().position().x_coord()
  startY = road.getStart().position().y_coord()
  endX = road.getEnd().position().x_coord()
  endY = road.getEnd().position().y_coord()
  translateX = 500
  translateY = 300
  strokeWidth = 8
  onMouseClicked = {
    ae =>
      new Alert(AlertType.Information) {
        initOwner(master)
        title = "Route"
        headerText = s"Voici quelques informations à propos de la route reliant ${road.begin} à ${road.end}: "
        contentText = s"Actuellement ${road.numberOfTrains()} trains circulent sur cette voie"
      }.showAndWait()
  }
}
