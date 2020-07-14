package de.htwg.se.Monopoly.model.boardComponent.boardBaseImpl

import com.google.inject.Inject
import de.htwg.se.Monopoly.model.boardComponent.IBoard
import de.htwg.se.Monopoly.model.fieldComponent.IField
import de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl.{ChanceCard, SpecialField, Street, Tax}
import de.htwg.se.Monopoly.model.{NeighbourhoodTypes, Variable}
import de.htwg.se.Monopoly.model.playerComponent.IPlayer

import scala.collection.mutable

case class Board(fields: Vector[IField]) extends IBoard{

  @Inject()
  def this() {
    this(Variable.START_BOARD)
  }

  def getField(player: IPlayer, newPosition: Int): IField = {
    fields(newPosition).actOnPlayer(player)
  }

  def getFieldsSameNeighbourhoodType(nhT: NeighbourhoodTypes.Value): Vector[IField] = {
    var sameNeighbourhood = Vector[IField] ()
    for (f <- fields) {
      f match {
        case s: Street =>
          if (s.neighbourhoodTypes == nhT) {
            sameNeighbourhood = sameNeighbourhood :+ s
          }
        case _ =>
      }
    }
    sameNeighbourhood
  }

  override def toString: String = {
    val boardString = new mutable.StringBuilder("")
    boardString ++= "%-6s %-25s %-10s %-5s %-5s %-20s\n".format("index", "name", "type", "price", "rent", "owner")
    for (i <- 0 to 39) {
      fields(i) match {
        case Street(index, name, neighbourhoodTypes, price, rent, owner) =>
          boardString ++= "%-6s %-25s %-10s %-5s %-5s %-20s\n".format(index, name, neighbourhoodTypes, price, rent, owner)
        case ChanceCard(index, name, cardIndex, getMoney, giveMoney, otherPlayerIndex, info) =>
          boardString ++= "%-6s %-25s\n".format(index, name)
        case Tax(index, name, taxAmount) =>
          boardString ++= "%-6s %-25s %14s\n".format(index, name, taxAmount)
        case SpecialField(index, name) =>
          boardString ++= "%-6s %-25s\n".format(index, name)
      }
    }
    boardString.toString()
  }
}
