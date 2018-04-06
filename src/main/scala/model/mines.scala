package mines

import building._
import factory._
import stuff._
import town._

class Mines(input : List[Stuff], output : Stuff, val ticks : Int, city : Town ) extends Factory(input, output, ticks, city) {
}
