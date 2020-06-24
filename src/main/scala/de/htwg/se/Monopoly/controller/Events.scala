package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.aview.Gui.AddPlayerDialog

import scala.swing.event.Event

class NewGameEvent extends Event
class PlayerSet extends Event
case class DialogClosed(dialog: AddPlayerDialog, cancel:Boolean) extends Event
class DiceRolled extends Event
class HandleStreet extends Event
class OwnStreet extends Event
case class MoneyTransaction(money: Int) extends Event
class DecrementJailCounter extends Event
class NextPlayer extends Event
class WaitForNextPlayer extends Event
class LandedOnField extends Event
