package de.htwg.se.Monopoly.model

import de.htwg.se.Monopoly.controller.GameStatus
import de.htwg.se.Monopoly.controller.GameStatus.GameStatus

import scala.util.Random

case class ChanceCard(override val index: Int, override val name: String,
                      getMoney: Int, giveMoney: Int, otherPlayerIndex: Int) extends Field(index, name) {

  val BANK_MONEY_BONUS = 100
  val PRETTY_BONUS = 50
  val GIVE_BONUS = 30

  override def actOnPlayer(player: Player): (ChanceCard, GameStatus) = generateRandomCard(player)

  def generateRandomCard(player: Player): (ChanceCard, GameStatus) = {
    val list = Random.shuffle(listOfChanceCard())
    val randomChanceCard: Function[Player, ChanceCard]  = list(0)
    (randomChanceCard(player), GameStatus.CHANCE_CARD)
  }

  def listOfChanceCard(): List[Function[Player, ChanceCard]] = List[Function[Player, ChanceCard]](
    bankIsGivingMoney,
    youArePrettyGivingBonus,
    giveAmountToOtherPlayers
  )

  def bankIsGivingMoney(player: Player): ChanceCard = {
    print("The Bank is giving you 100. \nKeep it safe!")
    ChanceCard(index, name, BANK_MONEY_BONUS, 0, -1)
  }

  def youArePrettyGivingBonus(player: Player): ChanceCard = {
    print("You have won a \nfashion contest. Receive 50.")
    ChanceCard(index, name, PRETTY_BONUS, 0, -1)
  }

  def giveAmountToOtherPlayers(player: Player): ChanceCard = {
    val playerIndex = (player. index + 1) % 2
    print("Gib dem anderen Spieler 30")
    ChanceCard(index, name, (- GIVE_BONUS), GIVE_BONUS, playerIndex)
  }
  override def toString: String = {
    "%d: %s, Du bekommst/musst Zahlen: %s$, an %s$".format(index, name, getMoney, giveMoney)
    }
}
