package de.htwg.se.Monopoly.model

case class Tax(override val index: Int, override val name: String, taxAmount: Int) extends Field(index, name){
  override def actOnPlayer(player: Player): Tax = Tax(index, name, taxAmount)

  override def toString: String = {
    "%d: %s, Steuern: %s$".format(index, name, taxAmount)
  }
}
