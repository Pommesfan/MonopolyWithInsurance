package de.htwg.se.Monopoly.model

object NewPlayerFactoryMethod {
  def createNewPlayer(name: String, index: Int): Player = {
    Player(name, index)
  }
}
