package updatable

import scalafx.scene.control.{Button, Label, ComboBox, Accordion}

// trait used in order to add a method update to somme scalafx objects so that
// the type system is happy
trait Updatable {
  def update() = {}
}

class UpdatableButton extends Button with Updatable {}

class UpdatableLabel extends Label with Updatable {}

class UpdatableComboBox[T](items : Seq[T]) extends ComboBox(items) with Updatable {}

class UpdatableAccordion extends Accordion with Updatable {}
