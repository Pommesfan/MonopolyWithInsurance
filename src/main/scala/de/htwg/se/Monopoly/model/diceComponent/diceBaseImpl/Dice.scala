package de.htwg.se.Monopoly.model.diceComponent.diceBaseImpl

import de.htwg.se.Monopoly.model.diceComponent.IDice

import scala.util.Random

case class Dice() extends IDice {
  val random = Random

  def roll: Int = {
    random.nextInt(6) + 1
  }
}
