package building

import stuff._

abstract class Building(val input : List[Stuff], val output : Stuff){
  var funds = 1000
  def takeInput()
  def giveOutput()
}
