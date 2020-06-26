package de.htwg.se.Monopoly.model.fieldComponent

import de.htwg.se.Monopoly.model.playerComponent.IPlayer

trait IField{
  val index: Int
  val name: String

  def actOnPlayer(player: IPlayer): IField
}
