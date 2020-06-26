package de.htwg.se.Monopoly

import de.htwg.se.Monopoly.aview.Gui.SwingGui
import de.htwg.se.Monopoly.aview.Tui
import de.htwg.se.Monopoly.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Monopoly.controller.NewGameEvent
import de.htwg.se.Monopoly.model.boardComponent.boardBaseImpl.StartBoardFactoryMethod
import de.htwg.se.Monopoly.model.playerComponent.playerBaseImpl.Player

import scala.io.StdIn.readLine

object Monopoly {
  val controller = new Controller(StartBoardFactoryMethod.createStartBoard(), Vector[Player]())
  val tui = new Tui(controller)
  val gui = new SwingGui(controller)
  controller.publish(new NewGameEvent)

  def main(args: Array[String]): Unit = {
    var input: String = ""
    if (args.length > 0) input=args(0)
    if (!input.isEmpty) {
      tui.processInputLine(input)
    } else {
      do {
        input = readLine()
        tui.processInputLine(input)
      } while (input != "exit")
    }
  }
}
