package cityPane

import town._
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.PieChart

class CityPane(val townsList : Seq[Town]) extends TitledPane() {
  text = "Villes"
  maxWidth = 250
  var selectedTown = townsList(0)
  val nameLabel = new Label()
  val populationLabel = new Label()
  val factories = new Label()

  // Commented because it was too slow with this implementation
  // val stocks = new ListView[String]()

  def update() = {
    nameLabel.text = s"  ${selectedTown.toString()}  "
    populationLabel.text = s"Population : ${selectedTown.population()}"
    factories.text = s"Nombre d'usines : ${selectedTown.factories.length}"
    //stocks.data = selectedTown.stocks.map(s => PieChart.Data(s.name,s.quantity))
  }
  update()

  val select = new ComboBox(townsList)
  {
    onAction = {ae =>selectedTown = value.value
      update()
    }
  }

  val stocks = new PieChart() {
    data = selectedTown.stocks.map(s => PieChart.Data(s.name,s.quantity))
  }

  val grid = new GridPane(){
    vgap = 10

    add(select, 0, 0)
    add(nameLabel, 0, 1)
    add(populationLabel, 0, 2)
    add(factories,0,3)
  add(stocks,0,4)
  }

  content = grid

}
