package de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Monopoly.model.NeighbourhoodTypes
import de.htwg.se.Monopoly.model.playerComponent.IPlayer

case class Street(override val index: Int,
                  override val name: String,
                  neighbourhoodTypes: NeighbourhoodTypes.Value,
                  price: Int,
                  rent: Int,
                  owner: IPlayer = null) extends Field(index, name){

  override def actOnPlayer(player: IPlayer): Street = {
    Street(index, name, neighbourhoodTypes, price, rent, owner)
  }

  override def toString: String = {
    if (owner == null) {
      "%d: %s(%s), Kaufpreis %s$, Feld steht zum Verkauf und bringt %s$ Miete ein.\n".format(index, name, neighbourhoodTypes, price, rent)
    } else {
      "%d: %s(%s), Kaufpreis %s$, %s$ Miete gehen an %s.\n".format(index, name, neighbourhoodTypes, price, rent, owner)
    }
  }
}
