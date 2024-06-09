package de.htwg.se.MonopolyWithInsurance.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.MonopolyWithInsurance.controller._
import de.htwg.se.MonopolyWithInsurance.model.boardComponent.IBoard
import de.htwg.se.MonopolyWithInsurance.model.diceComponent.diceBaseImpl.Dice
import de.htwg.se.MonopolyWithInsurance.model.fieldComponent.IField
import de.htwg.se.MonopolyWithInsurance.model.playerComponent.IPlayer
import de.htwg.se.MonopolyWithInsurance.util.Command

class RollDiceCommand(controller: IController) extends Command{
  var board: IBoard = controller.board
  var players: Vector[IPlayer] = controller.players
  var context: Context = controller.context
  var currentPlayerIndex: Int = controller.currentPlayerIndex
  var playerIndex: Int = controller.currentPlayerIndex
  var rolledNumber: (Int, Int) = controller.rolledNumber
  private var actualField: IField = controller.actualField
  var history: Vector[String] = controller.history
  var undoJail: Boolean = controller.undoJail

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
      context.goToJail()
      controller.publish(new GoToJailEvent)
    } else {
      controller.movePlayer(firstRolledNumber + secondRolledNumber)
    }
  }

  override def undoStep: Unit = {
    val newBoard: IBoard = controller.board
    val newPlayers: Vector[IPlayer] = controller.players
    val newContext: Context = controller.context
    val newPlayerIndex: Int = controller.currentPlayerIndex
    val newRolledNumber: (Int, Int) = controller.rolledNumber
    val newActualField: IField = controller.actualField
    val newHistory: Vector[String] = controller.history
    val newUndoJail = controller.undoJail
    controller.board = board
    controller.players = players
    controller.context = context
    if (controller.undoJail) {
      controller.context.setState(new PayForJail)
    }
    controller.currentPlayerIndex = playerIndex
    controller.rolledNumber = rolledNumber
    controller.actualField = actualField
    controller.history = history
    controller.undoJail = undoJail
    board = newBoard
    players = newPlayers
    context = newContext
    playerIndex = newPlayerIndex
    rolledNumber = newRolledNumber
    actualField = newActualField
    history = newHistory
    undoJail = newUndoJail
  }

  override def redoStep: Unit = {
    val newBoard: IBoard = controller.board
    val newPlayers: Vector[IPlayer] = controller.players
    val newContext: Context = controller.context
    val newPlayerIndex: Int = controller.currentPlayerIndex
    val newRolledNumber: (Int, Int) = controller.rolledNumber
    val newActualField: IField = controller.actualField
    val newHistory: Vector[String] = controller.history
    val newUndoJail = controller.undoJail
    controller.board = board
    controller.players = players
    controller.context  = context
    controller.rolledNumber = rolledNumber
    controller.actualField = actualField
    controller.history = history
    controller.undoJail = undoJail
    board = newBoard
    players = newPlayers
    context  = newContext
    playerIndex = newPlayerIndex
    rolledNumber = newRolledNumber
    actualField = newActualField
    history = newHistory
    undoJail = newUndoJail
  }
}