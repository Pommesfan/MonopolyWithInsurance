package de.htwg.se.Monopoly

import de.htwg.se.Monopoly.aview.Tui
import de.htwg.se.Monopoly.model.{Board, Player}

import scala.io.StdIn.readLine

object Monopoly {
  var board = Board()
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      println("Board : \n" + board.toString)
      input = readLine()
      board = tui.processInputLine(input, board)
    } while (input != "q")
  }
}
