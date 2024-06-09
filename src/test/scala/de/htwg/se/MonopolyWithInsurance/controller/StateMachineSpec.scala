package de.htwg.se.MonopolyWithInsurance.controller

import java.awt.Color

import de.htwg.se.MonopolyWithInsurance.controller.controllerComponent.controllerBaseImpl.{Controller, FreeParking, GoToJail, LandedOnGo, NextPlayerState, StartState, VisitJail}
import de.htwg.se.MonopolyWithInsurance.model.fieldComponent.fieldBaseImpl.{ChanceCard, SpecialField}
import de.htwg.se.MonopolyWithInsurance.model.playerComponent.playerBaseImpl
import de.htwg.se.MonopolyWithInsurance.model.playerComponent.playerBaseImpl.Player
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StateMachineSpec extends WordSpec with Matchers {
  "A Controller has state" when {
    val controller = new Controller()
    val context = controller.context

    val loadController = new Controller()
    val loadContext = loadController.context
    "new" in {
      context.state.isInstanceOf[StartState] should be (true)
    }
    "set Player" in {
      context.setPlayer()
      context.state.isInstanceOf[NextPlayerState] should be (true)
    }
    "roll dice and handleField" should {
      "first player roll three doublets" in {
        controller.players = Vector[Player](playerBaseImpl.Player("player1", 0, 0, 0, 1500, "Cat", Color.BLUE, 3, insurance = None), playerBaseImpl.Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE, insurance = None))
        context.rollDice(controller)
        context.state.isInstanceOf[GoToJail] should be (true)
        context.nextPlayer()
      }
      "first player land on go" in {
        controller.players = Vector[Player](playerBaseImpl.Player("player1", 0, 0, 0, 1500, "Cat", Color.BLUE, insurance = None), playerBaseImpl.Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE, insurance = None))
        controller.actualField = SpecialField(0, "Los")
        context.rollDice(controller)
        context.state.isInstanceOf[LandedOnGo] should be (true)
        context.nextPlayer()
      }
      "first player land on visit jail" in {
        controller.players = Vector[Player](playerBaseImpl.Player("player1", 0, 10, 0, 1500, "Cat", Color.BLUE, insurance = None), playerBaseImpl.Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE, insurance = None))
        controller.actualField = SpecialField(10, "Gefängnis")
        context.rollDice(controller)
        context.state.isInstanceOf[VisitJail] should be (true)
        context.nextPlayer()
      }
      "first player land on free parking" in {
        controller.players = Vector[Player](playerBaseImpl.Player("player1", 0, 20, 0, 1500, "Cat", Color.BLUE, insurance = None), playerBaseImpl.Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE, insurance = None))
        controller.actualField = SpecialField(20, "Frei Parken")
        context.rollDice(controller)
        context.state.isInstanceOf[FreeParking] should be (true)
        context.nextPlayer()
      }
      "first player land on go to jail" in {
        controller.players = Vector[Player](playerBaseImpl.Player("player1", 0, 30, 0, 1500, "Cat", Color.BLUE, insurance = None), playerBaseImpl.Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE, insurance = None))
        controller.actualField = SpecialField(30, "Gefängnis: Gehen Sie ins Gefängnis")
        context.rollDice(controller)
        context.state.isInstanceOf[GoToJail] should be (true)
        context.nextPlayer()
      }
      "first player go to jail" in {
        controller.players = Vector[Player](playerBaseImpl.Player("player1", 0, 30, 0, 1500, "Cat", Color.BLUE, insurance = None), playerBaseImpl.Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE, insurance = None))
        controller.actualField = SpecialField(30, "Gefängnis: Gehen Sie ins Gefängnis")
        context.goToJail()
        context.state.isInstanceOf[GoToJail] should be (true)
        context.nextPlayer()
      }
      "first player get 'Ereigniskarte' go to jail" in {
        controller.players = Vector[Player](playerBaseImpl.Player("player1", 0, 30, 0, 1500, "Cat", Color.BLUE, insurance = None), playerBaseImpl.Player("player2", 1, 0, 0, 1500, "Car", Color.ORANGE, insurance = None))
        controller.actualField = ChanceCard(2, "Ereignisfeld", 4, 0, 0, -1, "Ereigniskarte: Gehe ins Gefängnis!\n")
        context.rollDice(controller)
        context.state.isInstanceOf[GoToJail] should be (true)
        context.nextPlayer()
      }
    }

    "next Player" should {
      "save old" in {
        context.state.isInstanceOf[NextPlayerState] should be (true)
        controller.save()
      }
      "load new" in {
        loadContext.state.isInstanceOf[StartState] should be (true)
      }
      "load saved Game" in {
        loadController.load()
        loadController should be equals(controller)
      }
    }
  }
}