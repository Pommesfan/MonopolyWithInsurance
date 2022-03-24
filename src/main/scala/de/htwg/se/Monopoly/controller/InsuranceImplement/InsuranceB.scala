package de.htwg.se.Monopoly.controller.InsuranceImplement

import de.htwg.se.Monopoly.controller.I_Insurance

class InsuranceB extends I_Insurance{
  override def pay_contribution: Int = 12
  override def absorbTax(taxAmount:Int, rolledEyes:Int): Int = taxAmount / 2
}
