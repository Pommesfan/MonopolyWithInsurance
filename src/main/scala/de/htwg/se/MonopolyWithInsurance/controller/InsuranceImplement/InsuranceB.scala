package de.htwg.se.MonopolyWithInsurance.controller.InsuranceImplement

import de.htwg.se.MonopolyWithInsurance.controller.I_Insurance

class InsuranceB extends I_Insurance{
  override def startCost: Int = 20
  override def pay_contribution: Int = 12
  override def absorbTax(taxAmount:Int, rolledEyes:Int): Int = taxAmount / 2
  override def numberOfInsurance(): Int = 2
}
