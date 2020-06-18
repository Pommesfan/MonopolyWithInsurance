package de.htwg.se.Monopoly.model

import de.htwg.se.Monopoly.controller.GameStatus.GameStatus

trait IField{
  val index: Int
  val name: String

  def actOnPlayer(player: Player): (Field, GameStatus)
}
