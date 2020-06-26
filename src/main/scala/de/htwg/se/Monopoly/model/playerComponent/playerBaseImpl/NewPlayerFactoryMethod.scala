package de.htwg.se.Monopoly.model.playerComponent.playerBaseImpl

import java.awt.Color
import de.htwg.se.Monopoly.model.playerComponent.playerBaseImpl

object NewPlayerFactoryMethod {
  def createNewPlayer(s: String, index: Int): Player = {
    val figureList = List("Car", "Cat", "Dog", "Fingerhut", "Hut", "Ship", "Shoe", "Wheelbarrow")
    val colors = List[Color](Color.BLUE, Color.ORANGE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN, Color.pink, Color.RED)

    val list = s.split(" ")
    if (list.length != 1) {
      playerBaseImpl.Player(list(0), index, figure = list(1), color = colors(index))
    } else {
      playerBaseImpl.Player(list(0), index, figure = figureList(index), color = colors(index))
    }
  }
}
