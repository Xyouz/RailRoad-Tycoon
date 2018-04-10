package factoryBuilder

import stuff._
import stuffData._
import factory._
import town._
import scala.util.Random


case class NotFoundTypeFactoryException() extends Exception()


// Use to build different type of factories while parsing xml maps
// Strange spelling to avoid a warning related to case-sensitive class distinction
class FactoryBuiilder() {
  val random = Random

  def or(s1 : Stuff, s2 : Stuff) = {
    if (random.nextBoolean()){
      s1
    }
    else {
      s2
    }
  }

  def orf(f1 : Factory, f2 : Factory) = {
    if (random.nextBoolean()){
      f1
    }
    else {
      f2
    }
  }

  def build(typeOf : String, town : Town) : Factory = {
    typeOf match {
      case "aluminum_plant" => {new Factory(List[Stuff](new Bauxite(100),new Coal(25)),new Aluminum(100),200,town)}
      case "bakery" => {new Factory(List[Stuff](new Grain(100)),new BakedGoods(100),200,town)}
      case "bauxite_mine" => {new Factory(List[Stuff](),new Bauxite(100),200,town)}
      case "brewery" => {new Factory(List[Stuff](new Grain(100),new Glass(10)),new Beer(100),200,town)}
      case "brickworks" => {new Factory(List[Stuff](new Clay(100)),new Bricks(100),200,town)}
      case "cannery" => {new Factory(List[Stuff](new Steel(10),or(or(new Fish(100),new Fruit(100)),or(new Meat(100),new Vegetables(100)))),new CannedFood(100),200,town)}
      case "cattle_ranch" => {new Factory(List[Stuff](new Grain(100)),or(new Cattle(100),new Milk(100)),200,town)}
      case "cement_factory" => {new Factory(List[Stuff](new Coal(100),new Limestone(100)),new Cement(100),200,town)}
      case "chemical_plant" => {new Factory(List[Stuff](new PetroleumProducts(100)),or(new Chemicals(100), new Plastics(100)),200,town)}
      case "clay_pit" => {new Factory(List[Stuff](),new Clay(100),200,town)}
      case "coal_mine" => {new Factory(List[Stuff](),new Coal(100),200,town)}
      case "copper_mine" => {new Factory(List[Stuff](),new Copper(100),200,town)}
      case "cotton_plantation" => {new Factory(List[Stuff](),new Cotton(100),200,town)}
      case "electronics_factory" => {new Factory(List[Stuff](new Glass(100),new Plastics(100),or(new AluminumWires(100),new CopperWires(100))),new Electronics(100),200,town)}
      case "fishery" => {new Factory(List[Stuff](),new Fish(100),200,town)}
      case "forestry" => {new Factory(List[Stuff](),new Timber(100),200,town)}
      case "fruit_orchard" => {new Factory(List[Stuff](),new Fruit(100),200,town)}
      case "furniture_factory" => {new Factory(List[Stuff](or(new Lumber(100),new Steel(100)),or(or(new Glass(100),new Leather(100)),new Textiles(100))),new Furniture(100),200,town)}
      case "glass_factory" => {new Factory(List[Stuff](new Coal(50),new Sand(100)),new Glass(100),200,town)}
      case "grain_farm" => {new Factory(List[Stuff](),new Grain(100),200,town)}
      case "iron_mine" => {new Factory(List[Stuff](),new Iron(100),200,town)}
      case "liquor_distillery" => {new Factory(List[Stuff](new Glass(20),or(new Fruit(100),new Grain(100))),new Liquor(100),200,town)}
      case "lumber_mill" => {new Factory(List[Stuff](new Timber(100)),or(new Lumber(100),new Woodchips(400)),200,town)}
      case "oil_refinery" => {new Factory(List[Stuff](new Oil(100)),or(new Fuel(100),new PetroleumProducts(100)),200,town)}
      case "oil_wells" => {new Factory(List[Stuff](),new Oil(100),200,town)}
      case "paper_mill" => {new Factory(List[Stuff](new Woodchips(100)),new Paper(100),200,town)}
      case "pig_farm" => {new Factory(List[Stuff](or(new Grain(100),new Vegetables(100))),new Pigs(100),200,town)}
      case "printing_press" => {new Factory(List[Stuff](new Paper(100)),new Press(100),200,town)}
      case "quarry" => {new Factory(List[Stuff](),or(new Marble(100),new Limestone(100)),200,town)}
      case "stone_quarry" => {new Factory(List[Stuff](),or(new Marble(100),new Limestone(100)),200,town)}
      case "rubber_plantation" => {new Factory(List[Stuff](),new Rubber(100),200,town)}
      case "sand_pit" => {new Factory(List[Stuff](),new Sand(100),200,town)}
      case "sheep_farm" => {new Factory(List[Stuff](new Grain(100)),or(new Wool(100),new Sheep(100)),200,town)}
      case "steel_mill" => {new Factory(List[Stuff](new Coal(100),new Iron(100)),new Steel(100),200,town)}
      case "stockyard" => {new Factory(List[Stuff](or(new Cattle(100),or(new Pigs(100),new Sheep(100)))),new Meat(100),200,town)}
      case "tannery" => {new Factory(List[Stuff](new Cattle(100)),new Leather(100),200,town)}
      case "textile_mill" => {new Factory(List[Stuff](or(new Cotton(100),new Wool(100))),new Textiles(100),200,town)}
      case "tyre_factory" => {new Factory(List[Stuff](new Chemicals(100),new Rubber(100),new SteelWires(100)),new Tyres(100),200,town)}
      case "vegetable_farm" => {new Factory(List[Stuff](),new Vegetables(100),200,town)}
      case "vehicle_factory" => {new Factory(List[Stuff](new Aluminum(100),new Electronics(100),new Glass(100),new Plastics(100),new Steel(100),new Tyres(100)),new Vehicles(100),200,town)}
      case "vineyard" => {new Factory(List[Stuff](new Glass(100)),new Wine(100),200,town)}
      case "wire_mill" => {orf(new Factory(List[Stuff](new Aluminum(100)),new AluminumWires(100),200,town),
                           orf(new Factory(List[Stuff](new Copper(100)),new CopperWires(100),200,town),
                              new Factory(List[Stuff](new Steel(100)),new SteelWires(100),200,town)))}
      case "wire_factory" => {orf(new Factory(List[Stuff](new Aluminum(100)),new AluminumWires(100),200,town),
                           orf(new Factory(List[Stuff](new Copper(100)),new CopperWires(100),200,town),
                              new Factory(List[Stuff](new Steel(100)),new SteelWires(100),200,town)))}
      case _ => throw NotFoundTypeFactoryException()
    }
  }
}
