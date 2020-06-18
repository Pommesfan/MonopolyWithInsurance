package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.model.{Board, Dice, Player}
import de.htwg.se.Monopoly.util.Command

class RollDiceCommand(controller: Controller) extends Command{
  var board: Board = controller.board
  var players: Vector[Player] = controller.players
  var context: Context = controller.context

  override def doStep: Unit = {
    val firstRolledNumber = Dice().roll
    val secondRolledNumber = Dice().roll
    controller.movePlayer(firstRolledNumber + secondRolledNumber)
  }

  override def undoStep: Unit = {
    val newBoard: Board = controller.board
    val newPlayers: Vector[Player] = controller.players
    val newContext: Context = controller.context
    controller.board = board
    controller.players = players
    controller.context = context
    board = newBoard
    players = newPlayers
    context = newContext
  }

  override def redoStep: Unit = {
    val newBoard: Board = controller.board
    val newPlayers: Vector[Player] = controller.players
    val newContext: Context = controller.context
    controller.board = board
    controller.players = players
    controller.context  = context
    board = newBoard
    players = newPlayers
    context  = newContext
  }
}