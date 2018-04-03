package town

import point._
import train._
import plane._
import good._
import stuff._
import sendTrainDialog._

case class NoAirportException() extends Exception()


// a class to implement the towns of the graphs with information on the name,
// the population, their wealth and methods to update them when a train come over
class Town(val id : Int, val name: String, var pop : Int,
  //val listofgoods :List[Good],
  // val leaving_roads : List[(Town)], // val coming_roads : List[(Town)],
  var pos : Point){
  var railwayStation = List[Train]()
  var airport = List[Plane]()
  var factories = List[String]()
  var hasAirport = false

  override def toString() = {name}
  def addFactory(name : String) = {
    factories = name +: factories
  }
  def getID() : Int = {id}
  def getName() : String = {name}
  def position() : Point={pos}
  def population() : Int={pop}
  def deltaPopulation(delta : Int) = {pop += delta}
  def incrPop() = {pop = pop+50}
  def update(){

  }

  def getAirport = {
    if (hasAirport) {
      airport
    }
    else {
      throw new NoAirportException
    }
  }

  //def leavingPopulation() : Int={(pop/10).toInt}
  var lpop = (pop/10).toInt
  def welcomeTrain(train : Train) = {
    railwayStation = train :: railwayStation
  }

  def welcomePlane(plane : Plane) = {
    airport = plane +: airport
    plane.setCurrentTown(Some(this))
  }

  def loadTrain(train : Train) = {
    println("/!  fonction town.loadTrain à écrire")
    pop -= lpop
    train.loading += lpop
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

  def receiveStuff(unloaded : Stuff) = {}
}
