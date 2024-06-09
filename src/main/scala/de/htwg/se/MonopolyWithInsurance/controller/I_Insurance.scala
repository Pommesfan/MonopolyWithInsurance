package de.htwg.se.MonopolyWithInsurance.controller

trait I_Insurance {
  def startCost: Int
  def pay_contribution: Int
  def absorbTax(taxAmount:Int, rolledEyes:Int): Int
}
