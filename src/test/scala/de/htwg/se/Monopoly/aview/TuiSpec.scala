package de.htwg.se.Monopoly.aview

import de.htwg.se.Monopoly.controller.Controller
import de.htwg.se.Monopoly.model.{Player, StartBoardFactoryMethod}
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TuiSpec extends WordSpec with Matchers {
  "A Monopoly Tui" should {
    "on input 'help' print string" in {
      val controller = new Controller(StartBoardFactoryMethod.createStartBoard(), Vector[Player]())
      val tui = new Tui(controller)
      val stream = new java.io.ByteArrayOutputStream()
      tui.processInputLine("help") should be
      Console.withOut(stream) {
        print("e         exit\np         new Players\n")
      }
    }
    "start game, set player, dice and not buy street" in {
      val controller = new Controller(StartBoardFactoryMethod.createStartBoard(), Vector[Player]())
      val tui = new Tui(controller)
      tui.processInputLine("p player1 player2")
      controller.players should be(Vector[Player](Player("player1", 0), Player("player2", 1)))
      val oldPlayer = controller.players(0)
      tui.processInputLine("d")
      controller.players(0) should not be (oldPlayer)
      var i = controller.players(0).currentPosition
      while (i != 3 & i != 5 & i != 6 & i != 8 & i != 9 & i != 11 & i != 12) {
        tui.processInputLine("z")
        tui.processInputLine("d")
        i = controller.players(0).currentPosition
      }
      tui.processInputLine("N")
      controller.currentPlayerIndex should be (1)
      tui.processInputLine("e")
    }
    "start game, set player, dice and buy street" in {
      val controller = new Controller(StartBoardFactoryMethod.createStartBoard(), Vector[Player]())
      val tui = new Tui(controller)
      tui.processInputLine("p player1 player2")
      controller.players should be(Vector[Player](Player("player1", 0), Player("player2", 1)))
      val oldPlayer = controller.players(0)
      tui.processInputLine("d")
      controller.players(0) should not be (oldPlayer)
      var i = controller.players(0).currentPosition
      while (i != 3 & i != 5 & i != 6 & i != 8 & i != 9 & i != 11 & i != 12) {
        tui.processInputLine("z")
        tui.processInputLine("d")
        i = controller.players(0).currentPosition
      }
      tui.processInputLine("J")
      controller.currentPlayerIndex should be (1)
      tui.processInputLine("e")
    }
    "start game, set player, dice, undo and redo" in {
      val controller = new Controller(StartBoardFactoryMethod.createStartBoard(), Vector[Player]())
      val tui = new Tui(controller)
      tui.processInputLine("p player1 player2")
      controller.players should be(Vector[Player](Player("player1", 0), Player("player2", 1)))
      val old = controller.players(0)
      tui.processInputLine("d")
      val newController = controller
      newController.players(0) should not be (old)
      tui.processInputLine("z")
      controller.currentPlayerIndex should be (0)
      controller.players(0) should be (old)
      tui.processInputLine("y")
      controller should be (newController)
      val stream = new java.io.ByteArrayOutputStream()
      tui.processInputLine("Kein Pattern Matching") should be
      Console.withOut(stream) {
        print("Kein Pattern matching!")
      }
    }
    "start game, set player, dice, buy Street and undo and redo" in {
      val controller = new Controller(StartBoardFactoryMethod.createStartBoard(), Vector[Player]())
      val tui = new Tui(controller)
      tui.processInputLine("p player1 player2")
      controller.players should be(Vector[Player](Player("player1", 0), Player("player2", 1)))
      val oldPlayer = controller.players(0)
      tui.processInputLine("d")
      controller.players(0) should not be (oldPlayer)
      var i = controller.players(0).currentPosition
      while (i != 3 & i != 5 & i != 6 & i != 8 & i != 9 & i != 11 & i != 12) {
        tui.processInputLine("z")
        tui.processInputLine("d")
        i = controller.players(0).currentPosition
      }
      tui.processInputLine("J")
      controller.currentPlayerIndex should be (1)
      tui.processInputLine("z")
      tui.processInputLine("y")
      tui.processInputLine("e")
    }
  }
}