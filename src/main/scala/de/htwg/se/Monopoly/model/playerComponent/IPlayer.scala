package de.htwg.se.Monopoly.model.playerComponent

import java.awt.Color

import de.htwg.se.Monopoly.model.playerComponent.playerBaseImpl.Player

trait IPlayer{
  val name: String
  val currentPosition: Int
  val index: Int
  val inJail: Int
  val money: Int
  val figure: String
  val color: Color
  val pasch: Int

  def setPosition(newPosition: Int): Player
  def incrementMoney(amount: Int): Player
  def decrementMoney(amount: Int): Player
  def goToJail(): Player
  def decrementJailCounter(): Player
  def setJailCounterZero(): Player
  def setPasch(i: Int): Player

  //override def toString: String = super.toString
}
