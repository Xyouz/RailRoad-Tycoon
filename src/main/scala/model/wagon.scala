package wagon

import stuff._
import cargo._
import train._


class Wagon(name : String, maxLoad : Double) extends Cargo(name, maxLoad){
  def typeOfStuff() = {} 
}
