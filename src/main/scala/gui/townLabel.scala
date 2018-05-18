package townLabel

import updatable.Updatable
import scalafx.scene.control.Label
import zoom.Zoom
import town.Town
import math._
import scalafx.scene.paint.Color._

class TownLabel(val town : Town,val zoom : Zoom) extends Label with Updatable {
  text = town.name
  textFill = DarkBlue
  val visibilityThreshold = 150
  def visibilityTest() = {
    (zoom.maxX-zoom.minX < visibilityThreshold) ||
    (zoom.maxY-zoom.minY < visibilityThreshold) ||
    (town.population > 750000)
  }

  override def update() = {
    val (x,y) = zoom.transform(town.position().x,town.position().y)
    layoutX = x
    layoutY = y
    if (visibilityTest()){
      visible = true
    }
    else {
      visible = false
    }
  }
}
