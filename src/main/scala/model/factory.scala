package factory

import building._
import stuff._
import town._


class Factory(input : List[Stuff], output : Stuff, ticks : Int, city : Town) extends Building(input, output, city){
  var time = 0
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
    time += 1
    if (time == 200) {
      for (j <- stocks) {
        j.consumeStuff(3)
        time = 0
        println(j.quantity)
      }
    }
  }
  def sendTo() = {} // choisir le type de véhicule avec lequel on veut envoyer les stuff (et ce vers city)
}
