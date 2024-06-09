package de.htwg.se.MonopolyWithInsurance

import com.google.inject.{Guice, Injector}
import de.htwg.se.MonopolyWithInsurance.aview.Gui.SwingGui
import de.htwg.se.MonopolyWithInsurance.aview.Tui
import de.htwg.se.MonopolyWithInsurance.controller.{IController, NewGameEvent}

import scala.io.StdIn.readLine

object Monopoly {
  val injector: Injector = Guice.createInjector(new MonopolyModule())
  val controller: IController = injector.getInstance(classOf[IController])
  val gui = new SwingGui(controller)
  val tui = new Tui(controller)
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
    System.exit(0)
  }
}
