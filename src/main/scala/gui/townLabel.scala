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
  def visibilityTest() = {
    (zoom.maxX-zoom.minX < 100) || (zoom.maxY-zoom.minY < 100) || (town.population > 800000)
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
