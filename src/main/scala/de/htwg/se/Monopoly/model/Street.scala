package de.htwg.se.Monopoly.model

case class Street(override val index: Int,
                  override val name: String,
                  neighbourhoodTypes: NeighbourhoodTypes.Value,
                  price: Int,
                  rent: Int,
                  owner: Option[Player] = None) extends Field(index, name){

  override def actOnPlayer(player: Player): Street = {
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