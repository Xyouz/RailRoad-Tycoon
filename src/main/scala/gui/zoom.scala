package zoom


import scalafx.Includes._
import updatable._
import scalafx.scene.layout._
import scalafx.scene._
import scalafx.scene.control._
import scalafx.geometry._
import scalafx.event._
import javafx.event.EventHandler
import javafx.scene.input.ScrollEvent
import javafx.scene.input.MouseEvent


/** This class is supposed to allow the player to zoom in and out,
 * this class is useful in the class "GUI".
*/

class Zoom() extends Pane {
  var zoomMini : Double = 0
  var zoomMaxi : Double = Double.PositiveInfinity
  onScroll = new EventHandler[ScrollEvent] {
    override def handle(event : ScrollEvent) : Unit = {
      var zoomingBy : Double = 2.0
      if (event.getDeltaY() <= 0) {
        zoomingBy = 1 / zoomingBy
      }
      zoomable(zoomingBy)
      event.consume()
    }
  }

  def zoomable(coeff : Double) = {
    var factor = scaleX() * coeff
    if (factor > zoomMaxi) {
      factor = zoomMaxi
    }
    if (factor < zoomMini) {
      factor = zoomMini
    }
  }

}
