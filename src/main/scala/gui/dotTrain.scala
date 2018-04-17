package dotTrain

import updatable._
import scalafx.Includes._
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import train._
import dotVehicle.CircVehicle

/** This class turns trains into light blue points on the interface.
*/

class CircTrain(train : Train) extends CircVehicle(train) with Updatable{
  translateX = 500
  translateY = 300
}
