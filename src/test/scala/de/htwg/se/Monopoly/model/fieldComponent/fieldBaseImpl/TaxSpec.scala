package de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Monopoly.model.playerComponent.playerBaseImpl.Player
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TaxSpec extends WordSpec with Matchers {
  "A Tax" when {
    "create" should {
      val tax = Tax(22, "DiesIstEineSteuer", 200)
      "have a name, index, amount" in {
        tax.index should be (22)
        tax.name should be ("DiesIstEineSteuer")
        tax.taxAmount should be (200)
      }
    }
    "actOnPlayer" should {
      val tax = Tax(22, "DiesIstEineSteuer", 200)
      val player = Player(name = "Yvonne", 0, 0, inJail = 0, 1500)
      "return tax" in {
        tax.actOnPlayer(player) should be (Tax(22, "DiesIstEineSteuer", 200))
      }
      "return string" in {
        tax.toString should be("22: DiesIstEineSteuer: 200$.\n")
      }
    }
    "unapply" should {
      val tax = Tax(22, "DiesIstEineSteuer", 200)
      "test for unapply method" in {
        Tax.unapply(tax).get should be ((22, "DiesIstEineSteuer", 200))
      }
    }
  }
}
