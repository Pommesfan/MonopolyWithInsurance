package de.htwg.se.Monopoly.model

import scala.collection.mutable

case class Grid(board: Board, players: List[Player], currentPlayerIndex: Int) {
  def this(player: List[Player]) = this(new Board(), player, 0)

  def setPlayers(players: List[Player]): Grid = copy(players= players)

  def getActualPlayer: Player = {
    var player: Player = null
    for(i <- players if i.index == currentPlayerIndex) {
      player = i
    }
    player
  }

  def newMove: Grid = {
    if (getActualPlayer.inJail == 0) {
      val player = getActualPlayer.newPosition
      val (newboard, newPlayers) = board.newMoveBoard(player, players, player.currentPosition)
      Grid(newboard, newPlayers, incrementCurrentPlayerIndex())
    } else {
      Grid(board, players.updated(getActualPlayer.index, getActualPlayer.incrementJailCounter()), incrementCurrentPlayerIndex())
    }
  }

  def incrementCurrentPlayerIndex(): Int = if (currentPlayerIndex + 1 < players.length) {currentPlayerIndex + 1} else {0}

  override def toString: String = {
    val boardString = new mutable.StringBuilder("")
    if (board.fields == null) {
      boardString ++= "Wilkommen zu Monopoly!\n"
      boardString ++= "  help\n  e \texit\n  d \tdice\n  p \tname of player"
    } else {

      boardString ++= "%-6s %-25s %-10s %-5s %-5s %-20s\n".format("index", "name", "type", "price", "rent", "owner")
      for (i <- 0 to 39) {
        board.fields(i) match {
          case Street(index, name, neighbourhoodTypes, price, rent, owner) =>
            boardString ++= "%-6s %-25s %-10s %-5s %-5s %-20s\n".format(index, name, neighbourhoodTypes, price, rent, owner)
          case ChanceCard(index, name) =>
            boardString ++= "%-6s %-25s\n".format(index, name)
          case Tax(index, name, taxAmount) =>
            boardString ++= "%-6s %-25s %14s\n".format(index, name, taxAmount)
          case SpecialField(index, name) =>
            boardString ++= "%-6s %-25s\n".format(index, name)
        }
      }
      boardString ++= "\nPlayers:\n%-6s %-25s %-10s %-5s\n".format("index", "name", "money", "position")
      for (player <- players) {
        boardString ++= "%-6s %-25s %-10s %-5s\n".format(player.index, player.name, player.money, player.currentPosition)
      }
      //boardString ++= "%s".format(returnString)
    }
    boardString.toString()
  }
}
