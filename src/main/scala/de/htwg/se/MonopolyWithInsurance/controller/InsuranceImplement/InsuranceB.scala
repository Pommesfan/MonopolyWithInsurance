package de.htwg.se.MonopolyWithInsurance.controller.InsuranceImplement

import de.htwg.se.MonopolyWithInsurance.controller.I_Insurance

class InsuranceB extends I_Insurance{
  override def startCost: Int = 20
  override def pay_contribution: Int = 12
  override def absorbTax(taxAmount:Int, rolledEyes:Int): Int = taxAmount / 2
  override def numberOfInsurance(): Int = 2
  override def absorbPenalty(): Unit = ???
  override def preventJail(dice: Int): Option[Int] = Some(if(dice % 2 == 0) 0 else 10)
  override def riskLoadingChancecard(): Option[Int] = Some(2)
}
