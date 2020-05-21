package de.htwg.se.Monopoly.aview

import de.htwg.se.Monopoly.model.Board

class Tui {

  def processInputLine(input: String, board:Board): Board = {
    input match {
      case "q" => board

    }
  }
}
