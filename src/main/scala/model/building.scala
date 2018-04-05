package building

import stuff._
import town._

abstract class Building(val input : List[Stuff], val output : Stuff, val city : Town){
  var funds = 1000
  def takeInput()
  def giveOutput()
  def updates()
  def stock()
}
