package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.aview.Gui.AddPlayerDialog

import scala.swing.event.Event

class NewGameEvent extends Event
class TestEvent extends Event
class PlayerSet extends Event
case class DialogClosed(dialog: AddPlayerDialog, cancel:Boolean) extends Event
