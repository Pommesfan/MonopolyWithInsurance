package de.htwg.se.Monopoly.model

case class ChanceCard(override val index: Int, override val name: String) extends Field(index, name) {
  override def actOnPlayer(player: Player): String = ChanceCardGenerator().generateRandomCard(player)
}
