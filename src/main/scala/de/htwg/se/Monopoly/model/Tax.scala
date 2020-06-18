package de.htwg.se.Monopoly.model

case class Tax(override val index: Int, override val name: String, taxAmount: Int) extends Field(index, name){
  override def actOnPlayer(player: Player, players: List[Player]): (Tax, List[Player]) = {
    val newPlayers = players.updated(player.index, player.decrementMoney(taxAmount))
    println(name + ": " + taxAmount)
    (Tax(index, name, taxAmount), newPlayers)
  }
}
