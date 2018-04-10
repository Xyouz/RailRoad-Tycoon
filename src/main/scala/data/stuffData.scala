package stuffData

import stuff._

class Aluminum(quantity : Double) extends Stuff("Aluminum",quantity,1000, "Dry"){}
class AluminumWires(quantity : Double) extends Stuff("Aluminum Wires",quantity,1500, "Dry"){}
class BakedGoods(quantity : Double) extends Stuff("Baked Goods",quantity,1000,"Container"){}
class Bauxite(quantity : Double) extends Stuff("Bauxite",quantity,1000, "Dry"){}
class Beer(quantity : Double) extends Stuff("Beer",quantity,1000, "Liquid"){}
class Bricks(quantity : Double) extends Stuff("Bricks",quantity,1000, "Dry"){}
class Cattle(quantity : Double) extends Stuff("Cattle",quantity,1000, "Container"){}
class CannedFood(quantity : Double) extends Stuff("Canned Food",quantity,1000, "Container"){}
class Cement(quantity : Double) extends Stuff("Cement",quantity,1000, "Dry"){}
class Chemicals(quantity : Double) extends Stuff("Chemicals",quantity,1000,"Liquid"){}
class Clay(quantity : Double) extends Stuff("Clay",quantity,1000, "Dry"){}
class Coal(quantity : Double) extends Stuff("Coal",quantity,1000, "Dry"){}
class Copper(quantity : Double) extends Stuff("Copper",quantity,1000, "Dry"){}
class CopperWires(quantity : Double) extends Stuff("CopperWires",quantity,1000, "Dry"){}
class Cotton(quantity : Double) extends Stuff("Cotton",quantity,1000, "Dry"){}
class Electronics(quantity : Double) extends Stuff("Electronics",quantity,1000, "Dry"){}
class Fish(quantity : Double) extends Stuff("Fish",quantity,1000, "Container"){}
class Fruit(quantity : Double) extends Stuff("Fruit",quantity,1000, "Container"){}
class Fuel(quantity : Double) extends Stuff("Fuel",quantity,1000, "Liquid"){}
class Furniture(quantity : Double) extends Stuff("Furniture",quantity,1000, "Dry"){}
class Glass(quantity : Double) extends Stuff("Glass",quantity,1000, "Dry"){}
class Grain(quantity : Double) extends Stuff("Grain",quantity,1000, "Dry"){}
class Iron(quantity : Double) extends Stuff("Iron",quantity,1000, "Dry"){}
class Leather(quantity : Double) extends Stuff("Leather",quantity,1000, "Dry"){}
class Limestone(quantity : Double) extends Stuff("Limestone",quantity,1000, "Dry"){}
class Liquor(quantity : Double) extends Stuff("Liquor",quantity,1000, "Liquid"){}
class Lumber(quantity : Double) extends Stuff("Lumber",quantity,1000, "Dry"){}
class Marble(quantity : Double) extends Stuff("Marble",quantity,1000, "Dry"){}
class Meat(quantity : Double) extends Stuff("Meat",quantity,1000, "Container"){}
class Milk(quantity : Double) extends Stuff("Milk",quantity,1000, "Liquid"){}
class Oil(quantity : Double) extends Stuff("Oil",quantity,1000, "Liquid"){}
class Paper(quantity : Double) extends Stuff("Paper",quantity,1000, "Dry"){}
class PetroleumProducts(quantity : Double) extends Stuff("Petroleum Products",quantity,1000, "Liquid"){}
class Pigs(quantity : Double) extends Stuff("Pigs",quantity,1000, "Container"){}
class Plastics(quantity : Double) extends Stuff("Plastics",quantity,1000, "Dry"){}
class Press(quantity : Double) extends Stuff("Press",quantity,1000, "Container"){}
class Rubber(quantity : Double) extends Stuff("Rubber",quantity,1000, "Dry"){}
class Sand(quantity : Double) extends Stuff("Sand",quantity,1000, "Dry"){}
class Sheep(quantity : Double) extends Stuff("Sheep",quantity,1000, "Container"){}
class Steel(quantity : Double) extends Stuff("Steel",quantity,1000, "Dry"){}
class SteelWires(quantity : Double) extends Stuff("Steel Wires",quantity,1000, "Dry"){}
class Textiles(quantity : Double) extends Stuff("Textiles",quantity,1000, "Container"){}
class Timber(quantity : Double) extends Stuff("Timber",quantity,1000, "Dry"){}
class Tyres(quantity : Double) extends Stuff("Tyres",quantity,1000, "Dry"){}
class Vegetables(quantity : Double) extends Stuff("Vegetables",quantity,1000, "Container"){}
class Vehicles(quantity : Double) extends Stuff("Vehicles",quantity,1000, "Individual"){}
class Wine(quantity : Double) extends Stuff("Wine",quantity,1000, "Liquid"){}
class Woodchips(quantity : Double) extends Stuff("Woodchips",quantity,1000, "Dry"){}
class Wool(quantity : Double) extends Stuff("Wool",quantity,1000, "Dry"){}




class StockFiller(quantity : Double) {
  def fill() = {
    List(new Aluminum(quantity) ,new AluminumWires(quantity) ,new BakedGoods(quantity) ,new Bauxite(quantity) ,new Beer(quantity) ,new Bricks(quantity) ,new Cattle(quantity) ,new CannedFood(quantity) ,new Cement(quantity) ,new Chemicals(quantity) ,new Clay(quantity) ,new Coal(quantity) ,new Copper(quantity) ,new CopperWires(quantity) ,new Cotton(quantity) ,new Electronics(quantity) ,new Fish(quantity) ,new Fruit(quantity) ,new Fuel(quantity) ,new Furniture(quantity) ,new Glass(quantity) ,new Grain(quantity) ,new Iron(quantity) ,new Leather(quantity) ,new Limestone(quantity) ,new Liquor(quantity) ,new Lumber(quantity) ,new Marble(quantity) ,new Meat(quantity) ,new Milk(quantity) ,new Oil(quantity) ,new Paper(quantity) ,new PetroleumProducts(quantity) ,new Pigs(quantity) ,new Plastics(quantity) ,new Press(quantity) ,new Rubber(quantity) ,new Sand(quantity) ,new Sheep(quantity) ,new Steel(quantity) ,new SteelWires(quantity) ,new Textiles(quantity) ,new Timber(quantity) ,new Tyres(quantity) ,new Vegetables(quantity) ,new Vehicles(quantity) ,new Wine(quantity) ,new Woodchips(quantity) ,new Wool(quantity))
  }
}
