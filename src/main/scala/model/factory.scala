package factory

import building._
import stuff._
import town._


class Factory(input : List[Stuff], output : Stuff, ticks : Int, city : Town) extends Building(input, output, city){
  var time = 0
  override def takeInput() = {
    for (i <- 0 until input.length){
      for (j <- city.stocks){
        try {
          j.transferTo(stocks(i),input(i))
        } catch {
          case _ : Throwable => ()//println("à changer")
        }
      }
    }
  }
  override def update() = {
    time += 1
    if (time == ticks) {
      funds = funds - 200
      takeInput()
      giveOutput()
      funds = funds + city.receiveStuff(output)
      output.quantity = 0
    }
  }
  def sendTo() = {} // choisir le type de véhicule avec lequel on veut envoyer les stuff (et ce vers city)
}
