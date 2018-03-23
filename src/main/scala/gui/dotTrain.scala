package dotTrain

import updatable._
import scalafx.Includes._
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import train._
import dotVehicle.CircVehicle

class CircTrain(train : Train) extends CircVehicle(train) with Updatable{
}
