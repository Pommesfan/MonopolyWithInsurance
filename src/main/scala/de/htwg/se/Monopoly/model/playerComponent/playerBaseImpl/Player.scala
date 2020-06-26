package de.htwg.se.Monopoly.model.playerComponent.playerBaseImpl

import java.awt.Color

import de.htwg.se.Monopoly.model.Variable
import de.htwg.se.Monopoly.model.playerComponent.IPlayer

case class Player(override val name: String,
                  override val index: Int,
                  override val currentPosition: Int = 0,
                  override val inJail: Int = 0,
                  override val money: Int = Variable.INITIAL_PLAYER_MONEY,
                  override val figure: String = "",
                  override val color: Color = Color.WHITE,
                  override val pasch: Int = 0) extends IPlayer {

  def this(name: String) = this(name, 0, 0, 0, Variable.INITIAL_PLAYER_MONEY)

  override def setPosition(newPosition: Int): Player = {
    if (newPosition < 0) {
      val newCurrentPosition = newPosition + Variable.TOTAL_NUMBER_OF_FIELDS
      copy(currentPosition = newCurrentPosition)
    } else {
      if (newPosition >= Variable.TOTAL_NUMBER_OF_FIELDS) {
        print("You go over Go \nCollect 200!\n")
        val newCurrentPosition = newPosition - Variable.TOTAL_NUMBER_OF_FIELDS
        val newMoney = money + Variable.MONEY_NEW_ROUND
        copy(currentPosition = newCurrentPosition, money = newMoney)
      } else {
        copy(currentPosition = newPosition)
      }
    }
 }

  def incrementMoney(amount: Int): Player = copy(money = money + amount, currentPosition = currentPosition)
  def decrementMoney(amount: Int): Player = copy(money = money - amount)
  def goToJail(): Player = copy(currentPosition= 10, inJail = 2)
  def decrementJailCounter(): Player = copy(inJail= inJail - 1)
  def setJailCounterZero(): Player = copy(inJail = 0, pasch = 0)
  def setPasch(i: Int): Player = copy(pasch = i)
  override def toString:String = name
}
