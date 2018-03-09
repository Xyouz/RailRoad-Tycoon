package road

import model.town
import model.point
import model.train

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
    var distToBegin = t.distanceOnRoad
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
      if (dist >= length){
        arrived = arrived :+ ((train, end.getID()))
        trainsAB = trainsAB.filter(_ != train)
      }
    }
    for (train <- trainsBA){
      var dist = train.update()
      if (dist >= length){
        arrived = arrived :+ ((train, begin.getID()))
        trainsBA = trainsBA.filter(_ != train)
      }
    }
  arrived
  }

  def launchTrain(train : Train, destination : Town) ={
    if (destination == end){
      trainsAB = trainsAB :+ train
      }
    else{
      trainsBA = trainsBA :+ train
      }
  }

}
