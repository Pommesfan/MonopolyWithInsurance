package de.htwg.se.Monopoly.model

case class Board(fields: List[Field]) {
  def this() = this(Variable.START_BOARD)

  def setField(field: Field): Board = {
    copy(fields = Board.this.fields.updated(index, field))
  }

  def getStreet(index: Int): Field = {
    var street: Field = null
    for (i <- fields if i.index == index) {
      street = i
    }
    street
  }

  def newMoveBoard(player: Player, players: List[Player], position: Int): (Board, List[Player]) = {
    val (field, newPlayers) = getStreet(position).actOnPlayer(player, players)
    (setStreet(position, field), newPlayers)
  }
}
