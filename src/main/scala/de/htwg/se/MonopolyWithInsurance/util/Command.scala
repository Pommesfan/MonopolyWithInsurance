package de.htwg.se.MonopolyWithInsurance.util

trait Command {

  def doStep: Unit
  def undoStep: Unit
  def redoStep: Unit

}