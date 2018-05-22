package saveLoader

import saveUtils._

import town._
import point._
import trainEngine._
import planeEngine._
import stuff._
import cargo._
import factory._
import train._
import plane._
import model._

import fileReader._
import xmlParsing._

import java.io.File
import gui._


object SaveLoader {
  def load(path : String) = {
    val gameString = Reader.read(path + "/game.json")
    val gameData = JsonUtil.fromJson[GameData](gameString)
    val loader = new XMLParser(new File(gameData.mapName))
    val game = loader.getGame()
    game.money = gameData.money
    val stage = new MainGame(game)
    for (townFile <- (new File(path + "/towns")).listFiles) {
      val townString = Reader.read(townFile)
      val townData = JsonUtil.fromJson[TownData](townString)
      game.townList(townData.id).pop = townData.pop
      game.townList(townData.id).isHub = townData.isHub
      game.townList(townData.id).stocks = townData.stocks
    }
    for (trainFile <- (new File(path + "/trains")).listFiles) {
      val trainString = Reader.read(trainFile)
      val trainData = JsonUtil.fromJson[TrainData](trainString)
      val route = trainData.route.map(game.townList(_))
      val newTrain = new Train(trainData.name,trainData.engine,game)
      game.trainList = newTrain +: game.trainList
      newTrain.longHaul = trainData.longHaul
      newTrain.desiredLoad = trainData.desiredLoad
      if (route.length == 0){
          newTrain.setDestination(game.townList(0))
      }
      else {
          newTrain.setDestination(route(0))
          newTrain.setRoute(route)
          game.trainsOnTransit = (newTrain,newTrain.getDestination()) +: game.trainsOnTransit
      }
      stage.addToBeDrawn(newTrain)
      stage.infoWidget.trainPane.addTrainToComboBox(newTrain)
      stage.infoWidget.trainPane.selectedTrain = Some(newTrain)
      stage.infoWidget.trainPane.desiredLoad.min = trainData.engine.maxLoad*0.05
      stage.infoWidget.trainPane.desiredLoad.max = trainData.engine.maxLoad*0.9
      stage.infoWidget.trainPane.desiredLoad.value = trainData.desiredLoad
    }
    for (planeFile <- (new File(path + "/planes")).listFiles) {
      val planeString = Reader.read(planeFile)
      val planeData = JsonUtil.fromJson[PlaneData](planeString)
      val planeRoute = planeData.route.map(game.townList(_))
      val newPlane = new Plane(planeData.name,planeData.engine,game)
      newPlane.desiredLoad = planeData.desiredLoad
      newPlane.longHaul = planeData.longHaul
      val route = planeData.route.map(game.townList(_))
      game.planeList = newPlane +: game.planeList
      stage.addToBeDrawn(newPlane)
      stage.infoWidget.planePane.addPlaneToComboBox(newPlane)
      stage.infoWidget.planePane.selectedPlane = Some(newPlane)
      stage.infoWidget.planePane.desiredLoad.min = planeData.engine.maxLoad*0.05
      stage.infoWidget.planePane.desiredLoad.max = planeData.engine.maxLoad*0.9
      stage.infoWidget.planePane.desiredLoad.value = planeData.desiredLoad

      if (planeRoute.length == 0){
        val town = game.townList.filter(_.hasAirport)(0)
        newPlane.setDestination(town)
        newPlane.nextDest = town.getID
        town.welcomePlane(newPlane)
      }
      else {
        val town = planeRoute(planeRoute.length -1)
        newPlane.setDestination(town)
        newPlane.nextDest = town.getID
        town.welcomePlane(newPlane)
        newPlane.setRoute(planeRoute)
        val briefing = game.airports.getBriefing(newPlane.getCurrentTown,planeRoute(0),newPlane.engine.maxRange)
        if (briefing.length > 1 && (briefing(0).pos-briefing(1).pos).norm()*newPlane.engine.priceByKm <= game.money) {
          newPlane.startFly(newPlane.getCurrentTown,planeRoute(0))
        }
      }
    }
    stage
  }
}
