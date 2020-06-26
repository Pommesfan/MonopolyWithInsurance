package de.htwg.se.Monopoly.model.boardComponent

import de.htwg.se.Monopoly.model.fieldComponent.IField
import de.htwg.se.Monopoly.model.NeighbourhoodTypes
import de.htwg.se.Monopoly.model.playerComponent.IPlayer

trait IBoard {
  val fields: Vector[IField]

  def createNewBoard(): IBoard
  def getField(p: IPlayer, newPosition: Int): IField
  def getFieldsSameNeighbourhoodType(nhT: NeighbourhoodTypes.Value): Vector[IField]
  override def toString: String = ""
}
