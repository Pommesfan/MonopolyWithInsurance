package de.htwg.se.MonopolyWithInsurance.model.fieldComponent.fieldBaseImpl

import de.htwg.se.MonopolyWithInsurance.model.playerComponent.IPlayer

case class Tax(override val index: Int, override val name: String, taxAmount: Int) extends Field(index, name){
  override def actOnPlayer(player: IPlayer): Tax = Tax(index, name, taxAmount)

  override def toString: String = {
    "%d: %s: %s$.\n".format(index, name, taxAmount)
  }
}
