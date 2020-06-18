package de.htwg.se.Monopoly.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  /**
  "A Player" when {
    "new" should {
      val player1 = new Player("Yvonne")
      val player2 = Player("Nicole", index = 1)
      "have a name"  in {
        player1.name should be("Yvonne")
        player2.name should be("Nicole")
      }
      "have a index, e.g. second Player" in {
        player1.index should be (0)
      }
      "have a currentPosition" in {
        player1.currentPosition should be (0)
      }
      "have a status, if he is in jail" in {
        player1.inJail should be (false)
      }
      "have a amount of money" in {
        player1.money should be (Variable.INITIAL_PLAYER_MONEY)
      }
      "have a nice String representation" in {
        player1.toString should be("Yvonne")
      }
      "have no streets" in {
        player1.streets should be (empty)
      }
    }
    "roll the dice" should {
      val player1 = Player("Yvonne", index = 0)
      val player2 = Player("Nicole", index = 1)
      val player3 = Player("Jessica", index = 2, currentPosition = 38)
      "set Position from 0 to 7" in {
        player1.setPosition(7) should be (Player("Yvonne", 0, currentPosition = 7))
      }
      "Set Position from 0 to 39 (go 2 backwards)" in {
        player2.setPosition(-2) should be (Player("Nicole", 1, 38))
      }
      "Set Position to 3" in {
        player3.setPosition(42) should be (Player("Jessica", 2, 3, money = 1700))
      }
    }
    "earn money" should {
      val player1 = Player("Yvonne", index = 0)
      val player2 = Player("Nicole", index = 1, currentPosition = 10, money = 1500)
      val player3 = Player("Jessica", index = 2, currentPosition = 38, money = -100)
      val player4 = Player("Tamara", index = 3, money = -500)
      val player5 = Player("Michael", index = 4, money = 100)
      "earn 200" in {
        player1.incrementMoney(200) should be (Player("Yvonne", index = 0, money = 1700))
        player2.incrementMoney(200) should be (Player("Nicole", index = 1, currentPosition = 10, money = 1700))
        player3.incrementMoney(200) should be (Player("Jessica", index = 2, currentPosition = 38, money = 100))
        player4.incrementMoney(200) should be (Player("Tamara", index = 3, money = -300))
        player5.incrementMoney(200) should be (Player("Michael", index = 4, money = 300))
      }
      "lose 200" in {
        player1.decrementMoney(200) should be (Player("Yvonne", index = 0, money = 1300))
        player2.decrementMoney(200) should be (Player("Nicole", index = 1, currentPosition = 10, money = 1300))
        player3.decrementMoney(200) should be (Player("Jessica", index = 2, currentPosition = 38, money = -300))
        player4.decrementMoney(200) should be (Player("Tamara", index = 3, money = -700))
        player5.decrementMoney(200) should be (Player("Michael", index = 4, money = -100))
      }
    }
  }

*/
}
