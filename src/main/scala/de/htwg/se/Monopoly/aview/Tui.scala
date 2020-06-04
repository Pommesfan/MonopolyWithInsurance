package de.htwg.se.Monopoly.aview

import de.htwg.se.Monopoly.controller.Controller
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
      case "d" => controller.roll()
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
  override def update: Unit = println(controller.gridToString)
}
