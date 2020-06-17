package de.htwg.se.Monopoly.controller

import de.htwg.se.monopoly.util.Command

class SetCommand(controller: Controller) extends Command {
  override def doStep: Unit = controller.grid = controller.grid.roll

  override def undoStep: Unit = controller.grid = controller.grid.roll

  override def redoStep: Unit = controller.grid = controller.grid.roll

}
