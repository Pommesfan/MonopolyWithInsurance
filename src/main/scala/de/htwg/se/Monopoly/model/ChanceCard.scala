package de.htwg.se.Monopoly.model

import scala.util.Random

case class ChanceCard(override val index: Int, override val name: String) extends Field(index, name) {

  val GO_POSITION = 0
  val MAYFAIR_POSITION = 39
  val BANK_MONEY_BONUS = 100
  val PRETTY_BONUS = 50

  override def actOnPlayer(player: Player): ChanceCard = generateRandomCard(player)

  def generateRandomCard(player: Player): ChanceCard = {
    val list = Random.shuffle(listOfChanceCard())
    val randomChanceCard: Function[Player, ChanceCard]  = list(0)
    randomChanceCard(player)
  }

  def listOfChanceCard(): List[Function[Player, ChanceCard]] = List[Function[Player, ChanceCard]](
    bankIsGivingMoney,
    youArePrettyGivingBonus,
    //giveAmountToOtherPlayers
  )

  def bankIsGivingMoney(player: Player): ChanceCard = {
    player.incrementMoney(BANK_MONEY_BONUS)
    println("The Bank is giving you 100. \nKeep it safe!")
    ChanceCard(index, name)
  }

  def youArePrettyGivingBonus(player: Player): ChanceCard = {
    player.incrementMoney(PRETTY_BONUS)
    println("You have won a \nfashion contest. Receive 50.")
    ChanceCard(index, name)
  }

  /**
  def giveAmountToOtherPlayers(player: Player, chanceCard: ChanceCard): Field = {
    val board = new Board
    board.players(player.index).decrementMoney(30)
    board.players((player.index + 1) % 2).incrementMoney(30)
    "The other player is smarter. \nGive him 30."
    chanceCard
  }
   */
}
