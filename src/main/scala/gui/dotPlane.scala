package dotPlane

import updatable._
import scalafx.Includes._
import scalafx.scene.shape.{Circle}
import scalafx.scene.paint.Color._
import plane._
import dotVehicle.CircVehicle
import zoom.Zoom
/** This class turns planes into navy points on the interface.
*/

class CircPlane(plane : Plane, zoom : Zoom) extends CircVehicle(plane, zoom) with Updatable{
  fill = Aquamarine
}
