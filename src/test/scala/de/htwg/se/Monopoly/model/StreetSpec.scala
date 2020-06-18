package de.htwg.se.Monopoly.model

import java.io.ByteArrayInputStream

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StreetSpec extends WordSpec with Matchers {
  /**
  "A Street" when {
    "new" should {
      val street = Street(1, "Badstraße", NeighbourhoodTypes.Brown, 60, 2)
      "have a name, index, neighbourhoodType, price, rent, owner" in {
        street.name should be ("Badstraße")
        street.index should be (1)
        street.price should be (60)
        street.rent should be (2)
        street.owner should be (null)
        street.neighbourhoodTypes should be (NeighbourhoodTypes.Brown)
      }
    }
    "visit from Player" should {
      val player1 = new Player("Yvonne")
      val player2 = Player("Nicole", index = 1, currentPosition = 10, money = 1500)
      val street1 = Street(1, "Badstraße", NeighbourhoodTypes.Brown, 60, 2)
      val street2 = Street(3, "Turmstraße", NeighbourhoodTypes.Brown, 60, 4, player1)
      "is available, but not bought" in {
        val in = new ByteArrayInputStream("N".getBytes)
        Console.withIn(in) {
          street1.actOnPlayer(player1) should be(Street(1, "Badstraße", NeighbourhoodTypes.Brown, 60, 2))
        }
      }
      "is available and bought" in {
        val in = new ByteArrayInputStream("J".getBytes)
        Console.withIn(in) {
          street1.actOnPlayer(player1) should be(Street(1, "Badstraße", NeighbourhoodTypes.Brown, 60, 2, player1))
        }
      }
      "is own street" in {
        street2.actOnPlayer(player1) should be (Street(3, "Turmstraße", NeighbourhoodTypes.Brown, 60, 4, player1))
      }
      "is owned by another player" in {
        street2.actOnPlayer(player2) should be (Street(3, "Turmstraße", NeighbourhoodTypes.Brown, 60, 4, player1))
      }
    }
  }*/
}
