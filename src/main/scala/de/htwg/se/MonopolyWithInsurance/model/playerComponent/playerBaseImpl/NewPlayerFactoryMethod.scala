package de.htwg.se.MonopolyWithInsurance.model.playerComponent.playerBaseImpl

import de.htwg.se.MonopolyWithInsurance.controller.InsuranceImplement.InsuranceA

import java.awt.Color
import de.htwg.se.MonopolyWithInsurance.model.playerComponent.playerBaseImpl

object NewPlayerFactoryMethod {
  def createNewPlayer(s: String, index: Int): Player = {
    val figureList = List("Car", "Cat", "Dog", "Fingerhut", "Hut", "Ship", "Shoe", "Wheelbarrow")
    val colors = List[Color](Color.BLUE, Color.ORANGE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN, Color.pink, Color.RED)

    val list = s.split(" ")
    if (list.length != 1) {
      playerBaseImpl.Player(list(0), index, figure = list(1), color = colors(index), insurance = None)
    } else {
      playerBaseImpl.Player(list(0), index, figure = figureList(index), color = colors(index), insurance = None)
    }
  }
}
