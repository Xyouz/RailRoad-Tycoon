package trainCargoRouter

import model._
import town._
import cargo._
import scala.collection.mutable.PriorityQueue
import math.max

/** This class find the best way to carry cargo on the rail network,
  * promoting the use of hub when long distances are to be travelled.
 */
case class NotRailConnectedError() extends Exception()

class TrainCargoRouter(val game : Game) {
  var nt = game.townList.length
  var distances = Array.fill[Double](nt)(0)
  var prec = Array.fill[Int](nt)(-1)
  var visited = Array.fill[Boolean](nt)(false)
  var heap = PriorityQueue[(Double,Int)]()
  var briefing = List[Int]()

  def oneStepReachable(t1 : Town, t2 : Town) = {
    game.railMap.neighboor(t1,t2) && (t1 != t2)
  }

  def neighboors(t : Town) = {
    game.townList.filter(oneStepReachable(t,_))
  }

  def initialize(startTownID : Int, endTownID : Int) = {
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

  def distanceMultiplier(t1 : Town, t2 : Town) = {
    if (t1.isHub && t2.isHub) {
      0.6
    }
    else {
      1.0
    }
  }

  def explore(start : Town, end : Town) = {
    var currentTown = start
    while (heap.length != 0 && currentTown != end){
      val (dist,city) = heap.dequeue()
      if (! visited(city)) {
        visited(city) = true
        if (city != end.getID) {
          for {town <- neighboors(intToTown(city))} {
            val multiplier = distanceMultiplier(town,intToTown(city))
            val disTown = distances(city) + multiplier * town.pos.distance(intToTown(city).pos)
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
      if (currentTown == -1){
        throw new NotRailConnectedError()
      }
      briefing = currentTown +: briefing
    }
  }

  def whichHubs(start : Town, end : Town) = {
    initialize(start.getID, end.getID)
    explore(start, end)
    try {
      makeBriefing(start, end)
    } catch {
      case NotRailConnectedError() => None
    }
    val fullbriefing = briefing.map(intToTown(_)).filter(_.isHub)
    if (fullbriefing.length >= 2){
      Some((fullbriefing(0),fullbriefing(fullbriefing.length-1)))
    }
    else {
      None
    }
  }


}
