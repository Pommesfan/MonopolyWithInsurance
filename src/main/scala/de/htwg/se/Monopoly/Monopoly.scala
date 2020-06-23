package de.htwg.se.Monopoly

import de.htwg.se.Monopoly.aview.Gui.SwingGui
import de.htwg.se.Monopoly.aview.Tui
import de.htwg.se.Monopoly.controller.{Controller, TestEvent}
import de.htwg.se.Monopoly.model.{Player, StartBoardFactoryMethod}

import scala.io.StdIn.readLine

object Monopoly {
  val controller = new Controller(StartBoardFactoryMethod.createStartBoard(), Vector[Player]())
  val tui = new Tui(controller)
  val gui = new SwingGui(controller)
  controller.publish(new TestEvent)
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
