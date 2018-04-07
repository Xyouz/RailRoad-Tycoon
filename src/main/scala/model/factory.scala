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
  override def giveOutput() = {output}
  override def update() = {
    time += 1
    //          \/ mettre ticks
    if (time == 100) {
      takeInput()
      if (city.getID == 0){
        println("update")
        stocks foreach {t=>println(s"${t.name} ${t.quantity}")}
      }
      for (j <- stocks) {  
        j.consumeStuff(3)
        time = 0
        println(j.quantity)
      }
    }
  }
  def sendTo() = {} // choisir le type de véhicule avec lequel on veut envoyer les stuff (et ce vers city)
}
