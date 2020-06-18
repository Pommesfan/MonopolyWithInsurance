package de.htwg.se.Monopoly.model

import scala.io.StdIn.readLine

case class Street(override val index: Int,
                  override val name: String,
                  neighbourhoodTypes: NeighbourhoodTypes.Value, //private
                  price: Int, //private
                  rent: Int,
                  owner: Player = null) extends Field(index, name){

  override def actOnPlayer(player: Player, players: List[Player]): (Street, List[Player]) = {
    if (owner == player){
      println("You already own " + Street.this.name + ".")
      (Street(index, name, neighbourhoodTypes, price, rent, owner), players)
    } else if (owner == null){
      println(Street.this.name + " ist zum Kauf verfügbar.\nKaufen? (J/N)")
      val (street, newPlayer) = buyStreet(Street(index, name, neighbourhoodTypes, price, rent, owner), player)
      (street, players.updated(player.index, newPlayer))
    } else {
      val newPlayers = players.updated(player.index, player.decrementMoney(rent))
      val nextNewPlayers = newPlayers.updated(owner.index, owner.incrementMoney(rent))
      println("%s\n is owned by %s.\nYou paid him %d.".format(name, owner.name, rent))
      (Street(index, name, neighbourhoodTypes, price, rent, owner), nextNewPlayers)
    }
  }

  def buyStreet(street: Street, player: Player): (Street, Player) = {
    var input: String = ""
    do {
      input = readLine()
    } while (input == ("J", "N"))
    input match {
      case "N" =>
        (Street(street.index, street.name, street.neighbourhoodTypes, street.price, street.rent, street.owner), player)
      case "J" =>
        val newPlayer = player.decrementMoney(street.price)
        (Street(street.index, street.name, street.neighbourhoodTypes, street.price, street.rent, newPlayer), newPlayer)
    }
  }

}
