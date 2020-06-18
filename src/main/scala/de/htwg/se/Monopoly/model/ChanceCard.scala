package de.htwg.se.Monopoly.model

import scala.util.Random

case class ChanceCard(override val index: Int, override val name: String) extends Field(index, name) {

  val GO_POSITION = 0
  val MAYFAIR_POSITION = 39
  val BANK_MONEY_BONUS = 100
  val PRETTY_BONUS = 50

  override def actOnPlayer(player: Player, players: List[Player]): (ChanceCard, List[Player]) = generateRandomCard(player, players)

  def generateRandomCard(player: Player, players: List[Player]): (ChanceCard, List[Player])  = {
    val list = Random.shuffle(listOfChanceCard())
    val randomChanceCard: (Player, List[Player]) => (ChanceCard, List[Player])  = list(0)
    randomChanceCard(player, players)
  }

  def listOfChanceCard(): List[(Player, List[Player]) => (ChanceCard, List[Player])] =
    List(
      bankIsGivingMoney,
      youArePrettyGivingBonus,
      giveAmountToOtherPlayers
    )

  def bankIsGivingMoney: (Player, List[Player]) => (ChanceCard, List[Player]) =
    (player: Player, players: List[Player]) => {
      val newPlayers = players.updated(player.index, player.incrementMoney(BANK_MONEY_BONUS))
      println("The Bank is giving you 100. \nKeep it safe!")
      (ChanceCard(index, name), newPlayers)
    }

  def youArePrettyGivingBonus: (Player, List[Player]) => (ChanceCard, List[Player]) =
    (player: Player, players: List[Player]) => {
      val newPlayers = players.updated(player.index, player.incrementMoney(PRETTY_BONUS))
      println("You have won a \nfashion contest. Receive 50.")
      (ChanceCard(index, name), newPlayers)
    }

  def giveAmountToOtherPlayers: (Player, List[Player]) => (ChanceCard, List[Player]) =
    (player: Player, players: List[Player]) => {
      val playersNew = players.updated(player.index, player.decrementMoney(30))
      val newPlayerIndex = (player. index + 1) % 2
      val finalPlayers = playersNew.updated(newPlayerIndex, players(newPlayerIndex).incrementMoney(30))
      println("Gib dem anderen Spieler 30")
      (ChanceCard(index, name), finalPlayers)
    }
}
