package de.htwg.se.MonopolyWithInsurance.model.boardComponent

import de.htwg.se.MonopolyWithInsurance.model.fieldComponent.IField
import de.htwg.se.MonopolyWithInsurance.model.NeighbourhoodTypes
import de.htwg.se.MonopolyWithInsurance.model.playerComponent.IPlayer

trait IBoard {
  val fields: Vector[IField]

  def getField(p: IPlayer, newPosition: Int): IField
  def getFieldsSameNeighbourhoodType(nhT: NeighbourhoodTypes.Value): Vector[IField]
}
