package zoom


import scalafx.Includes._
import updatable._
import scalafx.scene.layout._
import scalafx.scene._
import scalafx.scene.control._
import scalafx.geometry._
import scalafx.event._
import scalafx.beans.property.ReadOnlyDoubleProperty


/** This class is used to change transform coordinates depending on a zoom factor,
 *  this class is useful in the class "GUI".
*/

class Zoom(val stageWidth:ReadOnlyDoubleProperty, val stageHeight:ReadOnlyDoubleProperty) {
  val leftOffset = 300.0
  var maxX = 1000.0
  var minX = -250.0
  var maxY= 700.0
  var minY = -300.0

  def transform(x : Double, y : Double) = {
    var resX = (x - minX) * (stageWidth.value - leftOffset) / (maxX - minX) + leftOffset
    var resY = (y - minY) * ( - stageHeight.value ) / (maxY - minY) + stageHeight.value
    (resX, resY)
  }

  def translate(deltaX : Double, deltaY : Double) = {
    maxX -= deltaX
    minX -= deltaX
    maxY -= deltaY
    minY -= deltaY
  }

  def zoomFactor(factor : Double) = {
    /** factor < 1 : zoom in
     *  factor > 1 : zoom out
    */
    val averageX = (minX + maxX) / 2
    val averageY = (minY + maxY) / 2
    maxX = averageX + factor * (maxX - averageX)
    maxY = averageY + factor * (maxY - averageY)
    minX = averageX + factor * (minX - averageX)
    minY = averageY + factor * (minY - averageY)
  }
}
