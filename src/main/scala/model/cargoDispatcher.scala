package cargoDispatcher

import town.Town
import cargo.Cargo
import stuff._
import model.Game
import math.{max,min}
import scala.util._

case class CargoDispatcherError() extends Exception()

class CargoDispatcher(val townList : Seq[Town], val town : Town) {
  val outputs = town.factories.map(_.output)
  val rand = new Random()

  def rBool () = {
    rand.nextBoolean()
  }
  def rDouble () = {
    rand.nextDouble()
  }

  def fillCargo(cargo : Cargo) = {
    if (!cargo.isEmpty()){
      throw new CargoDispatcherError()
    }
    var maxi = Double.NegativeInfinity
    var argMax = town.stocks(0)
    var townMax = townList(0)
    for (stuff <- outputs){
      if (stuff.category == cargo.typeOfLoad){
        for (city <- townList){
          if (((0.9 + 0.1 * rDouble())*maxi < (city.priceOfStuff(stuff)-town.priceOfStuff(stuff)))
              && city != town){
            maxi = city.priceOfStuff(stuff)-town.priceOfStuff(stuff)
            townMax = city
            argMax = stuff
          }
        }
      }
    }
    if (maxi > Double.NegativeInfinity) {
      cargo.destination = Some(townMax)
      val townQuantity = argMax.findInList(town.stocks).quantity
      val content = argMax.copy()
      content.quantity = min(townQuantity, cargo.maxLoad)
      try {
        town.takeStuff(content)
        cargo.content = Some(content)
      }
      catch {
        case NotEnoughQuantityException() => {
          cargo.content = None
          cargo.destination = None
        }
      }
    }
  }
}
