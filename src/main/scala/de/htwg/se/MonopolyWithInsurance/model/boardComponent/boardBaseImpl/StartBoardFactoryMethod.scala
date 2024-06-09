package de.htwg.se.MonopolyWithInsurance.model.boardComponent.boardBaseImpl

import de.htwg.se.MonopolyWithInsurance.model.Variable
import de.htwg.se.MonopolyWithInsurance.model.boardComponent.{IBoard, boardBaseImpl}

object StartBoardFactoryMethod {
  def createStartBoard(): IBoard = {
    boardBaseImpl.Board(Variable.START_BOARD)
  }
}
