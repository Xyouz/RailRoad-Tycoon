package factory

import building._
import stuff._
import town._

/** It extends the class "Building". It takes as an input one or more goods, and transform it into an ouput.
 * A transformation occurs after a certain amount of ticks: it is the time the factory needs to produce something.
*/

class Factory(input : List[Stuff], output : Stuff, ticks : Int, city : Town) extends Building(input, output, city){
  var time = 0
  override def takeInput() = {
    var s = 0
    var i = 0
    var j = 0
    while ((i < input.length)&&(j< city.stocks.length)){
      if (input(i)==city.stocks(j)){
        if (funds >= city.priceOfStuff(input(i)) && city.stocks(j).hasEnough(input(i)))
        {
          s += 1
          city.stocks(j).transferTo(stocks(i),input(i))
          funds -= city.priceOfStuff(input(i))
        }
        else {
          city.message += s"Les usines n'ont pas de quoi fonctionner, on manque notamment de ${input(i)}.\n"
        }
        i += 1
        j += 1
      }
      else {
        j += 1
      }
    }
  }
  override def update() = {
    time += 1
    if (time == ticks) {
      time = 0
      funds = funds - 200
      giveOutput()
      funds = funds + city.receiveStuff(bufferOut)
      bufferOut.quantity = 0
      takeInput()
    }
  }
  def sendTo() = {} // choisir le type de véhicule avec lequel on veut envoyer les stuff (et ce vers city)
}
