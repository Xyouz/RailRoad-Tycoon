package updatable

import scalafx.scene.control.{Button, Label}

trait Updatable {
  def update{}
}

class UpdatableButton extends Button with Updatable {}

class UpdatableLabel extends Label with Updatable {}
