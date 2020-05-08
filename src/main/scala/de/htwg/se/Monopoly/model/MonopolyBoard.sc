import scala.util.Random
//new ModelWorksheet, because the other one is for testing new structures

/********** Interfaces ********/

/****Player****/
trait IPlayer{
  val currentPosition: Int
  val index: Int
  val inJail: Boolean
  val money: Int

  def setPosition(newPosition: Int): Unit
}

/********** Player ********/
val INITIAL_PLAYER_MONEY = 1500
val TOTAL_NUMBER_OF_FIELDS = 40
val MONEY_NEW_ROUND = 200

case class Player(name: String,
                  override val index: Int = 0,
                  override val currentPosition: Int = 0,
                  override val inJail: Boolean = false,
                  override val money: Int = INITIAL_PLAYER_MONEY) extends IPlayer {

  //def this(name: String) = this(name, 0, 0, false, INITIAL_PLAYER_MONEY)

 // val streets = List[Street]()

  override def setPosition(newPosition: Int): Unit = {
    if (newPosition < 0) {
      val newCurrentPosition = newPosition + TOTAL_NUMBER_OF_FIELDS
      if (newCurrentPosition >= TOTAL_NUMBER_OF_FIELDS) {
        val newCurrentPosition = newPosition - TOTAL_NUMBER_OF_FIELDS
        copy(currentPosition = newCurrentPosition)
      } else {
        copy(currentPosition = newCurrentPosition)
      }
    } else {
      val newCurrentPosition = newPosition
      if (newCurrentPosition >= TOTAL_NUMBER_OF_FIELDS) {
        val newCurrentPosition = newPosition - TOTAL_NUMBER_OF_FIELDS
        val newMoney = money + MONEY_NEW_ROUND
        copy(currentPosition = newCurrentPosition, money = newMoney)
      } else {
        copy(currentPosition = newCurrentPosition)
      }
    }
  }
  def incrementMoney(amount: Int): Player = copy(money = money + amount)
  def decrementMoney(amount: Int): Player = copy(money = money + amount)
  override def toString:String = name
}

val player1 = Player("Yvonne")
player1.money

val player2 = new Player(name = "Yvonne", index =0, currentPosition = 0, inJail = false, money = 5)
player2.money


/********** Enums ********/

object NeighbourhoodTypes extends Enumeration {
  val Brown, Blue, Pink, Orange, Red, Yellow, Green, Purple, Station, Utility = Value
}

val Neighbourhood = NeighbourhoodTypes.values


trait IField{
  val name: String
  val index: Int
  def actOnPlayer(player: Player): String
}

/**** Field ****/
abstract class Field( override val index: Int, override val name: String) extends IField {
  override def actOnPlayer(player: Player): String
}

/**** ChanceCard ****/

case class ChanceCardGenerator(){
  val GO_POSITION = 0
  val MAYFAIR_POSITION = 39
  val BANK_MONEY_BONUS = 100
  val PRETTY_BONUS = 50

  def listOfChanceCard(): List[Function[Player, String]] = List[Function[Player, String]](
    bankIsGivingMoney,
    youArePrettyGivingBonus,
   // giveAmountToOtherPlayers
  )

  def bankIsGivingMoney(player: Player): String = {
    player.incrementMoney(BANK_MONEY_BONUS)
    "The Bank is giving you 100. \nKeep it safe!"
  }

  def youArePrettyGivingBonus(player: Player): String = {
    player.incrementMoney(PRETTY_BONUS)
    "You have won a \nfashion contest. Receive 50."
  }



  def generateRandomCard(player: Player): String = {
    val list = Random.shuffle(listOfChanceCard())
    val randomChanceCard: Function[Player, String]  = list(0)
    randomChanceCard(player)
  }
}

case class ChanceCard(override val index: Int, override val name: String) extends Field(index, name) {
  override def actOnPlayer(player: Player): String = ChanceCardGenerator().generateRandomCard(player)
}

/**** SpecialField ****/

case class SpecialField(override val index: Int, override val name: String) extends Field(index, name){
  override def actOnPlayer(player: Player): String = {
    if (index == 0){
      player.incrementMoney(MONEY_NEW_ROUND)
      "You landed on Go \nCollect 200!"
    } else if (index == 10) {
      "You are visiting your \ndear friend in Jails."
    } else if (index == 20) {
      "You landed on Free Parking. \nNothing happens."
    } else {
      "You are in jail! \nYou will skip the next three turns"
    }
  }
}

/**** Street ****/
case class Street(override val index: Int,
                  override val name: String,
                  private val neighbourhoodTypes: NeighbourhoodTypes.Value,
                  private val price: Int,
                  rent: Int,
                  owner: Player = null) extends Field(index, name){
  override def actOnPlayer(player: Player): String = {
    if (owner == player){
      "You already own " + Street.this.name + "."
    } else if (owner == null){
      Street.this.name + " is available \nfor purchase."
    } else {
      player.decrementMoney(rent)
      owner.incrementMoney(rent)
      "%s\n is owned by %s.\nYou paid him %d.".format(name, owner.name, rent)
    }
  }
}

/**** Tax ****/
case class Tax(override val index: Int, override val name: String, taxAmount: Int) extends Field(index, name){
  override def actOnPlayer(player: Player): String = {
    val taxAmountTotal = player.decrementMoney(taxAmount)
    name + ": " + taxAmountTotal
  }
}


/********** Board ********/

case class Board() {
  val players: List[Player] = List(Player("name1", 0), Player("Player2", 1))
  val currentPlayerIndex: Int = 0
  //val fields = allfields

  //def initializeBoard(namePlayer: List[String]): Unit = {

}

val board1 = Board()