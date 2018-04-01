package railMap

import town._
import road._
import point._
import model._
import scala.math._

class RailMap(val allTowns : Seq[Town], val allRoads : Seq[Road])
{
// we need to define a function to find the shortest path between two towns,
// not necessarilly assuming that the graph of the towns is connex.
// We chose the algorithm of Floyd-Warshall.
  val nt = allTowns.length
  val nr = allRoads.length
  val inf = Double.PositiveInfinity
  val matrixLength = Array.fill[Double](nt, nt)(inf)
  val matrixRoads = Array.fill[Road](nt, nt)(allRoads(0))

  def shortestPath() = {
    for (i <- 0 until nt) {
      matrixLength(i)(i) = 0
    }

    for (r <- allRoads) {
      var j = r.getEnd.getID()
      var k = r.getStart.getID()
      var l = r.length
      matrixLength(j)(k) = l
      matrixLength(k)(j) = l
      matrixRoads(j)(k) = r
      matrixRoads(k)(j) = r
    }


    //Algorithm of Floyd-warshall itself

    for (k <- 0 until nt){
      for (i <- 0 until nt){
        for (j <- 0 until nt){
          if (matrixLength(i)(j) > matrixLength(i)(k) + matrixLength(k)(j)) {
            var d = matrixLength(i)(k) + matrixLength(k)(j)
            var r = matrixRoads(i)(k)
            matrixLength(i)(j) = d
            matrixRoads(i)(j) = r
          }
        }
      }
    }
  }

  shortestPath()

  def connectedComponent(town : Town) = {
    var res = Seq[Town]()
    var i = town.getID()
    for (j <- 0 until nt) {
      if (! (matrixLength(i)(j).isPosInfinity) ){
        res = allTowns(j) +: res
      }
    }
    res
  }

  def nextRoad(currentTownID : Int, nextTownID : Int) = {
    matrixRoads(currentTownID)(nextTownID)
  }


}
