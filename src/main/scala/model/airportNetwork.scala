package airportNetwork

import model._
import town._
import scala.collection.mutable.PriorityQueue

/** This class enables us to send planes into cities that actually own an airport.
 * If a plane must fly over several town, we use Dijkstra algorithm to find the shortest path.
 */

class AirportNetwork(val game : Game) {
  var nt = game.townList.length
  var distances = Array.fill[Double](nt)(0)
  var prec = Array.fill[Int](nt)(-1)
  var visited = Array.fill[Boolean](nt)(false)
  var heap = PriorityQueue[(Double,Int)]()
  var briefing = List[Int]()
  def oneStepReachable(t1 : Town, t2 : Town, range : Double) = {
    t1.hasAirport && t2.hasAirport && (range >= t1.pos.distance(t2.pos)) && (t1 != t2)
  }

  def neighboors(t : Town, range : Double) = {
    game.townList.filter(oneStepReachable(t,_,range))
  }

  def initialize(startTownID : Int, endTownID : Int) = {
    nt = game.townList.length
    distances = Array.fill[Double](nt)(Double.PositiveInfinity)
    prec = Array.fill[Int](nt)(-1)
    visited = Array.fill(nt)(false)
    distances(startTownID) = 0
    heap = PriorityQueue((0.0,startTownID))(
      implicitly[Ordering[(Double, Int)]].reverse
    )
    briefing = List[Int](endTownID)
  }

  def intToTown(n : Int) = {
    game.townList(n)
  }

  def explore(start : Town, end : Town, range : Double) = {
    var currentTown = start
    while (heap.length != 0 && currentTown != end){
      val (dist,city) = heap.dequeue()
      if (! visited(city)) {
        visited(city) = true
        if (city != end.getID) {
          for {town <- neighboors(intToTown(city),range)} {
            val disTown = distances(city) + town.pos.distance(intToTown(city).pos)
            if (disTown < distances(town.getID)) {
              distances(town.getID) = disTown
              prec(town.getID) = city
              heap.enqueue((disTown,town.getID))
            }
          }
        }
      }
    }
  }

  def makeBriefing(start : Town, end : Town) = {
    var currentTown = end.getID
    while (currentTown != start.getID) {
      currentTown = prec(currentTown)
      briefing = currentTown +: briefing
    }
  }

  def getBriefing(start : Town, end : Town, range : Double) = {
    initialize(start.getID, end.getID)
    explore(start, end, range)
    makeBriefing(start, end)
    briefing.toArray.map(intToTown(_))
  }

  def connectedComponent(town : Town, range : Double) : Seq[Town] = {
    var result = Seq[Town]()
    nt = game.townList.length
    var connected = Array.fill[Boolean](nt)(false)
    var toExplore = Seq[Town](town)
    while (toExplore.length >= 1) {
      var c_town = toExplore.head
      toExplore = toExplore.tail
      if (false == connected(c_town.getID)) {
        connected(c_town.getID) = true
        result = c_town +: result
        toExplore = neighboors(c_town,range) ++ toExplore
      }
    }
    result
  }


}
