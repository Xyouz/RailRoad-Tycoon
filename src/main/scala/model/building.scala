package building

import stuff._
import town._

/** The abstact class Building is useful in classes sharing the following thing :
 * -It concerns facilities that deal with a certain amount of stocks
 * This is extended in the class "Factory"
*/

abstract class Building(val input : List[Stuff], val output : Stuff, val city : Town){
  var stocks = List[Stuff]()
  var bufferOut = output.copy()
  for {i <- input} {
    stocks = stocks :+ i.copy()
  }
  var funds = 10000.0
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
    else {
    }
  }
  def update()
}
