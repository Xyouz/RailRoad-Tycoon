package dotTrain

import updatable._
import scalafx.Includes._
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import train._
import dotVehicle.CircVehicle
import zoom.Zoom

/** This class turns trains into light blue points on the interface.
*/

class CircTrain(train : Train, zoom : Zoom) extends CircVehicle(train,zoom) with Updatable{

}
