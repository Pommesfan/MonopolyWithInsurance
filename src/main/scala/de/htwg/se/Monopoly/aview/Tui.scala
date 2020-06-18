package de.htwg.se.Monopoly.aview

import de.htwg.se.Monopoly.controller.{Controller, GameStatus}
import de.htwg.se.Monopoly.model.{Grid, Player}
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
      case "d" => controller.newMove()
      case pattern(input) =>
        val list = input.toString.split(" ")
        var player = new ListBuffer[Player]()
        var num = 0
        for (i <- list if i != "p") {
          player += Player(i.toString, num)
          num += 1
        }
        controller.setPlayers(player.toList)
      case _ => printf("kein pattern matching\n")
    }
    controller.remove(this)
  }

  override def update: Boolean = {
    print(controller.gridToString)
    print(GameStatus.message(controller.gameStatus))
    controller.gameStatus=GameStatus.IDLE
    true
  }
}
