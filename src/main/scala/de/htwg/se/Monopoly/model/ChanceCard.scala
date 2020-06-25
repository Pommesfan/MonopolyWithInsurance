package de.htwg.se.Monopoly.model

import scala.util.Random

case class ChanceCard(override val index: Int, override val name: String, cardIndex: Int,
                      getMoney: Int, giveMoney: Int, otherPlayerIndex: Int) extends Field(index, name) {

  val BANK_MONEY_BONUS = 100
  val PRETTY_BONUS = 50
  val GIVE_BONUS = 30

  override def actOnPlayer(player: Player): ChanceCard = generateRandomCard(player)

  def generateRandomCard(player: Player): ChanceCard = {
    val list = Random.shuffle(listOfChanceCard())
    val randomChanceCard: Function[Player, ChanceCard]  = list.head
    randomChanceCard(player)
  }

  def listOfChanceCard(): List[Function[Player, ChanceCard]] = List[Function[Player, ChanceCard]](
    // bankIsGivingMoney,
    // youArePrettyGivingBonus,
    //  giveAmountToOtherPlayers,
    goToJail
  )

  def bankIsGivingMoney(player: Player): ChanceCard = {
    print("The Bank is giving you 100. \nKeep it safe!\n")
    ChanceCard(index, name, 1, BANK_MONEY_BONUS, 0, -1)
  }

  def youArePrettyGivingBonus(player: Player): ChanceCard = {
    print("You have won a \nfashion contest. Receive 50.\n")
    ChanceCard(index, name, 2,  PRETTY_BONUS, 0, -1)
  }

  def giveAmountToOtherPlayers(player: Player): ChanceCard = {
    val playerIndex = (player. index + 1) % 2
    print("Gib dem anderen Spieler 30\n")
    ChanceCard(index, name, 3, - GIVE_BONUS, GIVE_BONUS, playerIndex)
  }

  def goToJail(player: Player): ChanceCard = {
    print("Gehe ins Gefängnis!\n")
    ChanceCard(index, name, 4, 0, 0, -1)
  }

  override def toString: String = {
    if( otherPlayerIndex != -1) {
      "%d: %s, Du bekommst/musst Zahlen: %s$, an %s.\n".format(index, name, getMoney, otherPlayerIndex)
    } else {
      "%d: %s, Du musst %s$ Zahlen.\n".format(index, name, getMoney)
    }
  }
}
