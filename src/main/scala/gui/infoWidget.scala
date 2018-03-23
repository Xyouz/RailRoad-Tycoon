package infoWidget

import infoPane._
import cityPane._
import scalafx.Includes._
import scalafx.scene.control._
import updatable._
import model._
import setRouteDialog._
import train._
import gui._
import scalafx.application.{JFXApp, Platform}



class InfoWidget(val master : MainGame, val game : Game) extends Accordion() with Updatable{
    maxWidth = 200
    val p1 = new InfoPane()

    val selectTrain = new ComboBox(game.trainList){
      selectionModel().selectedItem.onChange {
        (_, _, newValue) => {
          val dialog = new setRouteDialog(master,game,newValue)

          val res = dialog.showAndWait()
          res match {
            case _ => ()
          }
        }
      }
    }

    def addTrainToInfoWidget(train : Train) = {
      selectTrain += train
    }

    panes = List(
      p1,
      new CityPane(game.townList),
      new TitledPane {
        text = "Trains"
        content = selectTrain
      }
    )
    override def update() = {
      p1.update(game.money)
    }
}
