package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.model.{Board, ChanceCard, NeighbourhoodTypes, Player, SpecialField, Street, Tax, Variable}
import de.htwg.se.Monopoly.util.Observer
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {

  "A Controller" when {
    "observed by an Observer" should {
      val board = Board(Variable.START_BOARD)
      val controller = new Controller(board)
      val observer = new Observer {
        var updated: Boolean = false

        def isUpdated: Boolean = updated

        override def update: Boolean = {
          updated = true; updated
        }
      }
      controller.add(observer)
      "notify its Observer after set players" in {
        controller.setPlayers(Array[String]("Player1", "Player2", "Player3"))
        observer.updated should be(true)
        controller.players should have length (3)
      }
      "notify its Observer after move Player to Street(5, \"Südbahnhof\")" in {
        controller.movePlayer(5) should be(Street(5, "Südbahnhof", NeighbourhoodTypes.Station, 200, 25))
        observer.updated should be(true)
      }
      "notify its Observer after player buys Street" in {
        controller.buyStreet()
        observer.updated should be(true)
        controller.context.state.isInstanceOf[NextPlayerState] should be(true)
      }
      "notify its Observer after move player to SpecialField(30, \"Gefängnis: Gehen Sie ins Gefängnis\")" in {
        controller.movePlayer(30) should be(SpecialField(30, "Gefängnis: Gehen Sie ins Gefängnis"))
        controller.players(controller.currentPlayerIndex - 1) should be(Player("Player2", 1, 10, 3, 1500))
        observer.updated should be(true)
      }
      "notify its Observer after move Player to Tax(4, \"Einkommenssteuer\", 200)" in {
        controller.movePlayer(4) should be(Tax(4, "Einkommenssteuer", 200))
        observer.updated should be(true)
      }
      "notify its Observer after handle ChanceCard" in {
        controller.movePlayer(2).isInstanceOf[ChanceCard] should be(true)
        observer.updated should be(true)
      }
      "notiy its Observer after decrement jail counter" in {
        controller.movePlayer(5) should be(SpecialField(10, "Gefängnis"))
        observer.updated should be(true)
      }
      "notiy its Observer after move Player to Street(5, \"Südbahnhof\", NeighbourhoodTypes.Station, 200, 25, player1)" in {
        controller.movePlayer(1) should be(Street(5, "Südbahnhof", NeighbourhoodTypes.Station, 200, 25, Some(Player("Player1", 0, 5, 0, 1500))))
        observer.updated should be(true)
      }
      "notify its Observer after handle ChanceCard(7, \"Ereignisfeld\", (- 30), 30, 1)" in {
        val old = controller.players(controller.currentPlayerIndex)
        controller.handleChanceCard(ChanceCard(7, "Ereignisfeld", (-30), 30, 1))
        controller.players(controller.currentPlayerIndex - 1) should not be (old)
        observer.updated should be(true)
      }
      "notify its Observer after handle own Street" in {
        controller.nextPlayer()
        controller.nextPlayer()
        controller.movePlayer(39) should be(Street(5, "Südbahnhof", NeighbourhoodTypes.Station, 200, 25, Some(Player("Player1", 0, 5, 0, 1500))))
        observer.updated should be(true)
      }
      "notify its Observer after move to field \"30: FreeParking\"" in {
        controller.players = controller.players.updated(1, Player("Player2", 1, 10, 0, 1500))
        val old = controller.players(controller.currentPlayerIndex)
        controller.movePlayer(10)
        controller.players(controller.currentPlayerIndex - 1) should not be (old)
        controller.context.state.isInstanceOf[NextPlayerState] should be(true)
        observer.updated should be(true)
      }
      "notify its Observer after move to field \"0: Go\"" in {
        controller.movePlayer(34)
        observer.updated should be(true)
      }
      "notify its Observer after move to field \"10: Jail\"" in {
        controller.movePlayer(5)
        observer.updated should be(true)
      }
    }

  }
}