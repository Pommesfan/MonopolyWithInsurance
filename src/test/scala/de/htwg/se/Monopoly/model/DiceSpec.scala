package de.htwg.se.Monopoly.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DiceSpec extends WordSpec with Matchers {
  "A Dice" when {
    "new" should {
      val dice = new Dice
      "return a random number between 1 and 6" in {
        dice.roll should be < 7
      }
    }
    "unapply" should {
      val dice = new Dice
      "test for unapply method" in {
        Dice.unapply(dice)
      }
    }
  }
}
