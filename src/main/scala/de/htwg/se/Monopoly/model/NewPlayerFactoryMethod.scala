package de.htwg.se.Monopoly.model

import java.awt.Color

object NewPlayerFactoryMethod {
  def createNewPlayer(s: String, index: Int): Player = {
    val figureList = List("Car", "Cat", "Dog", "Fingerhut", "Hut", "Ship", "Shoe", "Wheelbarrow")
    val colors = List[Color](Color.BLUE, Color.ORANGE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN, Color.pink, Color.RED)

    val list = s.split(" ")
    if (list.length != 1) {
      Player(list(0), index, figure = list(1), color = colors(index))
    } else {
      Player(list(0), index, figure = figureList(index), color = colors(index))
    }
  }
}
