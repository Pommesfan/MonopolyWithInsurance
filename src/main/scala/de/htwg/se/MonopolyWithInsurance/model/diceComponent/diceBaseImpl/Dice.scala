package de.htwg.se.MonopolyWithInsurance.model.diceComponent.diceBaseImpl

import de.htwg.se.MonopolyWithInsurance.model.diceComponent.IDice

import scala.util.Random

case class Dice() extends IDice {
  val random = Random

  def roll: Int = {
    random.nextInt(6) + 1
  }
}
