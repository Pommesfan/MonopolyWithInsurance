package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.model.{Grid, Player}
import de.htwg.se.Monopoly.util.Observable

class Controller(var grid: Grid) extends Observable{

  def setPlayers(player: List[Player]): Unit = {
    grid = grid.setPlayers(player)
    notifyObservers
  }

  def roll(): Unit = {
    grid = grid.roll
    notifyObservers
  }

  def gridToString: String = grid.toString

}
