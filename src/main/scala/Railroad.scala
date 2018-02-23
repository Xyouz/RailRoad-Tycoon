import gui._
import model._

object Program
{
  def main(args: Array[String])= {
    val bordeaux = new Town("Bordeaux", 132, List(("bananes", 12), ("mures", 32)), new Point(4,5) )
    val agen = new Town("Agen", 132, List(("bananes", 12), ("mures", 32)), new Point(0,5) )
    val t = new Train(bordeaux, agen,1)
    val r = new Road(bordeaux, agen,Array(bordeaux.position(),agen.position()))
    val p = r.position(2.5)
    p._1.print()
	 }
}
