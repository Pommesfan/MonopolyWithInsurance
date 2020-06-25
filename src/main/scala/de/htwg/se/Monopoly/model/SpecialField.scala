package de.htwg.se.Monopoly.model

case class SpecialField(override val index: Int, override val name: String) extends Field(index, name){
  override def actOnPlayer(player: Player): SpecialField = SpecialField(index, name)

  override def toString: String = {
    "%d: %s\n".format(index, name)
  }
}
