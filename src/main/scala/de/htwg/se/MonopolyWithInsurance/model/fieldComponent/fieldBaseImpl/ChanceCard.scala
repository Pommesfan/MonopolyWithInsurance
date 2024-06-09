package de.htwg.se.MonopolyWithInsurance.model.fieldComponent.fieldBaseImpl

import de.htwg.se.MonopolyWithInsurance.model.playerComponent.IPlayer

import scala.util.Random

case class ChanceCard(override val index: Int, override val name: String, cardIndex: Int,
                      getMoney: Int, giveMoney: Int, otherPlayerIndex: Int, info: String = "") extends Field(index, name) {

  val BANK_MONEY_BONUS = 100
  val PRETTY_BONUS = 50
  val GIVE_BONUS = 30

  override def actOnPlayer(player: IPlayer): ChanceCard = generateRandomCard(player)

  def generateRandomCard(player: IPlayer): ChanceCard = {
    val list = Random.shuffle(listOfChanceCard())
    val randomChanceCard: Function[IPlayer, ChanceCard]  = list.head
    randomChanceCard(player)
  }

  def listOfChanceCard(): List[Function[IPlayer, ChanceCard]] = List[Function[IPlayer, ChanceCard]](
    bankIsGivingMoney,
    youArePrettyGivingBonus,
    giveAmountToOtherPlayers,
    goToJail
  )

  def bankIsGivingMoney(player: IPlayer): ChanceCard = {
    val message = "Ereigniskarte: Die Bank gibt dir 100$.\n"
    ChanceCard(index, name, 1, BANK_MONEY_BONUS, 0, -1, message)
  }

  def youArePrettyGivingBonus(player: IPlayer): ChanceCard = {
    val message = "Ereigniskarte: Du hast eine Fashion-Wettbewerb gewonnen.\n Erhalte 50$.\n"
    ChanceCard(index, name, 2,  PRETTY_BONUS, 0, -1, message)
  }

  def giveAmountToOtherPlayers(player: IPlayer): ChanceCard = {
    val playerIndex = (player. index + 1) % 2
    val message = "Ereigniskarte: Gib dem anderen Spieler 30.\n"
    ChanceCard(index, name, 3, - GIVE_BONUS, GIVE_BONUS, playerIndex, message)
  }

  def goToJail(player: IPlayer): ChanceCard = {
    val message = "Ereigniskarte: Gehe ins Gefängnis!\n"
    ChanceCard(index, name, 4, 0, 0, -1, message)
  }

  override def toString: String = {
    if( otherPlayerIndex != -1) {
      "%d: %s, Du bekommst/musst Zahlen: %s$, an %s.\n".format(index, name, getMoney, otherPlayerIndex)
    } else if (cardIndex == 4) {
      "%d: %s, Gehe ins Gefängnis.\n".format(index, name)
    } else {
      "%d: %s, Du bekommst %s$.\n".format(index, name, getMoney)
    }
  }
}
