package infoWidget

import infoPane._
import scalafx.scene.control._
import updatable._
import model._

class InfoWidget(game : Game) extends Accordion() with Updatable{
    maxWidth = 200
    maxHeight = 150
    var p1 = new InfoPane()
    panes = List(
      p1,
      new TitledPane {
        text = "Villes"
        content = new TextField {
          promptText = "Hi! Scalafx Ensemble!"
        }
      },
      new TitledPane {
        text = "Trains"
        content = new CheckBox {
          text = "CheckBox 1"
        }
      }
    )
    override def update() = {
      p1.update(game.money)
    }
}
