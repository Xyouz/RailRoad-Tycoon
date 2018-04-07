package town

import point._
import train._
import plane._
import good._
import stuff._
import sendTrainDialog._
import factory._
import scala.math.max

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



  def update(){
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
