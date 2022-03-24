package de.htwg.se.Monopoly.controller

trait I_Insurance {
  def pay_contribution: Int
  def absorbTax(taxAmount:Int, rolledEyes:Int): Int
}
