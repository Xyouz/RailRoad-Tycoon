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
	   println(y)
	   }
}