class Goods (val namegoods : String, val pricetag : Float ){}  


class Town(val name: String,val pop : Int, val listofgoods :List[(String, Int)]) {}


class Train(val leaving: Town, val destination: Town, val speed : Float){
    var passengers = leaving.pop
    var goodies = leaving.listofgoods
    var loaded: Int = 0
    def loading(): Unit = {loaded = (0.1*passengers).toInt}
}




object Pmpm {
def main()= {
       	   val y = new Town("Bordeaux", 132, List(("bananes", 12), ("mures", 32)) )
	   val z = new Town("Agen", 78, List(("pruneaux", 85), ("poulet", 15)))
	   println(y)
	   println(z)
	   val t1 = new Train(y, z, 9.2)
	   println(t1.passengers)
	   }
}