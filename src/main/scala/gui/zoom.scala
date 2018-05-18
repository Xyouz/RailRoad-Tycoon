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
  var orthonormal = true
  var maxX = 1000.0
  var minX = -250.0
  var maxY= 700.0
  var minY = -300.0

  def transform(x : Double, y : Double) = {
    if (orthonormal) {
      if (maxX-minX > maxY-minY){
        var deltaY = (maxX-minX)*(stageHeight.value)/(stageWidth.value - leftOffset)
        var ratio = deltaY/(maxY-minY)
        var averageY = (maxY+minY)/2
        maxY = averageY + ratio * (maxY - averageY)
        minY = averageY + ratio * (minY - averageY)
      }
      else {
        var deltaX = (maxX-minX)*(stageWidth.value - leftOffset)/(stageHeight.value)
        var ratio = deltaX/(maxX-minX)
        var averageX = (maxX+minX)/2
        maxX = averageX + ratio * (maxX - averageX)
        minX = averageX + ratio * (minX - averageX)
      }
    }
    var resX = (x - minX) * (stageWidth.value - leftOffset) / (maxX - minX) + leftOffset
    var resY = (y - minY) * ( - stageHeight.value ) / (maxY - minY) + stageHeight.value
    (resX, resY)
  }

  def translate(deltaX : Double, deltaY : Double) = {
    maxX -= deltaX * (maxX - minX)
    minX -= deltaX * (maxX - minX)
    maxY -= deltaY * (maxY - minY)
    minY -= deltaY * (maxY - minY)
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
