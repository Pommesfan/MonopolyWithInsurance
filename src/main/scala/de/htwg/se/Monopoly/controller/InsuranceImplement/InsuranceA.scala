package de.htwg.se.Monopoly.controller.InsuranceImplement

import de.htwg.se.Monopoly.controller.I_Insurance

class InsuranceA extends I_Insurance{
  override def pay_contribution: Int = 10
  override def absorbTax(taxAmount:Int, rolledEyes:Int): Int = if(rolledEyes % 2 == 0) taxAmount else taxAmount / 2
}
