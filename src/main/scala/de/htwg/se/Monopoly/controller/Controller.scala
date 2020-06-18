package de.htwg.se.Monopoly.controller

import de.htwg.se.monopoly.controller.GameStatus._
import de.htwg.se.Monopoly.model.{Grid, Player}
import de.htwg.se.Monopoly.util.{Observable, UndoManager}

class Controller(var grid: Grid) extends Observable{

  var gameStatus: GameStatus = IDLE
  private val undoManager = new UndoManager

  def setPlayers(player: List[Player]): Unit = {
    grid = grid.setPlayers(player)
    notifyObservers
  }

  def newMove(): Unit = {
    grid = grid.newMove
    notifyObservers
  }

  def gridToString: String = grid.toString

  def solve: Unit = {
    undoManager.doStep(new SolveCommand(this))
    notifyObservers
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
  }

}
