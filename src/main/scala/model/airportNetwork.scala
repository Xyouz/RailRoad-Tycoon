package airportNetwork

import model._
import town._

class AirportNetwork(val game : Game) {
  var nt = game.townList.length
  var distances = Array.fill[Double](nt)(0)

  def oneStepReachable(t1 : Town, t2 : Town, range : Double) = {
    t1.hasAirport && t2.hasAirport && (range >= t1.pos.distance(t2.pos)) && (t1 != t2)
  }

  def neighboors(t : Town, range : Double) = {
    game.townList.filter(oneStepReachable(t,_,range))
  }

  def initialize(startTownID : Int) = {
    distances = Array.fill[Double](nt)(Double.NegativeInfinity)
    distances(startTownID) = 0
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
