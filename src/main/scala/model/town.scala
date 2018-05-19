package town

import point._
import train._
import plane._
import cargo._
import stuff._
import factory._
import scala.math.{max,min}
import stuffData._
import model._
import scala.util.Random
import cargoDispatcher.CargoDispatcher
import model.Game


case class NoAirportException() extends Exception()


/** This class implements the towns of the graphs with information on the name,
 the population, their wealth and methods to update them when a train or a plane come over.
 * There is also a method that handles the stocks of the goods in the town.
*/

class Town(val id : Int, val name: String, var pop : Int, var pos : Point){
  var airport = List[Plane]()
  var factories = List[Factory]()
  var stocks = List[Stuff]()
  var hasAirport = false
  val rndGen = new Random()
  var cargoDispatcher = new CargoDispatcher(Seq[Town](),this)

  def setTownList(towns : Seq[Town]) = {
    cargoDispatcher = new CargoDispatcher(towns, this)
  }

  var isHub = false
  var cargosInTown = List[Cargo]()

  val x = position.x
  val y = position.y

  override def toString() = {name}
  def addFactory(plan : Factory) = {
    factories = plan +: factories
  }
  def getID() : Int = {id}
  def getName() : String = {name}
  def position() : Point={pos}
  def population() : Int={pop}
  def deltaPopulation(delta : Int) = {pop += delta}
  def incrPop() = {pop = pop+50}

  def isInput(stuff : Stuff) : Boolean = {
    for (factory <- factories){
      for (input <- factory.input){
        if(stuff == input){
          return true
        }
      }
    }
    false
  }

  def isOutput(stuff : Stuff) : Boolean = {
    for (factory <- factories){
      if(stuff == factory.output){
        return true
      }
    }
    false
  }

  def priceOfStuff(stuff : Stuff):Double = {
    var i = stocks.find(_==stuff)
    i match {
      case Some(s) => if (isInput(stuff)){
        max(0,s.maxPrice - 0.05*(s.quantity))*2
      }
      else {
        max(0,s.maxPrice - 0.05*(s.quantity))
      }
      case None => throw new Exception
    }
  }

  val consuptionStuff = List(new Aluminum(1), new BakedGoods(1), new Beer(1),
                             new Bricks(1), new CannedFood(1), new Cement(1),
                             new Chemicals(1), new Electronics(1), new Fish(1),
                             new Fruit(1), new Fuel(1), new Furniture(1),
                             new Glass(1), new Grain(1), new Leather(1),
                             new Liquor(1), new Liquor(1), new Lumber(1),
                             new Marble(1), new Meat(1), new Milk(1),
                             new Paper(1), new Plastics(1), new Press(1),
                             new Rubber(1), new Steel(1), new Textiles(1),
                             new Tyres(1), new Vegetables(1), new Vehicles(1),
                             new Wine(1) )
  def cityConsumption() = {
    var i = 0
    var j = 0
    while ((i < consuptionStuff.length) && (j < stocks.length)){
      if (consuptionStuff(i) == stocks(j)) {
        try {
          stocks(j).subStuff(consuptionStuff(i).scale(0.001* population))
        }
        catch {
          case NotEnoughQuantityException() => ()//println("pas assez de bouffe")
        }
        i = i + 1
        j = j + 1
      }
      else {
        j = j + 1
      }
    }
  }

  var t = 0

  def prepareCargo(cargo : Cargo) = {
    if (cargo.isEmpty) {
      cargoDispatcher.fillCargo(cargo)
    }
  }

  def unloadCargo(cargo : Cargo) = {
    if ((!cargo.isEmpty)&&(cargo.destination==Some(this))) {
      cargo.destination = None
      receiveStuff(cargo.unload)
    }
  }

  def update(){
    t = t + 1
    if (t==200) {
      t = 0
      cityConsumption()
    }
    cargosInTown.map(unloadCargo(_))
    factories.map(_.update())
    cargosInTown.map(prepareCargo(_))
  }

  def getAirport = {
    if (hasAirport) {
      airport
    }
    else {
      throw new NoAirportException()
    }
  }

  var lpop = (pop/10).toInt

  def welcomePlane(plane : Plane) = {
    airport = plane +: airport
    plane.setCurrentTown(Some(this))
  }

  var excedent = 1000.0

  def loadTrain(train : Train) = {
    if (pop - lpop > 0){
      pop -= lpop
      train.loading += lpop
    }
    for (cargo <- cargosInTown.filter(! _.isEmpty())){
      cargo.destination match {
        case None => {
          try {
            receiveStuff(cargo.unload)
          }
          catch {
            case EmptyCargo() => ()
          }
        }
        case Some(city) => {
          // if (train.load + cargo.weight <= train.desired load)
          if (id==0){
            println("prendre en compte le paramètre desiredLoa (cf town.loadTrain)")
          }
          if (train.route.exists(_ == city)){
            train.listOfWagon = cargo +: train.listOfWagon
            cargosInTown = cargosInTown.filterNot(_ == cargo)
          }
        }
      }
    }
  }

  def receiveCargo(cargo : Cargo) = {
    cargosInTown = cargo +: cargosInTown
  }

  def loadCargo(cargo : Cargo) = {
    var argMax = stocks(0)
    var max = Double.NegativeInfinity
    for (i <- stocks){
      if (cargo.kindOfLoad() == i.stuffCategory() && i.quantity >= 0 && priceOfStuff(i)>=max){
        max = priceOfStuff(i)*(rndGen.nextFloat()/10 + 0.1)
        argMax = i
      }
    }
    var toSend = argMax.copy()
    toSend.quantity = min(argMax.quantity,cargo.maxLoad)*(rndGen.nextFloat()+1)/2
    argMax.subStuff(toSend)
    cargo.load(toSend)
    priceOfStuff(toSend)
  }



  def takeStuff(stuff : Stuff) = {
    (stuff.findInList(stocks)).subStuff(stuff)
  }

  def receiveStuff(unloaded : Stuff) = {
    var sum = 0.0
    for (i <- stocks) {
      if (i==unloaded){
        val res = priceOfStuff(i)*unloaded.quantity
        i.addStuff(unloaded)
        sum = res
      }
    }
    sum
  }
}
