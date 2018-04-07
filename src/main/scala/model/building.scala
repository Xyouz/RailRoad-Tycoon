package building

import stuff._
import town._

abstract class Building(val input : List[Stuff], val output : Stuff, val city : Town){
  var stocks = List[Stuff]()
  for {i <- input} {
    stocks = stocks :+ i.copy()
  }
  var funds = 1000
  def takeInput()
  def giveOutput() : Stuff
  def update()
}
