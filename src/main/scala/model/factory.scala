package factory

import building._
import stuff._
import town._

class Factory(input : List[Stuff], output : Stuff, val ticks : Int, city : Town) extends Building(input, output, city){
  override def takeInput() = {
    for (i <- input){

    }
  }
  override def giveOutput() = {}
  override def updates() = {}
  override def stock() = {}
}
