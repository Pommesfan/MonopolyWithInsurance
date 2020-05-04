package de.htwg.se.Monopoly.model

import scala.util.Random

case class ChanceCardGenerator(){
  val GO_POSITION = 0
  val MAYFAIR_POSITION = 39
  val BANK_MONEY_BONUS = 100
  val PRETTY_BONUS = 50

  def listOfChanceCard(): List[Function[Player, String]] = List[Function[Player, String]](
    bankIsGivingMoney,
    youArePrettyGivingBonus,
    giveAmountToOtherPlayers
  )

  def bankIsGivingMoney(player: Player): String = {
    player.incrementMoney(BANK_MONEY_BONUS)
    "The Bank is giving you 100. \nKeep it safe!"
  }

  def youArePrettyGivingBonus(player: Player): String = {
    player.incrementMoney(PRETTY_BONUS)
    "You have won a \nfashion contest. Receive 50."
  }

  def giveAmountToOtherPlayers(player: Player): String = {
    val board = new Board
    board.players(player.index).decrementMoney(30)
    board.players((player.index + 1) % 2).incrementMoney(30)
    "The other player is smarter. \nGive him 30."
  }

  def generateRandomCard(player: Player): String = {
    val list = Random.shuffle(listOfChanceCard())
    val randomChanceCard: Function[Player, String]  = list(0)
    randomChanceCard(player)
  }
}
