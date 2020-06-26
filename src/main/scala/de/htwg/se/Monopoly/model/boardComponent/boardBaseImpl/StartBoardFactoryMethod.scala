package de.htwg.se.Monopoly.model.boardComponent.boardBaseImpl

import de.htwg.se.Monopoly.model.Variable
import de.htwg.se.Monopoly.model.boardComponent.boardBaseImpl

object StartBoardFactoryMethod {
  def createStartBoard(): Board = {
    boardBaseImpl.Board(Variable.START_BOARD)
  }
}
