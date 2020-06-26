package de.htwg.se.Monopoly.model.boardComponent.boardBaseImpl

import de.htwg.se.Monopoly.model.Variable
import de.htwg.se.Monopoly.model.boardComponent.{IBoard, boardBaseImpl}

object StartBoardFactoryMethod {
  def createStartBoard(): IBoard = {
    boardBaseImpl.Board(Variable.START_BOARD)
  }
}
