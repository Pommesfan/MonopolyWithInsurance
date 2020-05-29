package de.htwg.se.Monopoly.model

import scala.collection.mutable

case class Grid(board: Board) {
  def this(player: List[Player]) = this(new Board(player)) //wird beim starten aufgerufen
  def this(board: Board, field: Field) = {
    this(Board(board.players, board.fields, board.currentPlayerIndex))
    //this.returnString = string
  }
  val players: List[Player] = List[Player]()
  var returnString = ""

  def setPlayers(players: List[Player]): Grid = copy(board.setPlayers(players).init())

  def getActualPlayer: Player = board.getActualPlayer

  def roll: Grid = {
    val newBord1 = board.roll()
    val player = newBord1.getActualPlayer
    val position = player.currentPosition
    val field = board.fields.apply(position)
    val returnField = field.actOnPlayer(player)
    val newBoard2 = newBord1.setField(returnField)
    val newBoard3 = newBoard2.changePlayerIndex()
    new Grid(newBoard3, returnField)
  }


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
      for (player <- board.players) {
        boardString ++= "%-6s %-25s %-10s %-5s\n".format(player.index, player.name, player.money, player.currentPosition)
      }
      boardString ++= "%s".format(returnString)
    }
    boardString.toString()
  }
}
