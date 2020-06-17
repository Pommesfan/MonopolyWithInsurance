package de.htwg.se.Monopoly.controller

object GameStatus  extends Enumeration{
  type GameStatus = Value
  val IDLE, NEXT_PLAYER = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    NEXT_PLAYER ->"Next Player"
  )

  def message(gameStatus: GameStatus): Unit = {
    map(gameStatus)
  }

}