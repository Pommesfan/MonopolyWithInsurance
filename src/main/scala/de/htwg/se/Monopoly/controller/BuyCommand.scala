package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.controller.GameStatus.GameStatus
import de.htwg.se.Monopoly.model.{Board, Player}
import de.htwg.se.Monopoly.util.Command

class BuyCommand(controller: Controller) extends Command{

  var board: Board = controller.board
  var players: Vector[Player] = controller.players
  var gameStatus: GameStatus = controller.gameStatus

  override def doStep: Unit = {
    controller.buyStreet()
  }

  override def undoStep: Unit = {
    val newBoard: Board = controller.board
    val newPlayers: Vector[Player] = controller.players
    val newGameState: GameStatus = controller.gameStatus

    controller.board = board
    controller.players = players
    controller.gameStatus = gameStatus
    board = newBoard
    players = newPlayers
    gameStatus = newGameState
  }

  override def redoStep: Unit = {
    val newBoard: Board = controller.board
    val newPlayers: Vector[Player] = controller.players
    val newGameState: GameStatus = controller.gameStatus

    controller.board = board
    controller.players = players
    controller.gameStatus = gameStatus
    board = newBoard
    players = newPlayers
    gameStatus = newGameState
  }
}