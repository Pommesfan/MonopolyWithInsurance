package de.htwg.se.Monopoly.model

case class SpecialField(override val index: Int, override val name: String) extends Field(index, name){
  override def actOnPlayer(player: Player): String = {
    if (index == 0){
      player.incrementMoney(Variable.MONEY_NEW_ROUND)
      "You landed on Go \nCollect 200!"
    } else if (index == 10) {
      "You are visiting your \ndear friend in Jails."
    } else if (index == 20) {
      "You landed on Free Parking. \nNothing happens."
    } else {
      "You are in jail! \nYou will skip the next three turns"
    }
  }
}
