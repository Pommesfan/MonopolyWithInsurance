package de.htwg.se.Monopoly.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player1 = new Player("Player1")
      val player2 = Player("Player2", index = 1)
      "have a name"  in {
        player1.name should be("Player1")
        player2.name should be("Player2")
      }
      "have a index, e.g. first Player" in {
        player1.index should be (0)
      }
      "have a currentPosition" in {
        player1.currentPosition should be (0)
      }
      "have a status, if he is in jail" in {
        player1.inJail should be (0)
      }
      "have a amount of money" in {
        player1.money should be (Variable.INITIAL_PLAYER_MONEY)
      }
      "have a nice String representation" in {
        player1.toString should be("Player1")
      }
    }
    "roll the dice" should {
      val player1 = Player("Player1", index = 0)
      val player2 = Player("Player2", index = 1)
      val player3 = Player("Player3", index = 2, currentPosition = 38)
      "set Position from 0 to 7" in {
        player1.setPosition(7) should be (Player("Player1", 0, currentPosition = 7))
      }
      "Set Position from 0 to 38 (go 2 backwards)" in {
        player2.setPosition(-2) should be (Player("Player2", 1, 38))
      }
      "Set Position to 2" in {
        player3.setPosition(42) should be (Player("Player3", 2, 2, money = 1700))
      }
    }
    "earn money" should {
      val player1 = Player("Player1", index = 0)
      val player2 = Player("Player2", index = 1, currentPosition = 10, money = 1500)
      val player3 = Player("Player3", index = 2, currentPosition = 38, money = -100)
      val player4 = Player("Player4", index = 3, money = -500)
      val player5 = Player("Player5", index = 4, money = 100)
      "earn 200" in {
        player1.incrementMoney(200) should be (Player("Player1", index = 0, money = 1700))
        player2.incrementMoney(200) should be (Player("Player2", index = 1, currentPosition = 10, money = 1700))
        player3.incrementMoney(200) should be (Player("Player3", index = 2, currentPosition = 38, money = 100))
        player4.incrementMoney(200) should be (Player("Player4", index = 3, money = -300))
        player5.incrementMoney(200) should be (Player("Player5", index = 4, money = 300))
      }
      "lose 200" in {
        player1.decrementMoney(200) should be (Player("Player1", index = 0, money = 1300))
        player2.decrementMoney(200) should be (Player("Player2", index = 1, currentPosition = 10, money = 1300))
        player3.decrementMoney(200) should be (Player("Player3", index = 2, currentPosition = 38, money = -300))
        player4.decrementMoney(200) should be (Player("Player4", index = 3, money = -700))
        player5.decrementMoney(200) should be (Player("Player5", index = 4, money = -100))
      }
    }
    "go to jail" should {
      val player1 = Player("Player1", index = 0)
      val player1Jail = player1.goToJail()
      "go to Jail" in {
        player1.goToJail() should be (Player("Player1", index = 0, currentPosition = 10, inJail = 2))
        player1Jail.decrementJailCounter() should be (Player("Player1", index = 0, currentPosition = 10, inJail = 1))
      }
    }
  }
}