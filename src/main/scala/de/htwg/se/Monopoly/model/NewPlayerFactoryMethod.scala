package de.htwg.se.Monopoly.model

object NewPlayerFactoryMethod {
  def createNewPlayer(s: String, index: Int): Player = {
    val figureList = List("Car", "Cat", "Dog", "Fingerhut", "Hut", "Ship", "Shoe", "Wheelbarrow")
    val list = s.split(" ")
    if (list.length != 1) {
      Player(list(0), index, figure = list(1))
    } else {
      Player(list(0), index, figure = figureList(index))
    }
  }
}
