package factory

import building._
import stuff._

abstract class Factory(input : List[Stuff], output : Stuff, val ticks : Int) extends Building(input, output){

}
