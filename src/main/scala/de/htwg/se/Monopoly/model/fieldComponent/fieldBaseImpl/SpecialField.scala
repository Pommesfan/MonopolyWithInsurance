package de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Monopoly.model.playerComponent.IPlayer

case class SpecialField(override val index: Int, override val name: String) extends Field(index, name){
  override def actOnPlayer(player: IPlayer): SpecialField = SpecialField(index, name)

  override def toString: String = {
    "%d: %s.\n".format(index, name)
  }
}
