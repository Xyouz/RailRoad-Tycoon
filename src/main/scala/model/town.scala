package town

import point._
import train._
import good._

// a class to implement the towns of the graphs with information on the name,
// the population, their wealth and methods to update them when a train come over
class Town(val id : Int,
  val name: String,
  var pop : Int,
  val listofgoods :List[Good],
  // val leaving_roads : List[(Town)],
  // val coming_roads : List[(Town)],
  var pos : Point)
  {
  override def toString() = {name}
  var railwayStation = List[Train]()
  def getID() : Int = {id}
  def getName() : String = {name}
  def position() : Point={pos}
  def population() : Int={pop}
  def deltaPopulation(delta : Int) = {pop += delta}
  def incrPop() = {pop = pop+50}
  def update(){
  //  pop += 20
  }
  def welcomeTrain(train : Train) = {
    railwayStation = train :: railwayStation
    pop += train.loading;
    train.loading = 0
  }
  def hasTrains() : Boolean = { ! railwayStation.isEmpty}
  def goodbyeTrain(train : Train) : Boolean = {
    val n = railwayStation.length
    railwayStation = railwayStation.filter(_!=train)
    (n != railwayStation.length)
  }
}
