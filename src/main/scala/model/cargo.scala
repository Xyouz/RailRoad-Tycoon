package cargo

abstract class Cargo (val typeOfLoad : String, val maxLoad : Double) {
  def kindOfLoad() = {typeOfLoad}

}
