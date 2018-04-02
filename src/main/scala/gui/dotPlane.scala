package dotPlane

import updatable._
import scalafx.Includes._
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import plane._
import dotVehicle.CircVehicle

class CircPlane(plane : Plane) extends CircVehicle(plane) with Updatable{
  translateX = 500
  translateY = 300
  fill = Aquamarine
}
