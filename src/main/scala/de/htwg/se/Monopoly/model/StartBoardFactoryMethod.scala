package de.htwg.se.Monopoly.model

object StartBoardFactoryMethod {
  def createStartBoard(): Board = {
    Board(Variable.START_BOARD)
  }
}
