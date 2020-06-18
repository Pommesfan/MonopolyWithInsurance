package de.htwg.se.Monopoly.model

case class SpecialField(override val index: Int, override val name: String) extends Field(index, name){
  override def actOnPlayer(player: Player, players: List[Player]): (SpecialField, List[Player]) = {
    if (index == 0){
      println("You landed on Go \nCollect 200!")
      (SpecialField(index, name), players.updated(player.index,player.incrementMoney(Variable.MONEY_NEW_ROUND)))
    } else if (index == 10) {
      println("You are visiting your \ndear friend in Jails.")
      (SpecialField(index, name), players.updated(player.index, player.setPosition(index)))
    } else if (index == 20) {
      println("You landed on Free Parking. \nNothing happens.")
      (SpecialField(index, name), players.updated(player.index, player.setPosition(index)))
    } else {
      println("You are in jail! \nYou will skip the next three turns")
      (SpecialField(index, name), players.updated(player.index, player.goToJail()))
    }
  }
}
