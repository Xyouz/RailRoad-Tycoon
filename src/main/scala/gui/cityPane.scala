package cityPane

import town._
import charts._
import switchCharts._
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.PieChart

/** This class enables the player to chose a city on the interface to have information about it
 such as the population, the number of factories, the quantity of goods...
*/

class CityPane(val townsList : Seq[Town]) extends TitledPane() {
  text = "Villes"
  maxWidth = 250
  var selectedTown = townsList(0)
  val nameLabel = new Label()
  val populationLabel = new Label()
  val factories = new Label()
  val cargos = new Label()

  def update() = {
    nameLabel.text = s"  ${selectedTown.toString()}  "
    populationLabel.text = s"Population : ${selectedTown.population()}"
    factories.text = s"Nombre d'usines : ${selectedTown.factories.length}"
    cargos.text = s"Nombre de cargo : ${selectedTown.cargosInTown.length}"
    chartPeople.update()
  }

  val hub = new RadioButton("Hub?"){
    onAction = {
      ae => selectedTown.isHub = selected.value
    }
  }


  val select = new ComboBox(townsList)
  {
    onAction = {ae =>
      selectedTown = value.value
      hub.selected.value = selectedTown.isHub
      chartPeople.reeinitialize()
      update()
    }
  }

  object getPopulation extends giveValue {
    var time = 0
    val frequency = 50
    def value= {
      time = time + 1
      if (time % frequency == 0){
        Some((time, selectedTown.population()))
      }
      else {
        None
      }
    }
    def forceValue = {
      time += 1
      (time, selectedTown.population())
    }
  }

  val chartPeople = new SwitchCharts(getPopulation)
  update()

  val grid = new GridPane(){
    vgap = 10

    add(select, 0, 0)
    add(nameLabel, 0, 1)
    add(populationLabel, 0, 2)
    add(factories,0,3)
    add(hub,0,4)
    add(cargos,0,5)
    add(chartPeople, 0, 6)
  }

  content = grid

  def changeTown(town : Town) = {
    selectedTown = town
    select.setValue(town)
  }

}
