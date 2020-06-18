package de.htwg.se.Monopoly.model

abstract class Field( override val index: Int, override val name: String) extends IField {
  override def actOnPlayer(player: Player, players: List[Player]): (Field, List[Player])
}
