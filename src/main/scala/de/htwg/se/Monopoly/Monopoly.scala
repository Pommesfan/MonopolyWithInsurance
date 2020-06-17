package de.htwg.se.Monopoly

import de.htwg.se.Monopoly.aview.Tui
import de.htwg.se.Monopoly.controller.Controller
import de.htwg.se.Monopoly.model.Grid

import scala.io.StdIn.readLine

object Monopoly {
  val controller = new Controller(new Grid(List()))
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input: String = ""
    if (args.length > 0) input=args(0)
    if (!input.isEmpty) {
      tui.processInputLine(input)
    } else {
      do {
        input = readLine()
        tui.processInputLine(input)
      } while (input != "e")
    }
  }
}
