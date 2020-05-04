package de.htwg.se.Monopoly.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoardSpec extends WordSpec with Matchers {

  "A Board" when {
    "new" should {
      val board = Board()
      val player1 = new Player("Yvonne")
      val player2 = new Player("Nicole")
      "have a List of players" in {
        board.players should be (List(Player("Player1", 0), Player("Player2", 1)))
      }
      "have a current player index" in {
        board.currentPlayerIndex should be (0)
      }
      "have all fields" in {
        board.allfields(3) should be (Street(3, "Turmstrasse", NeighbourhoodTypes.Brown, 60, 4))
      }
    }
    "unapply" should {
      val board = Board()
      "test for unapply method" in {
        Board.unapply(board)
      }
    }
  }
}
