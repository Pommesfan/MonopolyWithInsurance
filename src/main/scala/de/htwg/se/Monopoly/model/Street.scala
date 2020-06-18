package de.htwg.se.Monopoly.model

import de.htwg.se.Monopoly.controller.GameStatus
import de.htwg.se.Monopoly.controller.GameStatus.GameStatus


case class Street(override val index: Int,
                  override val name: String,
                  neighbourhoodTypes: NeighbourhoodTypes.Value,
                  price: Int,
                  rent: Int,
                  owner: Player = null) extends Field(index, name){

  override def actOnPlayer(player: Player): (Street, GameStatus) = {
    if (player.equals(owner)){
      (Street(index, name, neighbourhoodTypes, price, rent, owner), GameStatus.ALREADY_OWNED)
    } else if (owner == null){
      (Street(index, name, neighbourhoodTypes, price, rent, owner), GameStatus.CAN_BE_BOUGHT)
    } else {
      (Street(index, name, neighbourhoodTypes, price, rent, owner), GameStatus.OWNED_BY_OTHER_PLAYER)
    }
  }

  override def toString: String = {
    "%d: %s(%s), Kaufpreis %s$, %s$ Miete gehen an".format(index, name, neighbourhoodTypes, price, rent, owner)
  }
}
