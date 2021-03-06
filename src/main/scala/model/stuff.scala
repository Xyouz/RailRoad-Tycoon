package stuff

import cargo._
import factory._
import saveUtils._

case class UnmatchedStuffException() extends Exception()
case class NotEnoughQuantityException() extends Exception()

/** This class describes in which category is the goods and how to deal with their respective stocks.
 * There are methods to know if some goods is the same than an other one,
 * to consume them according to the time,
 * to add or to substract some quantity of goods.
 * This class is quite useful to load or unload the goods in or out of the vehicles
*/

case class StuffData( name : String, quantity : Double, maxPrice : Double, category : String)

class Stuff(val name : String, var quantity : Double, val maxPrice : Double, val category : String){

  def toData = {
    new StuffData(name, quantity, maxPrice, category)
  }

  def this(cc : StuffData) = {
    this(cc.name , cc.quantity , cc.maxPrice , cc.category)
  }

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

  def findInList(list : Seq[Stuff]) = {
    (list.filter(_ == this))(0)
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
