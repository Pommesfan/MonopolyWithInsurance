package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.aview.Gui.AddPlayerDialog
import de.htwg.se.Monopoly.controller.controllerComponent.controllerBaseImpl.Context
import de.htwg.se.Monopoly.model.boardComponent.IBoard
import de.htwg.se.Monopoly.model.fieldComponent.IField
import de.htwg.se.Monopoly.model.playerComponent.IPlayer

import scala.swing.Publisher

trait IController extends Publisher {
  var board: IBoard
  var players: Vector[IPlayer]
  var currentPlayerIndex: Int
  var undoJail: Boolean
  var actualField: IField
  var context: Context
  var rolledNumber: (Int, Int)
  var history: Vector[String]

  def setPlayers(list: Array[String]): Unit
  def rollDice(): Unit
  def getActualPlayer: IPlayer
  def movePlayer(rolledEyes: Int): Unit
  def payToLeaveJail(p: IPlayer): Unit
  def setPlayer(p: IPlayer, n: Int): IField
  def nextPlayer(): Unit
  def buyStreet(): Unit
  def undo(): Unit
  def redo(): Unit
  def gameToString(): String
  def save: Unit = {}
  def load: Unit = {}
}

import scala.swing.event.Event

class NewGameEvent extends Event
class PlayerSet extends Event
case class DialogClosed(dialog: AddPlayerDialog, cancel:Boolean) extends Event
class DiceRolled extends Event
class HandleStreet extends Event
class BoughtStreet extends Event
class OwnStreet extends Event
case class MoneyTransaction(money: Int) extends Event
case class DecrementJailCounter(counter: Int) extends Event
class NextPlayer extends Event
class WaitForNextPlayer extends Event
class LandedOnField extends Event
class GoToJailEvent extends Event
class PayToLeave extends Event
case class HandleChanceCard(message: String) extends Event
case class NotEnoughMoney() extends Event
case class GameOver() extends Event
class ExitGame() extends Event
class RedoEvent() extends Event
class UndoEvent() extends Event
class LoadEvent() extends Event
class SaveEvent() extends Event
