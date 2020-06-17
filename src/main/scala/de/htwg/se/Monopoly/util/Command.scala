package de.htwg.se.Monopoly.util

trait Command {

  def doStep: Unit
  def undoStep: Unit
  def redoStep: Unit

}
