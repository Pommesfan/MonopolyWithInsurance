package de.htwg.se.Monopoly.controller

object GameStatus  extends Enumeration{
  type GameStatus = Value
  val IDLE, NEXT_PLAYER, STREET_IS_AVAILABLE, STREET_IS_NOT_AVAILABLE, ROLLED, DOUBLETS,
  INJAIL, NEW_POSITION, ALREADY_OWNED, CAN_BE_BOUGHT, OWNED_BY_OTHER_PLAYER, CHANCE_CARD,
  LANDED_ON_GO, JAIL_VISIT, FREE_PARKING, GO_TO_JAIL, TAX = Value

  val map = Map[GameStatus, String](
    IDLE -> "Set Player\n",
    NEXT_PLAYER -> "Next Player\n",
    STREET_IS_AVAILABLE -> "Street is available\n",
    STREET_IS_NOT_AVAILABLE -> "Street is owned by another player\n",
    ROLLED -> "Player rolled dice\n",
    DOUBLETS -> "You roll doublets\n",
    INJAIL -> "Player is in Jail\n",
    NEW_POSITION -> "Neue Position\n",
    ALREADY_OWNED -> "Is already owned by yourself\n",
    CAN_BE_BOUGHT -> "Street can be bought\n",
    OWNED_BY_OTHER_PLAYER -> "Owned by another Player\n",
    CHANCE_CARD -> "Get a ChanceCard\n",
    LANDED_ON_GO -> "You landed on Go \nCollect 200!\n",
    JAIL_VISIT -> "You are visiting your \ndear friend in Jails.\n",
    FREE_PARKING -> "You landed on Free Parking. \nNothing happens.\n",
    GO_TO_JAIL -> "You are in jail! \nYou will skip the next three turns\n",
    TAX -> "You have to pay taxes"
  )

  def message(gameStatus: GameStatus): Unit = {
    map(gameStatus)
  }

}