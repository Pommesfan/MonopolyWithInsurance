package de.htwg.se.Monopoly.model

case class SpecialField(override val index: Int, override val name: String) extends Field(index, name){
  override def actOnPlayer(player: Player): SpecialField = {
    if (index == 0){
      player.incrementMoney(Variable.MONEY_NEW_ROUND)
      println("You landed on Go \nCollect 200!")
      SpecialField(index, name)
    } else if (index == 10) {
      println("You are visiting your \ndear friend in Jails.")
      SpecialField(index, name)
    } else if (index == 20) {
      println("You landed on Free Parking. \nNothing happens.")
      SpecialField(index, name)
    } else {
      println("You are in jail! \nYou will skip the next three turns")
      SpecialField(index, name)
    }
  }
}
