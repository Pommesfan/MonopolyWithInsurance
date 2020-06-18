package de.htwg.se.Monopoly.model


import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ChanceCardSpec extends WordSpec with Matchers {
  /**
  "A ChanceCard" when {
    "new" should {
      val chanceCard1 = ChanceCard(2, "Gemeinschaftsfeld")
      val chanceCard2 = ChanceCard(22, "Ereignisfeld")
      "have parameter" in {
        chanceCard1.GO_POSITION should be (0)
        chanceCard1.MAYFAIR_POSITION should be (39)
        chanceCard1.BANK_MONEY_BONUS should be (100)
        chanceCard1.PRETTY_BONUS should be (50)
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
      val chanceCard1 = ChanceCard(2, "Gemeinschaftsfeld")
      val chanceCard2 = ChanceCard(22, "Ereignisfeld")
      val player1 = Player("Yvonne", 0, 2, inJail = false, 1500)
      val player2 = Player("Nicole", 1, 22, inJail = false, 1000)
      "get a community chest" in {
        chanceCard1.actOnPlayer(player1)
      }
      "get a community chest 2" in {
        chanceCard2.actOnPlayer(player2)
      }
      "unapply" should {
        val chanceCard = ChanceCard(2, "Gemeinschaftsfeld")
        "test for unapply method" in {
          ChanceCard.unapply(chanceCard).get should be((2, "Gemeinschaftsfeld"))
        }
      }
    }
    "generate 'bandIsGivingMoney' Card" should {
      val chanceCard = ChanceCard(2, "Gemeinschaftsfeld")
      val player = new Player("Nicole")
      "player get 100" in {
        chanceCard.bankIsGivingMoney(player) should be(ChanceCard(2, "Gemeinschaftsfeld"))
      }
    }
    "generate 'YouArePrettyGivingBonus' Card" should {
      val chanceCard = ChanceCard(2, "Gemeinschaftsfeld")
      val player = new Player("Jessica")
      "player get 50 " in {
        chanceCard.youArePrettyGivingBonus(player) should be(ChanceCard(2, "Gemeinschaftsfeld"))
      }
    }
    "unapply" should {
      val chanceCard = ChanceCard(2, "Gemeinschaftsfeld")
      "test for unapply method" in {
        ChanceCard.unapply(chanceCard)
      }
    }
  }
  */
}
