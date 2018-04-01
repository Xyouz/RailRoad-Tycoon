package xmlParsing

import model._
import scala.xml.{Elem, XML}
import town._
import road._
import point._

class XMLParser(xmlFile : String) {
  val doc = XML.loadFile(xmlFile)

  def unwrapOption( townOp : Option[Town] ) = {
    townOp match {
      case Some(t) => t
      case None => throw new Exception()
    }

  }

  def getGame() = {
    var towns = Seq[Town]()
    var rails = Seq[Road]()
    var id = 0
    for {town <- doc \\ "City"}{
      var name = town \@ "name"
      var population = (town \@ "population").toInt
      var x = (town \@ "x").toDouble
      var y = (town \@ "y").toDouble
      towns = new Town(id, name, population, new Point(x,y)) +: towns
      id = id + 1
    }
    for {connection <- doc \\ "Connection"}{
      var beginName = connection \@ "upstream"
      var endName = connection \@ "downstream"
      rails = new Road(unwrapOption(towns.find(_.name == beginName)),
                       unwrapOption(towns.find(_.name == endName))) +: rails
    }
    val game = new Game()
    game.loadMap(towns.reverse, rails)
    game
  }
}
