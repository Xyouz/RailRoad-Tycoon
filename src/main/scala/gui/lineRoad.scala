package lineRoad

import updatable._
import town._
import road._
import scalafx.Includes._
import scalafx.application.{JFXApp}
import scalafx.scene.control._
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.shape.{Line}
import zoom.Zoom

/** This class enables to have information on the vehicles that are on the selected road
*/

class LineRoad(val master : JFXApp.PrimaryStage, val road : Road, val zoom : Zoom) extends Line with Updatable {

  update()
  strokeWidth = 4

  onMouseClicked = {
    ae =>
      new Alert(AlertType.Information) {
        initOwner(master)
        title = "Route"
        headerText = s"Voici quelques informations à propos de la route reliant ${road.begin} à ${road.end}: "
        contentText = s"Actuellement ${road.numberOfTrains()} trains circulent sur cette voie"
      }.showAndWait()
  }

  override def update() = {
    val (sX,sY) = zoom.transform(road.getStart().position().x_coord(),road.getStart().position().y_coord())
    startX = sX
    startY = sY
    val (eX,eY) = zoom.transform(road.getEnd().position().x_coord(),road.getEnd().position().y_coord())
    endX = eX
    endY = eY
  }
}
