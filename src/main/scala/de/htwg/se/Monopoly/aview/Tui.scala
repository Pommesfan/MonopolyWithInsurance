package de.htwg.se.Monopoly.aview

import de.htwg.se.Monopoly.controller.{BuyStreet, Controller, DecrementJailCounter, DiceRolled, GameOver, GoToJail, GoToJailEvent, HandleChanceCard, HandleStreet, LandedOnField, MoneyTransaction, NewGameEvent, NextPlayer, NextPlayerState, NotEnoughMoney, OwnStreet, PayForJail, PayToLeave, PlayerSet, RedoEvent, StartState, UndoEvent, WaitForNextPlayer}
import de.htwg.se.Monopoly.util.Observer

import scala.swing.Reactor

class Tui(controller: Controller) extends Reactor {

  listenTo(controller)
  var numPlayer: Int = 0

  def processInputLine(input: String): Unit = {
    val pattern = "p (.*)".r
    input match {
      case "help" =>
        printf("%-10s%s\n%-10s%s\n", "e", "exit", "p", "new Players")
      case "exit" =>
        print("exit Game\n")
      case "z" =>
        if (controller.context.state.isInstanceOf[NextPlayerState]) {
          controller.undo
        } else {
          print("Zurück ist derzeit nicht möglich.\nBitte erst den Spielzug beenden.\n")
        }
      case "y" =>
        if (controller.context.state.isInstanceOf[NextPlayerState]) {
          controller.redo
        } else {
          print("Vorwärts ist derzeit nicht möglich.\n")
        }
      case "d" =>
        if (controller.context.state.isInstanceOf[NextPlayerState]) {
          controller.rollDice()
        }
        if (controller.context.state.isInstanceOf[PayForJail]) {
          controller.rollDice()
        }
      case "next" | "n" =>
        controller.nextPlayer()
      case pattern(input) =>
        if (controller.context.state.isInstanceOf[StartState]) {
          setPlayers(input.toString)
        }
      case "J" =>
        if (controller.context.state.isInstanceOf[BuyStreet]) {
          controller.buyStreet()
        }
      case "N" =>
        if (controller.context.state.isInstanceOf[BuyStreet]) {
          controller.publish(new WaitForNextPlayer)
        }
      case "jail" =>
        if (controller.context.state.isInstanceOf[GoToJail]) {
          controller.context.nextPlayer()
          controller.publish(new WaitForNextPlayer)
        }
      case "pay" =>
        if (controller.context.state.isInstanceOf[PayForJail]) {
          controller.payToLeaveJail(controller.getActualPlayer)
        }
      case "print TUI" =>
        printTui
      case _ => print("Kein Pattern matching!")
    }
  }

  def setPlayers(input: String): Unit = {
    val list = input.split(" ")
    controller.setPlayers(list)
  }

  reactions += {
    case e: NewGameEvent => print("Wilkommen zu einer neuen Runde Monopoly!\nBitte Geben Sie die Namen der Spieler an!\n(p name1 name2 ... name8)\n")
    case e: UndoEvent => printTui; print("Nächster Spieler darf Würfeln! (d)\n")
    case e: RedoEvent => printTui
    case e: PlayerSet => printTui; print("Spieler 1 darf Würfeln!\n(d)\n")
    case e: LandedOnField => print("Du landest auf Feld Nummer " + controller.actualField + "\n")
    case e: OwnStreet => print("Diese Straße gehört dir.\n")
    case e: HandleStreet => print("Möchten Sie diese Straße kaufen? (J/N)\n")
    case e: DiceRolled => print("Du hast eine " + controller.rolledNumber._1 + " und eine "
      + controller.rolledNumber._2 + " gewürfelt.\n")
    case e: MoneyTransaction => print("Zahle " + e.money + "$\n")
    case e: DecrementJailCounter => print("Warte " + (e.counter + 1) + " Runden bis du aus dem Gefägnis frei kommst\noder kaufe dich in der nächsten Runde frei.\n")
    case e: NextPlayer => printTui; print("Nächster Spieler darf Würfeln! (d)\n")
    case e: WaitForNextPlayer => print("Zug beenden?\n(next/n)\n")
    case e: GoToJailEvent => print("Gehe ins Gefängnis (3xPasch /Feld \"Gehe ins Gefängnis\" /Ereigniskarte)\n(jail)\n")
    case e: PayToLeave => print("Du befindest dich im Gefägnis.\nPasch würfeln(d) oder Freikaufen(pay).\n")
    case e: HandleChanceCard => print(e.message)
    case e: NotEnoughMoney => print("Du kannst diese Straße nicht kaufen, da du nicht genug Geld besitzt.\n")
    case e: GameOver =>
      var i = 0
      print("GAME OVER!\n\n")
      for (p <- controller.players) {
        i += 1
        print(i + ": " + p.name + "\t" + p.money + "\n")
      }
      print("\nSpiel beenden (exit)\n")
  }

  def printTui: Unit = {
    var output = ""
    if(controller.context.state.isInstanceOf[NextPlayerState]) {
      print(controller.gameToString)
    }
    output += "Aktueller Spieler: " + controller.currentPlayerIndex + "\n"
    print(output)
  }
}
