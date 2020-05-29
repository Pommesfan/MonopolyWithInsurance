package de.htwg.se.Monopoly.model

import scala.io.StdIn.readLine

case class Street(override val index: Int,
                  override val name: String,
                  neighbourhoodTypes: NeighbourhoodTypes.Value, //private
                  price: Int, //private
                  rent: Int,
                  owner: Player = null) extends Field(index, name){

  override def actOnPlayer(player: Player): Street = {
    if (owner == player){
      println("You already own " + Street.this.name + ".")
      Street(index, name, neighbourhoodTypes, price, rent, owner)
    } else if (owner == null){
      println(Street.this.name + " ist zum Kauf verfügbar.\nKaufen? (J/N)")
      buyStreet(Street(index, name, neighbourhoodTypes, price, rent, owner), player)
    } else {
      player.decrementMoney(rent)
      owner.incrementMoney(rent)
      println("%s\n is owned by %s.\nYou paid him %d.".format(name, owner.name, rent))
      Street(index, name, neighbourhoodTypes, price, rent, owner)
    }
  }

  def buyStreet(street: Street, player: Player): Street = {
    var input: String = ""
    do {
      input = readLine()
    } while (input == ("J", "N"))
    input match {
      case "N" =>
        Street(street.index, street.name, street.neighbourhoodTypes, street.price, street.rent, street.owner)
      case "J" =>
        player.decrementMoney(street.price)
        Street(street.index, street.name, street.neighbourhoodTypes, street.price, street.rent, player)
    }
  }
}
