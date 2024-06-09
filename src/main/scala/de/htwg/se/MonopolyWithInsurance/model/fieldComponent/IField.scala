package de.htwg.se.MonopolyWithInsurance.model.fieldComponent

import de.htwg.se.MonopolyWithInsurance.model.playerComponent.IPlayer

trait IField{
  val index: Int
  val name: String

  def actOnPlayer(player: IPlayer): IField
}
