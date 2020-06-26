package de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Monopoly.model.fieldComponent.IField
import de.htwg.se.Monopoly.model.playerComponent.IPlayer

abstract class Field( override val index: Int, override val name: String) extends IField {
  override def actOnPlayer(player: IPlayer): IField
}
