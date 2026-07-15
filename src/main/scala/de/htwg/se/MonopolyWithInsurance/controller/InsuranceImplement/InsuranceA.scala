package de.htwg.se.MonopolyWithInsurance.controller.InsuranceImplement

import de.htwg.se.MonopolyWithInsurance.controller.I_Insurance

class InsuranceA extends I_Insurance{
  override def startCost: Int = 12
  override def pay_contribution: Int = 10
  override def absorbTax(taxAmount:Int, rolledEyes:Int): Int = if(rolledEyes % 2 == 0) taxAmount else taxAmount / 2
  override def numberOfInsurance(): Int = 1
  override def absorbPenalty(): Unit = ???
  override def preventJail(dice: Int): Option[Int] = None
  override def riskLoadingChancecard(): Option[Int] = Some(4)
}
