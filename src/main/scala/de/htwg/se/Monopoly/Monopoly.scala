package de.htwg.se.Monopoly

import de.htwg.se.Monopoly.aview.Tui
import de.htwg.se.Monopoly.model.{Board, Grid, Player}

import scala.io.StdIn.readLine

object Monopoly {
  var grid = new Grid(List())
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      println("Board : \n" + grid.toString)
      input = readLine()
      grid = tui.processInputLine(input, grid)
    } while (input != "e")
  }
}
