package de.htwg.se.Monopoly.controller

import java.awt.Color

import de.htwg.se.Monopoly.model.{Board, ChanceCard, Player, SpecialField, Variable}
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StateMachineSpec extends WordSpec with Matchers {
  "A Controller has state" when {
    val board = Board(Variable.START_BOARD)
    val controller = new Controller(board)
    val context = controller.context
    "new" in {
      context.state.isInstanceOf[StartState] should be (true)
    }
    "set Player" in {
      context.setPlayer()
      context.state.isInstanceOf[NextPlayerState] should be (true)
    }
    "roll dice and handleField" should {
      "first player roll three doublets" in {
        controller.players = Vector[Player](Player("player1", 0, 0, 0, 1500, "Cat", Color.BLUE, 3), Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE))
        context.rollDice(controller)
        context.state.isInstanceOf[GoToJail] should be (true)
        context.nextPlayer()
      }
      "first player land on go" in {
        controller.players = Vector[Player](Player("player1", 0, 0, 0, 1500, "Cat", Color.BLUE), Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE))
        controller.actualField = SpecialField(0, "Los")
        context.rollDice(controller)
        context.state.isInstanceOf[LandedOnGo] should be (true)
        context.nextPlayer()
      }
      "first player land on visit jail" in {
        controller.players = Vector[Player](Player("player1", 0, 10, 0, 1500, "Cat", Color.BLUE), Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE))
        controller.actualField = SpecialField(10, "Gefängnis")
        context.rollDice(controller)
        context.state.isInstanceOf[VisitJail] should be (true)
        context.nextPlayer()
      }
      "first player land on free parking" in {
        controller.players = Vector[Player](Player("player1", 0, 20, 0, 1500, "Cat", Color.BLUE), Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE))
        controller.actualField = SpecialField(20, "Frei Parken")
        context.rollDice(controller)
        context.state.isInstanceOf[FreeParking] should be (true)
        context.nextPlayer()
      }
      "first player land on go to jail" in {
        controller.players = Vector[Player](Player("player1", 0, 30, 0, 1500, "Cat", Color.BLUE), Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE))
        controller.actualField = SpecialField(30, "Gefängnis: Gehen Sie ins Gefängnis")
        context.rollDice(controller)
        context.state.isInstanceOf[GoToJail] should be (true)
        context.nextPlayer()
      }
      "first player go to jail" in {
        controller.players = Vector[Player](Player("player1", 0, 30, 0, 1500, "Cat", Color.BLUE), Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE))
        controller.actualField = SpecialField(30, "Gefängnis: Gehen Sie ins Gefängnis")
        context.goToJail()
        context.state.isInstanceOf[GoToJail] should be (true)
        context.nextPlayer()
      }
      "first player get 'Ereigniskarte' go to jail" in {
        controller.players = Vector[Player](Player("player1", 0, 30, 0, 1500, "Cat", Color.BLUE), Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE))
        controller.actualField = ChanceCard(2, "Ereignisfeld", 4, 0, 0, -1, "Ereigniskarte: Gehe ins Gefängnis!\n")
        context.rollDice(controller)
        context.state.isInstanceOf[GoToJail] should be (true)
        context.nextPlayer()
      }
    }
  }
}