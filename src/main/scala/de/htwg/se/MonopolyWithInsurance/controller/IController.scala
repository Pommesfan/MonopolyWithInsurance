package de.htwg.se.MonopolyWithInsurance.controller

import de.htwg.se.MonopolyWithInsurance.aview.Gui.AddPlayerDialog
import de.htwg.se.MonopolyWithInsurance.controller.controllerComponent.controllerBaseImpl.Context
import de.htwg.se.MonopolyWithInsurance.model.boardComponent.IBoard
import de.htwg.se.MonopolyWithInsurance.model.fieldComponent.IField
import de.htwg.se.MonopolyWithInsurance.model.playerComponent.IPlayer

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
  def exit(): Unit
  def gameToString(): String
  def save: Unit
  def load: Unit
  def setInsurance(idx:Int): Unit
  def resignInsurance: Unit
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
case class NextPlayer(insurance: Int) extends Event
class WaitForNextPlayer extends Event
class LandedOnField extends Event
class GoToJailEvent extends Event
class PayToLeave extends Event
case class SignInsurance(amount: Int) extends Event
case class UnsignInsurance() extends Event
case class InsurancePays(amount: Int) extends Event
case class HandleChanceCard(message: String) extends Event
case class NotEnoughMoney() extends Event
case class GameOver() extends Event
class ExitGame() extends Event
class RedoEvent() extends Event
class UndoEvent() extends Event
class LoadEvent() extends Event
class SaveEvent() extends Event
