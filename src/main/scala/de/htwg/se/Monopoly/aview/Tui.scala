package de.htwg.se.Monopoly.aview

import de.htwg.se.Monopoly.controller.{Controller, GameStatus}
import de.htwg.se.Monopoly.model.{Player}
import de.htwg.se.Monopoly.util.Observer

import scala.collection.mutable.ListBuffer

class Tui(controller: Controller) extends Observer {

  controller.add(this)
  var numPlayer: Int = 0

  def processInputLine(input: String): Unit = {
    val pattern = "p (.*)".r
    input match {
      case "help" =>
        printf("%-10s%s\n%-10s%s\n", "e", "exit", "p", "new Players")
      case "e" => print("exit Game\n")
      case "z" => controller.undo
      case "y" => controller.redo
      case "d" => if (controller.gameStatus == GameStatus.NEXT_PLAYER) {
        controller.rollDice()
      }
      case pattern(input) =>
        if (controller.gameStatus == GameStatus.IDLE) {
          setPlayers(input.toString)
        }
      case "J" => if (controller.gameStatus == GameStatus.CAN_BE_BOUGHT) {
        controller.buyField()
      }
      case "N" => if (controller.gameStatus == GameStatus.CAN_BE_BOUGHT) {
        controller.nextPlayer()
      }
      case _ => print("Kein Pattern matching!")
    }
  }

  def setPlayers(input: String): Unit = {
    val list = input.split(" ")
    var player = new ListBuffer[Player]()
    var num = 0
    for (i <- list if i != "p") {
      player += Player(i.toString, num)
      num += 1
    }
    controller.setPlayers(player.toVector)
  }

  override def update: Boolean = {
    var output = ""
    if (controller.gameStatus == GameStatus.NEXT_PLAYER) {
      print(controller.gameToString)
    }
    output += GameStatus.map(controller.gameStatus) + controller.currentPlayerIndex + "\n"
    print(output)
    true
  }
}
