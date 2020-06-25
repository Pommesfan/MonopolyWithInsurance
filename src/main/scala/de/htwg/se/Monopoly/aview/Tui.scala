package de.htwg.se.Monopoly.aview

import de.htwg.se.Monopoly.controller.{BuyStreet, Controller, DecrementJailCounter, DiceRolled, HandleStreet, LandedOnField, MoneyTransaction, NewGameEvent, NextPlayer, NextPlayerState, OwnStreet, PlayerSet, StartState, WaitForNextPlayer}
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
      case "e" => print("exit Game\n")
      case "z" => controller.undo
      case "y" => controller.redo
      case "d" =>
        if (controller.context.state.isInstanceOf[NextPlayerState]) {
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
    case event: NewGameEvent => print("Bitte Geben Sie die Namen der Spieler an!\n(p name1 name2 ... name8)\n")
    case event: PlayerSet => printTui; print("Spieler 1 darf Würfeln!\n(d)\n")
    case event: LandedOnField => print("Du landest auf Feld Nummer " + controller.actualField + "\n")
    case event: OwnStreet => print("You landed on your own Street.\nNext Players turn.\n")
    case event: HandleStreet => print("Möchten Sie diese Straße kaufen? (J/N)\n")
    case event: DiceRolled => print("Du hast eine " + controller.rolledNumber._1 + " und eine "
      + controller.rolledNumber._2 +" gewürfelt.\n")
    case event: MoneyTransaction => print("MoneyTransaktion" + event.money + "\n")
    case event: DecrementJailCounter => print("Du befindest dich im Gefängnis. Würfel einen Pasch " +
      "oder warte " + event.counter + " Runden.\n")
    case event: NextPlayer => printTui; print("Nächster Spieler darf Würfeln!\n")
    case event: WaitForNextPlayer => print("Zug beenden?\n(next/n)\n")
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
