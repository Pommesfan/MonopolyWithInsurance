package de.htwg.se.Monopoly.model

import de.htwg.se.Monopoly.controller.GameStatus
import de.htwg.se.Monopoly.controller.GameStatus.GameStatus

case class SpecialField(override val index: Int, override val name: String) extends Field(index, name){
  override def actOnPlayer(player: Player): (SpecialField, GameStatus) = {
    if (index == 0){
      player.incrementMoney(Variable.MONEY_NEW_ROUND)
      (SpecialField(index, name), GameStatus.LANDED_ON_GO)
    } else if (index == 10) {
      (SpecialField(index, name), GameStatus.JAIL_VISIT)
    } else if (index == 20) {
      (SpecialField(index, name), GameStatus.FREE_PARKING)
    } else {
      (SpecialField(index, name), GameStatus.GO_TO_JAIL)
    }
  }
  override def toString: String = {
    "%d: %s".format(index, name)
  }
}
