package de.htwg.se.Monopoly.model

trait IPlayer{
  val currentPosition: Int
  val index: Int
  val inJail: Int
  val money: Int
  val figure: String

  def setPosition(newPosition: Int): Player
}
