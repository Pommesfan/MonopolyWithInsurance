package de.htwg.se.Monopoly.model

trait IPlayer{
  val currentPosition: Int
  val index: Int
  val inJail: Int
  val money: Int

  def setPosition(newPosition: Int): Player
}
