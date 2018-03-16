package railMap

import town._
import road._
import point._
import model._
import scala.math._

class RailMap(val allTowns : Seq[Town], val allRoads : Seq[Road])
{
//we need to define a function to find the shortest path between two towns, not necessarilly assuming that the graph of the towns is connex. We chose the algorithm of Floyd-Warshall.
  def shortestPath(towns : Seq[Town], roads : Seq[Road]) : Array[Array[(Road,Town,Double)]] = {
    val nt = towns.length
    val nr = roads.length
    val inf = Double.PositiveInfinity
    val matrix = Array.fill[(Int, Int, Double)](nt,nt)((-1,-1,inf))

   // a function that maps the first two integers of the tuples with the road and the town that respectively correspond to those numbers.
  def maps(mat1 : Array[ Array[(Int, Int, Double)]]) : Array[Array[(Road, Town, Double)]] = {
    val mat2 = Array.ofDim[(Road,Town, Double)](nt,nt)
    for (i <- 0 until nt) {
      for (j <- 0 until nt) {
        mat2(i)(j) = (roads(max(0,mat1(i)(j)._1)), towns(max(0,mat1(i)(j)._2)), mat1(i)(j)._3 )
      }
    }
    mat2
  }

  for (i <- 0 until nt) {
    matrix(i)(i) = (0,i,0)
  }

  for (i <- 0 until nr) {
    var j = roads(i).getEnd.getID()
    var k = roads(i).getStart.getID()
    var l = roads(i).length
    matrix(j)(k) = (i,k,l)
    matrix(k)(j) = (i,j,l)
  }


  //Algorithm of Floyd-warshall itself

  for (k <- 0 until nt){
    for (i <- 0 until nt){
      for (j <- 0 until nt){
        if (matrix(i)(j)._3 > matrix(i)(k)._3 + matrix(k)(j)._3) {
          var d = matrix(i)(k)._3 + matrix(k)(j)._3
          var r = matrix(i)(k)._1
          var n = matrix(i)(k)._2
          matrix(i)(j) = (r,n,d)
        }
      }
    }
  }
  maps(matrix)
}

  val dispatchMatrix = shortestPath(allTowns, allRoads)


  def connectedComponent(town : Town) = {
    var res = Seq[Town]()
    var i = 0
    for ((_,t,d) <- dispatchMatrix(town.getID())){
      if (! (d.isPosInfinity)){
        res = allTowns(i) +: res
      }
      i += 1
    }
    res
}


}
