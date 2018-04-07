package town

import point._
import train._
import plane._
import good._
import stuff._
import sendTrainDialog._
import factory._
import scala.math.max
import stuffData._

case class NoAirportException() extends Exception()


// a class to implement the towns of the graphs with information on the name,
// the population, their wealth and methods to update them when a train come over
class Town(val id : Int, val name: String, var pop : Int, var pos : Point){
  var railwayStation = List[Train]()
  var airport = List[Plane]()
  var factories = List[Factory]()
  var stocks = List[Stuff]()
  var hasAirport = false

  override def toString() = {name}
  def addFactory(plan : Factory) = {
    factories = plan +: factories
  }
  def getID() : Int = {id}
  def getName() : String = {name}
  def position() : Point={pos}
  def population() : Int={pop}
  def deltaPopulation(delta : Int) = {pop += delta}
  def incrPop() = {pop = pop+50}

  def priceOfStuff(stuff : Stuff):Double = {
    var i = stocks.find(_==stuff)
    i match {
      case Some(s) => max(0,s.maxPrice - 0.5*(s.quantity))
      case None => throw new Exception
    }
  }

  val consuptionStuff = List(new Aluminum(1), new BakedGoods(1), new Beer(1),
                             new Bricks(1), new CannedFood(1), new Cement(1),
                             new Chemicals(1), new Electronics(1), new Fish(1),
                             new Fruit(1), new Fuel(1), new Furniture(1),
                             new Glass(1), new Grain(1), new Leather(1),
                             new Liquor(1), new Liquor(1), new Lumber(1),
                             new Marble(1), new Meat(1), new Milk(1),
                             new Paper(1), new Plastics(1), new Press(1),
                             new Rubber(1), new Steel(1), new Textiles(1),
                             new Tyres(1), new Vegetables(1), new Vehicles(1),
                             new Wine(1) )
  def cityConsumption() = {
    var i = 0
    var j = 0
    while ((i < consuptionStuff.length) && (j < stocks.length)){
      if (consuptionStuff(i) == stocks(j)) {
        try {
          stocks(j).subStuff(consuptionStuff(i).scale(0.001* population))
        }
        catch {
          case NotEnoughQuantityException() => ()//println("pas assez de bouffe")
        }
        i = i + 1
        j = j + 1
      }
      else {
        j = j + 1
      }
    }
  }

  var t = 0

  def update(){
    t = t + 1
    if (t==200) {
      t = 0
      cityConsumption()
    }
    factories.map(_.update())
  }

  def getAirport = {
    if (hasAirport) {
      airport
    }
    else {
      throw new NoAirportException
    }
  }

  var lpop = (pop/10).toInt
  def welcomeTrain(train : Train) = {
    railwayStation = train :: railwayStation
  }

  def welcomePlane(plane : Plane) = {
    airport = plane +: airport
    plane.setCurrentTown(Some(this))
  }

  var excedent = 12.0

  def loadTrain(train : Train) = {
    pop -= lpop
    train.loading += lpop
    for (i <- stocks) {
      for (j <- train.wagons()){
        if (j.kindOfLoad() == i.stuffCategory() && i.quantity > excedent) {
          var toSend = new Stuff(i.name, (excedent - i.quantity), 12.0, i.category)
          j.load(toSend)
          i.subStuff(toSend)
        }
      }
    }
  }

  def loadPlane(plane : Plane) = {
    println("TODO  loadPlaane")
  }

  def hasTrains() : Boolean = { ! railwayStation.isEmpty}
  def goodbyeTrain(train : Train) : Boolean = {
    val n = railwayStation.length
    railwayStation = railwayStation.filter(_!=train)
    (n != railwayStation.length)
  }

  def receiveStuff(unloaded : Stuff) = {
    for (i <- stocks) {
      if (i==unloaded){
        var res = priceOfStuff(i)*unloaded.quantity
        i.addStuff(unloaded)
        res
      }
    }
    0.0
  }
}
