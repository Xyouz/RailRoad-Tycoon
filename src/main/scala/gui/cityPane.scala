package cityPane

import town._
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets

class CityPane(val townsList : Seq[Town]) extends TitledPane() {
  text = "Villes"
  maxWidth = 250
  var selectedTown = townsList(0)
  val nameLabel = new Label()
  val populationLabel = new Label()
  val factories = new Label()

  def update() = {
    nameLabel.text = s"  ${selectedTown.toString()}  "
    populationLabel.text = s"Population : ${selectedTown.population()}"
    factories.text = s"Nombre d'usines : ${selectedTown.factories.length}"
  }
  update()

  val select = new ComboBox(townsList)
  {
    onAction = {ae =>selectedTown = value.value
            update()
            }
  }


  val grid = new GridPane(){
    vgap = 10

    add(select, 0, 0)
    add(nameLabel, 0, 1)
    add(populationLabel, 0, 2)
    add(factories,0,3)
  }

  content = grid

}
