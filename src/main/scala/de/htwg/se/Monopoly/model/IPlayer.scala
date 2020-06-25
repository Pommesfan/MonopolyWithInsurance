package de.htwg.se.Monopoly.model

import java.awt.Color

trait IPlayer{
  val currentPosition: Int
  val index: Int
  val inJail: Int
  val money: Int
  val figure: String
  val color: Color

  def setPosition(newPosition: Int): Player
}
