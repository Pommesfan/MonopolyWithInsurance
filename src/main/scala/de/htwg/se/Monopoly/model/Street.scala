package de.htwg.se.Monopoly.model

case class Street(override val index: Int,
                  override val name: String,
                  neighbourhoodTypes: NeighbourhoodTypes.Value,
                  price: Int,
                  rent: Int,
                  owner: Player = null) extends Field(index, name){
  override def actOnPlayer(player: Player): String = {
    if (owner == player){
      "You already own " + Street.this.name + "."
    } else if (owner == null){
      Street.this.name + " is available \nfor purchase."
    } else {
      player.decrementMoney(rent)
      owner.incrementMoney(rent)
      "%s\n is owned by %s.\nYou paid him %d.".format(name, owner.name, rent)
    }
  }
}
