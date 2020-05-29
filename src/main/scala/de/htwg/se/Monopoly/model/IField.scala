package de.htwg.se.Monopoly.model

trait IField{
  val index: Int
  val name: String

  def actOnPlayer(player: Player): Field
}
