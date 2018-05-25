package xmlParsing

import model._
import scala.xml.{Elem, XML}
import town._
import road._
import point._
import cargo._
import factoryBuilder._
import stuffData._
import java.io.File
import trainEngine.TrainEngine
import planeEngine.PlaneEngine

case class XMLError(msg : String) extends Exception(msg) {

}

class XMLParser(xmlFile : File) {
  val doc = XML.loadFile(xmlFile)
  val engineDoc = XML.loadFile(new File("src/main/resources/engines/engines.xml"))
  val builder = new FactoryBuilder()
  val filler = new StockFiller(1000)
  val game = new Game()
  def unwrapOption( townOp : Option[Town] ) = {
    townOp match {
      case Some(t) => t
      case None => throw new Exception()
    }

  }

  def getEngines() = {
    for (engine <- engineDoc \\ "TrainEngine"){
      val name = engine \@ "name"
      val maxSpeed = (engine \@ "maxSpeed").toDouble
      val maxLoad = (engine \@ "maxLoad").toDouble
      val electric = (engine \@ "electric").toBoolean
      val price = (engine \@ "price").toDouble
      val priceByKm = (engine \@ "priceByKm").toDouble
      game.trainEngineList = new TrainEngine(name, maxSpeed, maxLoad, electric, price, priceByKm) +: game.trainEngineList
    }
    for (engine <- engineDoc \\ "PlaneEngine"){
      val name = engine \@ "name"
      val maxSpeed = (engine \@ "maxSpeed").toDouble
      val maxLoad = (engine \@ "maxLoad").toDouble
      val maxRange = (engine \@ "maxRange").toDouble
      val price = (engine \@ "price").toDouble
      val priceByKm = (engine \@ "priceByKm").toDouble
      game.planeEngineList = new PlaneEngine(name, maxSpeed, maxLoad, maxRange, price, priceByKm) +: game.planeEngineList
    }
  }

  def getGame() = {
    getEngines()
    var towns = Seq[Town]()
    var rails = Seq[Road]()
    var id = 0
    for {town <- doc \\ "City"}{
      var name = town \@ "name"
      var population = (town \@ "population").toInt
      var x = (town \@ "x").toDouble
      var y = (town \@ "y").toDouble
      var airport = (town \ "Airport")
      var newTown = new Town(id, name, population, new Point(x,y))
      newTown.stocks = filler.fill()
      if (airport.length != 0) {
        newTown.hasAirport = true
      }
      for (t<-List("Liquid","Container","Dry")){
        for (i <- 1 to 250){
          newTown.cargosInTown = (new Cargo(t,150)) +: newTown.cargosInTown
        }
      }
      for {factory <- town \\ "Factory"}{
        var typeOfFactory = factory \@ "type"
        try {
          newTown.addFactory(builder.build(typeOfFactory,newTown))
        }
        catch {
          case NotFoundTypeFactoryException() => {
            throw XMLError(s"$typeOfFactory : unknown factory type")
          }
        }
      }
      towns = newTown +: towns
      id = id + 1
    }
    for {connection <- doc \\ "Connection"}{
      var beginName = connection \@ "upstream"
      var endName = connection \@ "downstream"
      var rail = connection \ "Rail"

      if (rail.length != 0){
        rails = new Road(unwrapOption(towns.find(_.name == beginName)),
                         unwrapOption(towns.find(_.name == endName))) +: rails
      }
      else {
        var road = connection \ "Road"

        if (road.length != 0){
          rails = new Road(unwrapOption(towns.find(_.name == beginName)),
                           unwrapOption(towns.find(_.name == endName))) +: rails
        }
      }
    }
    game.loadMap(towns.reverse, rails)
    game.mapName = xmlFile.getAbsolutePath()
    game
  }
}
