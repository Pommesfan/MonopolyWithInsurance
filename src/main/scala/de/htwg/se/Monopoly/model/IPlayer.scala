package de.htwg.se.Monopoly.model

trait IPlayer{
  val currentPosition: Int
  val index: Int
  val inJail: Boolean
  val money: Int

  def setPosition(newPosition: Int): Player
}
