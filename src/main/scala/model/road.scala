package road

import town._
import point._
import train._

// A class to represent road between two Towns
class Road(val begin : Town,val end : Town){
  val townsVec = end.position - begin.position
  val length = townsVec.norm()
  var trainsAB = Seq[Train]()
  var trainsBA = Seq[Train]()
  def getStart() = {begin}
  def getEnd() = {end}
  def numberOfTrains() = {trainsAB.length + trainsBA.length}
  def _posTrain(t : Train, b : Boolean) = {
    var distToBegin = t.distance
    if (!b) {distToBegin = length - distToBegin}
    begin.position + townsVec.scale(distToBegin/length)
  }
  def getTrainsPos() = {
    (trainsAB.map(_posTrain(_,true)))++(trainsBA.map(_posTrain(_,false)))
  }
  def update() = {
    var arrived = Seq[(Train,Int)]()
    for (train <- trainsAB){
      var dist = train.update()
      train.setPosition( begin.position + townsVec.scale(dist/length) )
      if (dist >= length){
        train.setPosition(new Point(-10000,-10000))
        arrived = arrived :+ ((train, end.getID()))
        trainsAB = trainsAB.filter(_ != train)
      }
    }
    for (train <- trainsBA){
      var dist = train.update()
      dist = length - dist
      train.setPosition(begin.position + townsVec.scale(dist/length))
      if (dist <= 0){
        train.setPosition(new Point(-10000,-10000))
        arrived = arrived :+ ((train, begin.getID()))
        trainsBA = trainsBA.filter(_ != train)
      }
    }
    arrived
  }

  def launchTrain(train : Train, currentTown : Town) = {
    if (currentTown == begin){
      trainsAB = trainsAB :+ train
      }
    else{
      trainsBA = trainsBA :+ train
      }
  }

}
