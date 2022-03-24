package de.htwg.se.Monopoly.aview

import java.awt.Color

import de.htwg.se.Monopoly.controller.ExitGame
import de.htwg.se.Monopoly.controller.controllerComponent.controllerBaseImpl.{BuyStreet, Controller, ExitGameState, GameOverState, GoToJail, NextPlayerState, PayForJail, StartState}
import de.htwg.se.Monopoly.model.boardComponent.boardBaseImpl.StartBoardFactoryMethod
import de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl.SpecialField
import de.htwg.se.Monopoly.model.playerComponent.playerBaseImpl
import de.htwg.se.Monopoly.model.playerComponent.playerBaseImpl.Player
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TuiSpec extends WordSpec with Matchers {
  "A Monopoly Tui" should {
    val controller = new Controller(StartBoardFactoryMethod.createStartBoard(), Vector[Player]())
    val tui = new Tui(controller)
    "print string on input 'help'" in {
      val stream = new java.io.ByteArrayOutputStream()
      tui.processInputLine("help") should be
      Console.withOut(stream) {
        print("e         exit\np         new Players\n")
      }
    }
    "set players on input 'p player1 player2'" in {
      tui.processInputLine("p player1 player2")
      controller.players should be (Vector[Player](playerBaseImpl.Player("player1", 0, 0, 0, 1500, "Car", Color.BLUE, insurance = None), playerBaseImpl.Player("player2", 1, 0, 0, 1500, "Cat", Color.ORANGE, insurance = None)))
    }
    "roll dice  and not buy street on input'd' and 'N' for player1" in {
      val old = controller.rolledNumber
      tui.processInputLine("d")
      controller.rolledNumber should not be (old)
      var i = controller.getActualPlayer.currentPosition
      while (i != 3 & i != 5 & i != 6 & i != 8 & i != 9 & i != 11 & i != 12) {
        tui.processInputLine("n")
        tui.processInputLine("z")
        tui.processInputLine("d")
        i = controller.players(0).currentPosition
      }
      tui.processInputLine("N")
      tui.processInputLine("n")
      controller.currentPlayerIndex should be (1)
    }
    "roll dice  and buy street on input'd' and 'J' for player1" in {
      tui.processInputLine("z")
      controller.currentPlayerIndex should be (0)
      val old = controller.rolledNumber
      tui.processInputLine("d")
      controller.rolledNumber should not be (old)
      var i = controller.getActualPlayer.currentPosition
      while (i != 3 & i != 5 & i != 6 & i != 8 & i != 9 & i != 11 & i != 12) {
        tui.processInputLine("n")
        tui.processInputLine("z")
        tui.processInputLine("d")
        i = controller.players(0).currentPosition
      }
      tui.processInputLine("J")
      tui.processInputLine("n")
      controller.currentPlayerIndex should be (1)
    }
    "undo and redo on input 'z' and 'y'" in {
      controller.currentPlayerIndex should be (1)
      val old = controller
      tui.processInputLine("d")
      var i = controller.context.state.isInstanceOf[BuyStreet]
      while (!controller.context.state.isInstanceOf[BuyStreet]) {
        tui.processInputLine("n")
        tui.processInputLine("z")
        tui.processInputLine("d")
        i = controller.context.state.isInstanceOf[BuyStreet]
      }
      val stream = new java.io.ByteArrayOutputStream()
      tui.processInputLine("z") should be
      Console.withOut(stream) {
        print("Zurück ist derzeit nicht möglich.\nBitte erst den Spielzug beenden.\n")
      }
      tui.processInputLine("y") should be
      Console.withOut(stream) {
        print("Vorwärts ist derzeit nicht möglich.\n")
      }
      tui.processInputLine("J")
      tui.processInputLine("n")
      controller.currentPlayerIndex should be (0)
      val newController = controller
      tui.processInputLine("z")
      controller should be (old)
      controller.currentPlayerIndex should be (1)
      tui.processInputLine("y")
      controller should be (newController)
      tui.processInputLine("n")
      controller.currentPlayerIndex should be (0)
    }
  }
  "A second Monopoly Tui" should {
    val controller = new Controller(StartBoardFactoryMethod.createStartBoard(), Vector[Player]())
    val tui = new Tui(controller)
    "set players on input 'p player1 player2'" in {
      tui.processInputLine("p player1 player2")
      controller.players should be(Vector[Player](playerBaseImpl.Player("player1", 0, 0, 0, 1500, "Car", Color.BLUE, insurance = None), playerBaseImpl.Player("player2", 1, 0, 0, 1500, "Cat", Color.ORANGE, insurance = None)))
    }
    "player1 go to jail on input 'jail' after he landed on field 'goToJail'" in {
      controller.movePlayer(30)
      controller.actualField should be (SpecialField(30, "Gefängnis: Gehen Sie ins Gefängnis"))
      controller.context.state.isInstanceOf[GoToJail] should be (true)
      tui.processInputLine("jail")
      controller.getActualPlayer should be (playerBaseImpl.Player("player1", 0, 10, 2, 1500, "Car", Color.BLUE, insurance = None))
      tui.processInputLine("n")
      controller.currentPlayerIndex should be (1)
    }
    "player2 go to jail on input 'jail' after he landed on field 'goToJail'" in {
      controller.movePlayer(30)
      controller.actualField should be (SpecialField(30, "Gefängnis: Gehen Sie ins Gefängnis"))
      controller.context.state.isInstanceOf[GoToJail] should be (true)
      tui.processInputLine("jail")
      controller.getActualPlayer should be (playerBaseImpl.Player("player2", 1, 10, 2, 1500, "Cat", Color.ORANGE, insurance = None))
      tui.processInputLine("n")
      controller.currentPlayerIndex should be (0)
    }
    "player1 in jail, pay to leave jail" in {
      controller.context.state.isInstanceOf[PayForJail] should be (true)
      tui.processInputLine("pay")
      controller.getActualPlayer should not be (playerBaseImpl.Player("player1", 0, 10, 2, 1500, "Car", Color.BLUE, insurance = None))
      controller.context.setState(new NextPlayerState)
      tui.processInputLine("n")
      controller.currentPlayerIndex should be (1)
    }
    "player2 in jail, roll dice to leave jail: pasch" in {
      controller.context.state.isInstanceOf[PayForJail] should be (true)
      tui.processInputLine("d")
      var i = controller.rolledNumber._1 == controller.rolledNumber._2
      while (!i) {
        controller.undo
        tui.processInputLine("d")
        i = controller.rolledNumber._1 == controller.rolledNumber._2
      }
      controller.getActualPlayer should not be (playerBaseImpl.Player("player2", 1, 10, 2, 1500, "Cat", Color.ORANGE, insurance = None))
      controller.getActualPlayer.inJail should be (0)
      controller.getActualPlayer.pasch should be (0)
      if (controller.context.state.isInstanceOf[BuyStreet]) {
        tui.processInputLine("J")
      }
      if (controller.context.state.isInstanceOf[GoToJail]) {
        tui.processInputLine("jail")
      }
      tui.processInputLine("n")
      controller.undo
      controller.currentPlayerIndex should be (1)
    }
    "player2 in jail, roll dice to leave jail: no pasch" in {
      controller.context.state.isInstanceOf[PayForJail] should be (true)
      tui.processInputLine("d")
      var i = controller.rolledNumber._1 != controller.rolledNumber._2
      while (!i) {
        controller.undo
        tui.processInputLine("d")
        i = controller.rolledNumber._1 != controller.rolledNumber._2
      }
      controller.getActualPlayer should be (playerBaseImpl.Player("player2", 1, 10, 1, 1500, "Cat", Color.ORANGE, insurance = None))
      controller.context.setState(new NextPlayerState)
      tui.processInputLine("n")
      controller.currentPlayerIndex should be (0)
    }
    "exit Game" in {
      controller.exit()
      controller.context.state.isInstanceOf[ExitGameState] should be(true)
    }
  }
  "A third Monopoly Tui" should {
    val controller = new Controller(StartBoardFactoryMethod.createStartBoard(), Vector[Player]())
    val tui = new Tui(controller)
    "print tui on input 'print TUI'" in {
      tui.processInputLine("print TUI")
      controller.context.state.isInstanceOf[StartState] should be (true)
    }
    "print 'Kein Pattern matching!' on input 'falsche Eingabe'" in {
      tui.processInputLine("falsche Eingabe")
      controller.context.state.isInstanceOf[StartState] should be (true)
    }
    "game Over" in {
      controller.players = Vector[Player](playerBaseImpl.Player("player1", 0, 0, 0, 10, "Cat", Color.BLUE, insurance = None), playerBaseImpl.Player("player2", 1, 0, 0, 10, "Cat", Color.ORANGE, insurance = None))
      controller.context.setPlayer()
      controller.movePlayer(4)
      controller.context.state.isInstanceOf[GameOverState] should be (true)
    }
  }
}