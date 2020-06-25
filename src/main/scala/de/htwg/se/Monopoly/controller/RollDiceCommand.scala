package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.model.{Board, Dice, Field, Player}
import de.htwg.se.Monopoly.util.Command

class RollDiceCommand(controller: Controller) extends Command{
  var board: Board = controller.board
  var players: Vector[Player] = controller.players
  var context: Context = controller.context
  var currentPlayerIndex: Int = controller.currentPlayerIndex
  var playerIndex: Int = controller.currentPlayerIndex
  var rolledNumber: (Int, Int) = controller.rolledNumber
  private var actualField: Field = controller.actualField
  var history = controller.history

  override def doStep: Unit = {
    val firstRolledNumber = Dice().roll
    val secondRolledNumber = Dice().roll
    controller.rolledNumber = (firstRolledNumber, secondRolledNumber)
    controller.publish(new DiceRolled)
    if(firstRolledNumber == secondRolledNumber) {
      controller.players = controller.players.updated(controller.currentPlayerIndex, controller.getActualPlayer.setPasch(controller.getActualPlayer.pasch + 1))
    } else {
      controller.players = controller.players.updated(controller.currentPlayerIndex, controller.getActualPlayer.setPasch(0))
    }
    if (controller.getActualPlayer.pasch == 3) {
      context.goToJail(controller)
      controller.publish(new GoToJailEvent)
    } else {
      controller.movePlayer(firstRolledNumber + secondRolledNumber)
    }
  }

  override def undoStep: Unit = {
    val newBoard: Board = controller.board
    val newPlayers: Vector[Player] = controller.players
    val newContext: Context = controller.context
    val newPlayerIndex: Int = controller.currentPlayerIndex
    val newRolledNumber: (Int, Int) = controller.rolledNumber
    val newActualField: Field = controller.actualField
    val newHistory: Vector[String] = controller.history
    controller.board = board
    controller.players = players
    controller.context = context
    controller.currentPlayerIndex = playerIndex
    controller.rolledNumber = rolledNumber
    controller.actualField = actualField
    controller.history = history
    board = newBoard
    players = newPlayers
    context = newContext
    playerIndex = newPlayerIndex
    rolledNumber = newRolledNumber
    actualField = newActualField
    history = newHistory
  }

  override def redoStep: Unit = {
    val newBoard: Board = controller.board
    val newPlayers: Vector[Player] = controller.players
    val newContext: Context = controller.context
    val newPlayerIndex: Int = controller.currentPlayerIndex
    val newRolledNumber: (Int, Int) = controller.rolledNumber
    val newActualField: Field = controller.actualField
    val newHistory: Vector[String] = controller.history
    controller.board = board
    controller.players = players
    controller.context  = context
    controller.rolledNumber = rolledNumber
    controller.actualField = actualField
    controller.history = history
    board = newBoard
    players = newPlayers
    context  = newContext
    playerIndex = newPlayerIndex
    rolledNumber = newRolledNumber
    actualField = newActualField
    history = newHistory
  }
}