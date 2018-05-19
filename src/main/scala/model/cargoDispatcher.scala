package cargoDispatcher

import town.Town
import cargo.Cargo
import stuff.Stuff
import model.Game
import math.{max,min}

case class CargoDispatcherError() extends Exception()

class CargoDispatcher(val game: Game) {

  def fillCargo(cargo : Cargo, town : Town) = {
    val outputs = town.factories.map(_.output)
    if (!cargo.isEmpty()){
      throw new CargoDispatcherError()
    }
    var maxi = Double.NegativeInfinity
    var argMax = outputs(0)
    var townMax = game.townList(0)
    for (stuff <- outputs){
      for (city <- game.townList){
        if ((maxi < (city.priceOfStuff(stuff)-town.priceOfStuff(stuff)))
            && city != town){
          maxi = city.priceOfStuff(stuff)-town.priceOfStuff(stuff)
          townMax = city
          argMax = stuff
        }
      }
    }
    cargo.destination = Some(townMax)
    val content = argMax.copy()
    content.quantity = min(argMax.quantity, cargo.maxLoad)
    town.takeStuff(content)
    cargo.content = Some(content)
  }
}
