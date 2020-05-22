package de.htwg.se.Monopoly.model

import scala.util.Random

case class Dice() {
  val random = Random

  def roll: Int = {
    random.nextInt(6) + 1
  }
}
