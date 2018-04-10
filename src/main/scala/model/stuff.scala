package stuff

import cargo._
import factory._

case class UnmatchedStuffException() extends Exception()
case class NotEnoughQuantityException() extends Exception()

class Stuff(val name : String, var quantity : Double, val maxPrice : Double, val category : String){
  def stuffCategory() = {category}
  def getName() : String = {name}
  def getQuantity() : Double = {quantity}
  override def toString() = {name}
  def equalsTest(that : Stuff)  = {
    this.name == that.name
  }

  override def equals(that : Any) : Boolean = {
    that match {
      case s : Stuff => this.name == s.name
      case _ => false
    }
  }

  def addQuantity(that : Stuff) = {
    if (equalsTest(that)) {
      this.quantity = this.quantity + that.quantity
    }
  }

  def hasEnough(that : Stuff) = {
    this.quantity >= that.quantity && this==that
  }

  def copy() = {
    new Stuff(name,0,maxPrice, category)
  }

  def scale(scalar : Double) = {
    var res = this.copy()
    res.quantity = this.quantity * scalar
    res
  }

  def transferTo(that : Stuff, quant : Stuff) = {
    if ((this == that) && (that == quant)) {
      if (this.quantity >= quant.quantity) {
        this.subStuff(quant)
        that.addStuff(quant)
      }
    }
  }

  def addStuff(that : Stuff) = {
    if (this == that) {
      this.quantity = this.quantity + that.quantity
    }
    else {
      throw new UnmatchedStuffException()
    }
  }

  def subStuff(that : Stuff) = {
    if (this == that) {
      if (this.quantity >= that.quantity) {
        this.quantity = this.quantity - that.quantity
      }
      else {
        throw new NotEnoughQuantityException()
      }
    }
    else {
      throw new UnmatchedStuffException
    }
  }

  def consumeStuff(d : Int) = {
    this.quantity = this.quantity/d
  }

}
