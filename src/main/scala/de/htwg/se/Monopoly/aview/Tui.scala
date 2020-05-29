package de.htwg.se.Monopoly.aview

import de.htwg.se.Monopoly.model.{Grid, Player}

import scala.collection.mutable.ListBuffer

class Tui {
  var numPlayer: Int = 0

  def processInputLine(input: String, grid: Grid): Grid = {
    val pattern = "p (.*)".r
    input match {
      case "help" =>
        printf("%-10s%s\n%-10s%s\n", "e", "exit", "p", "new Players")
        grid
      case "e" => print("exit Game\n")
        grid
      case "d" =>
        printf("%s darf würfeln\n", grid.getActualPlayer)
        grid.roll
      case "p" =>
        grid
      case pattern(input) =>
        val list = input.toString.split(" ")
        var player = new ListBuffer[Player]()
        var num = 0
        for (i <- list if i != "p") {
          player += Player(i.toString, num)
          num += 1
        }
        grid.setPlayers(player.toList)
      case _ =>
        printf("kein pattern matching\n")
        grid
    }
  }
}
