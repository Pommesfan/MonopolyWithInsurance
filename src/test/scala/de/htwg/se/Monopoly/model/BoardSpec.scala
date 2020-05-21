package de.htwg.se.Monopoly.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoardSpec extends WordSpec with Matchers {

  "A Board" when {
    "new" should {
      val board = Board(List[Player](), null, 0)
      val board2 = new Board(List[Player]())
      "have a List of players" in {
        board.players should be(List())
      }
      "have a current player index" in {
        board.currentPlayerIndex should be(0)
      }
      "equals" in {
        board2 should be(Board(List(), null, 0))
      }
    }
    "unapply" should {
      val board = Board(List[Player](), null, 0)
      "test for unapply method" in {
        Board.unapply(board)
      }
    }
    "add player" should {
      val player1 = Player("Player1", 0)
      val player2 = Player("Player2", 1)
      val player3 = Player("Player3", 2)
      val player4 = Player("Player4", 3)
      val board = Board(List[Player](player1, player2), null, 0)
      "have a List of players" in {
        board.players should be(List[Player](Player("Player1", 0), Player("Player2", 1)))
      }
      "setPlayers" in {
        board.setPlayers(List[Player](player3, player4)) should be(Board(List[Player](player1, player2, player3, player4), null, 0))
      }
      "change PlayerIndex" in {
        board.changePlayerIndex() should be(Board(List[Player](player1, player2), null, 1))
      }
      "get actual player" in {
        val board2 = board.changePlayerIndex()
        board2.getActualPlayer should be(Player("Player2", 1))
        val board3 = board2.changePlayerIndex()
        board3.getActualPlayer should be(Player("Player1", 0))
      }
    }
    "init" should {
      val player1 = Player("Player1", 0)
      val player2 = Player("Player2", 0)
      val boardWithoutFields = Board(List[Player](player1, player2), null, 0)
      val boardWithFields = boardWithoutFields.init()
      "have all fields" in {
        boardWithFields.allfields() should have length (40)
        boardWithFields.fields(3) should be(Street(3, "Turmstrasse", NeighbourhoodTypes.Brown, 60, 4))
        boardWithFields.fields(31) should be(SpecialField(31, "Gefängnis: Gehen Sie ins Gefängnis"))
      }
      "change field" in {
        val board = boardWithFields.setField(Street(3, "Turmstrasse", NeighbourhoodTypes.Brown, 60, 4, player1))
        board.fields(3) should be(Street(3, "Turmstrasse", NeighbourhoodTypes.Brown, 60, 4, player1))
        board.getStreet(3) should be(Street(3, "Turmstrasse", NeighbourhoodTypes.Brown, 60, 4, player1))
      }
      "move player" in {
        val board = boardWithFields.roll()
        val newPlayerPosition = board.players(0).currentPosition
        board.players(0).currentPosition should be(newPlayerPosition)
      }
    }
  }
}
