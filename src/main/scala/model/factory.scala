package factory

import building._
import stuff._
import town._
import infoPane._


class Factory(input : List[Stuff], output : Stuff, val ticks : Int, city : Town) extends Building(input, output, city){
  var time = 0
  def runningTime() = { //synchroniser les ticks et time...
    while (true) {

    }
  }
  override def takeInput() = {
    for (i <- input){
      for (j <- stocks) {
        if (j.equals(i)) {
          j.addQuantity(i)
          i.quantity = 0.0
        }
      }
    }
  }
  override def giveOutput() = {output}
  override def updates() = {
    if (time == 200) {
      for (j <- stocks) {
        j.consumeStuff(3)
        time = 0
      }
    }
  }
}
