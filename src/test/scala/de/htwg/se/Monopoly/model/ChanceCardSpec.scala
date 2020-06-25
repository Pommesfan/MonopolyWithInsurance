package de.htwg.se.Monopoly.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ChanceCardSpec extends WordSpec with Matchers {

  "A ChanceCard" when {
    "new" should {
      val chanceCard1 = ChanceCard(2, "Gemeinschaftsfeld", 0, 0, 0, -1)
      val chanceCard2 = ChanceCard(22, "Ereignisfeld", 0, 0, 0, -1)
      "have parameter" in {
        chanceCard1.BANK_MONEY_BONUS should be(100)
        chanceCard1.PRETTY_BONUS should be(50)
        chanceCard1.GIVE_BONUS should be(30)
      }
      "have a index and name, is a cummunity chest" in {
        chanceCard1.index should be(2)
        chanceCard1.name should be("Gemeinschaftsfeld")
      }
      "have a index and name, is a Chance kart" in {
        chanceCard2.index should be(22)
        chanceCard2.name should be("Ereignisfeld")
      }
    }
    "act on Player" should {
      val chanceCard1 = ChanceCard(2, "Gemeinschaftsfeld", 0, 0, 0, -1)
      val chanceCard2 = ChanceCard(22, "Ereignisfeld", 0, 0, 0, -1)
      val player1 = Player("Player1", 0, 2, inJail = 0, 1500)
      val player2 = Player("Player2", 1, 22, inJail = 0, 1000)
      "get a community chest" in {
        chanceCard1.actOnPlayer(player1)
      }
      "get a community chest 2" in {
        chanceCard2.actOnPlayer(player2)
      }
      "unapply" should {
        val chanceCard = ChanceCard(2, "Gemeinschaftsfeld", 0, 0, 0, -1, "")
        "test for unapply method" in {
          ChanceCard.unapply(chanceCard).get should be((2, "Gemeinschaftsfeld", 0, 0, 0, -1, ""))
        }
      }
    }
    "generate 'bankIsGivingMoney' Card" should {
      val chanceCard = ChanceCard(2, "Gemeinschaftsfeld", 0, 0, 0, -1)
      val player = new Player("Player1")
      "player get 100" in {
        chanceCard.bankIsGivingMoney(player) should be(ChanceCard(2, "Gemeinschaftsfeld", 1, 100, 0, -1, "Ereigniskarte: Die Bank gibt dir 100$.\n"))
      }
      "return string" in {
        chanceCard.bankIsGivingMoney(player).toString should be("2: Gemeinschaftsfeld, Du bekommst 100$.\n")
      }
    }
    "generate 'YouArePrettyGivingBonus' Card" should {
      val chanceCard = ChanceCard(2, "Gemeinschaftsfeld", 0, 0, 0, -1)
      val player = new Player("Player1")
      "player get 50 " in {
        chanceCard.youArePrettyGivingBonus(player) should be(ChanceCard(2, "Gemeinschaftsfeld", 2, 50, 0, -1, "Ereigniskarte: Du hast eine Fashion-Wettbewerb gewonnen.\n Erhalte 50$.\n"))
      }
      "return string" in {
        chanceCard.youArePrettyGivingBonus(player).toString should be("2: Gemeinschaftsfeld, Du bekommst 50$.\n")
      }
    }
    "generate 'giveAmountToOtherPlayers' Card" should {
      val chanceCard = ChanceCard(2, "Gemeinschaftsfeld", 0, 0, 0, -1)
      val player = new Player("Player1")
      "player get 50 " in {
        chanceCard.giveAmountToOtherPlayers(player) should be(ChanceCard(2, "Gemeinschaftsfeld", 3, -30, 30, 1, "Ereigniskarte: Gib dem anderen Spieler 30.\n"))
      }
      "return string" in {
        chanceCard.giveAmountToOtherPlayers(player).toString should be("2: Gemeinschaftsfeld, Du bekommst/musst Zahlen: -30$, an 1.\n")
      }
    }
    "generate 'goToJail' Card" should {
      val chanceCard = ChanceCard(2, "Gemeinschaftsfeld", 0, 0, 0, -1)
      val player = new Player("Player1")
      "player go to jail" in {
        chanceCard.goToJail(player) should be(ChanceCard(2, "Gemeinschaftsfeld", 4, 0, 0, -1, "Ereigniskarte: Gehe ins Gefängnis!\n"))
      }
      "return string" in {
        chanceCard.goToJail(player).toString should be("2: Gemeinschaftsfeld, Gehe ins Gefängnis.\n")
      }
    }
    "unapply" should {
      val chanceCard = ChanceCard(2, "Gemeinschaftsfeld", 0, 0, 0, -1)
      "test for unapply method" in {
        ChanceCard.unapply(chanceCard)
      }
    }
  }
}

