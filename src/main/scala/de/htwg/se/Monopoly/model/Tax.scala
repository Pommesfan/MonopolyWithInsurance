package de.htwg.se.Monopoly.model

import de.htwg.se.Monopoly.controller.GameStatus
import de.htwg.se.Monopoly.controller.GameStatus.GameStatus

case class Tax(override val index: Int, override val name: String, taxAmount: Int) extends Field(index, name){
  override def actOnPlayer(player: Player): (Tax, GameStatus) = (Tax(index, name, taxAmount), GameStatus.TAX)

  override def toString: String = {
    "%d: %s, Steuern: %s$".format(index, name, taxAmount)
  }
}
