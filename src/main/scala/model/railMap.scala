package railMap

import town._
import road._
import point._
import model._
import scala.math._

class RailMap(val allTowns : Seq[Town], val allRoads : Seq[Road])
{
//we need to define a function to find the shortest path between two towns, not necessarilly assuming that the graph of the towns is connex. We chose the algorithm of Floyd-Warshall.
  val nt = allTowns.length
  val nr = allRoads.length
  val inf = Double.PositiveInfinity
  val matrixLength = Array.fill[Double](nt, nt)(inf)
  val matrixRoads = Array.fill[Int](nt, nt)(-1)

  def shortestPath(towns : Seq[Town], roads : Seq[Road]) : Array[Array[Double]] = {


   // a function that maps the first two integers of the tuples with the road and the town that respectively correspond to those numbers.
  def maps1(matou :  Array[ Array[Int]]) : Array[Array[Road]] = {
    val mat2 = Array.ofDim[Road](nt,nt)
    for (i <- 0 until nt) {
      for (j <- 0 until nt) {
        mat2(i)(j) = (roads(max(0,matou(i)(j))))
      }
    }
    mat2
  }

  for (i <- 0 until nt) {
    matrixLength(i)(i) = 0
  }

  for (i <- 0 until nr) {
    var j = roads(i).getEnd.getID()
    var k = roads(i).getStart.getID()
    var l = roads(i).length
    matrixLength(j)(k) = l
    matrixLength(k)(j) = l
    matrixRoads(j)(k) = 1
    matrixRoads(k)(j) = 1
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
  //maps1(matrixRoads)
  matrixLength
}

//  val dispatchMatrix = shortestPath(allTowns, allRoads)


  def connectedComponent(town : Town) = {
    var res = Seq[Town]()
    for (i <- 0 until nt) {
      for (j <- 0 until nt) {
        if (! (matrixLength(i)(j).isPosInfinity) ){
          res = allTowns(i) +: res
        }
      }
    }
    res
  }


}
