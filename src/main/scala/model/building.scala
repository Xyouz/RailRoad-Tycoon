package building

import stuff._
import town._

abstract class Building(val input : List[Stuff], val output : Stuff, val city : Town){
  var stocks = List[Stuff]()
  var bufferOut = output.copy()
  for {i <- input} {
    stocks = stocks :+ i.copy()
  }
  var funds = 1000
  def takeInput()
  def giveOutput() = {
    var token = true
    var i = 0
    while (token && i<input.length){
      token = stocks(i).hasEnough(input(i))
      i = i + 1
    }
    if (token) {
      for {i <- 0 until input.length} {
        stocks(i).subStuff(input(i))
      }
      bufferOut.addStuff(output)
    }
  }
  def update()
}
