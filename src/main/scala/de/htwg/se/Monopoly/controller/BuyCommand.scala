package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.model.{Board, Player, Street}
import de.htwg.se.Monopoly.util.Command

class BuyCommand(controller: Controller) extends Command{

  var board: Board = controller.board
  var players: Vector[Player] = controller.players
  var context: Context = controller.context
  var currentPlayerIndex: Int = controller.currentPlayerIndex

  override def doStep: Unit = {
    controller.actualField match {
      case s: Street =>
        controller.players = players.updated(controller.currentPlayerIndex, players(controller.currentPlayerIndex).decrementMoney(s.price))
        val street = Street(s.index, s.name, s.neighbourhoodTypes, s.price, s.rent, Some(players(controller.currentPlayerIndex)))
        controller.board = Board(board.fields.updated(s.index, street))
    }
    controller.nextPlayer()
  }

  override def undoStep: Unit = {
    val newBoard: Board = controller.board
    val newPlayers: Vector[Player] = controller.players
    val newContext: Context = controller.context
    val newCurrentPlayerIndex: Int = controller.currentPlayerIndex
    controller.board = board
    controller.players = players
    controller.context  = context
    controller.currentPlayerIndex = currentPlayerIndex
    board = newBoard
    players = newPlayers
    context  = newContext
    currentPlayerIndex = newCurrentPlayerIndex
  }

  override def redoStep: Unit = {
    val newBoard: Board = controller.board
    val newPlayers: Vector[Player] = controller.players
    val newContext: Context = controller.context
    val newCurrentPlayerIndex: Int = controller.currentPlayerIndex
    controller.board = board
    controller.players = players
    controller.context  = context
    controller.currentPlayerIndex = currentPlayerIndex
    board = newBoard
    players = newPlayers
    context  = newContext
    currentPlayerIndex = newCurrentPlayerIndex
  }
}