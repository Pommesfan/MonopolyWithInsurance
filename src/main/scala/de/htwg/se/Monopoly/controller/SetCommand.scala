package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.util.Command

class SetCommand(controller: Controller) extends Command {
  override def doStep: Unit = controller.grid = controller.grid.newMove

  override def undoStep: Unit = controller.grid = controller.grid.newMove

  override def redoStep: Unit = controller.grid = controller.grid.newMove

}
