package de.htwg.se.Monopoly.model

import de.htwg.se.Monopoly.controller.GameStatus.GameStatus

abstract class Field( override val index: Int, override val name: String) extends IField {
  override def actOnPlayer(player: Player): (Field, GameStatus)
}
