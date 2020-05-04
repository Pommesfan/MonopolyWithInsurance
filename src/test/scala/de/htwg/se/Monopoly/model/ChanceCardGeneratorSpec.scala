package de.htwg.se.Monopoly.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ChanceCardGeneratorSpec extends WordSpec with Matchers {
  "A ChanceCard" when {
    "new" should {
      val chanceCard1 = ChanceCardGenerator()
      "have parameter" in {
        chanceCard1.GO_POSITION should be (0)
        chanceCard1.MAYFAIR_POSITION should be (39)
        chanceCard1.BANK_MONEY_BONUS should be (100)
        chanceCard1.PRETTY_BONUS should be (50)
      }
    }
    "generate a randomCard" should {
      val chanceCard = ChanceCardGenerator()
      val player = new Player("Yvonne")
      "generate random" in {
        chanceCard.generateRandomCard(player) should fullyMatch regex """(([0-9a-zA-z\s].)*.)*"""
      }
    }
    "generate 'bandIsGivingMoney' Card" should {
      val chanceCard = ChanceCardGenerator()
      val player = new Player("Nicole")
      "player get 100" in {
        chanceCard.bankIsGivingMoney(player) should be("The Bank is giving you 100. \nKeep it safe!")
      }
    }
    "generate 'YouArePrettyGivingBonus' Card" should {
      val chanceCard = ChanceCardGenerator()
      val player = new Player("Jessica")
      "player get 50 " in {
        chanceCard.youArePrettyGivingBonus(player) should be("You have won a \nfashion contest. Receive 50.")
      }
    }
    "generate 'giveAmountToOtherPlayers' Card" should {
      "Player one lose 30, Player 2 get 30" in {
        val chanceCard = ChanceCardGenerator()
        val player1 = new Player("Yvonne")
        chanceCard.giveAmountToOtherPlayers(player1) should be ("The other player is smarter. \nGive him 30.")
      }
    }
    "get List of ChanceCards" should {
      val chanceCard = new ChanceCardGenerator()
      "generate List" in {
        chanceCard.listOfChanceCard()
      }
    }
    "unapply" should {
      val chanceCardGenerator = ChanceCardGenerator()
      "test for unapply method" in {
        ChanceCardGenerator.unapply(chanceCardGenerator)
      }
    }
  }
}
