package mines

import building._
import factory._
import stuff._
import town._

/** A mine bahaves the same way a factory does, so it extends the class "Factory".
*/

class Mines(input : List[Stuff], output : Stuff, val ticks : Int, city : Town ) extends Factory(input, output, ticks, city) {

}
