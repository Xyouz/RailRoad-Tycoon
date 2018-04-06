package stuff

import cargo._
import factory._

class Stuff(val name : String, var quantity : Double, val maxPrice : Double){
  def getName() : String = {name}
  def getQuantity() : Double = {quantity}

  def equalsTest(that : Stuff)  = {
    this.name == that.name
  }

   def addQuantity(that : Stuff) = {
    if (equals(that)) {
      this.quantity = this.quantity + that.quantity
    }
  }

  def subStuff(that : Stuff) = {
    if (this.quantity >= that.quantity) {
      this.quantity = this.quantity - that.quantity
    }
    else {
      throw new Exception
    }
  }

  def consumeStuff(d : Int) = {
    this.quantity = this.quantity/d
  }

}
