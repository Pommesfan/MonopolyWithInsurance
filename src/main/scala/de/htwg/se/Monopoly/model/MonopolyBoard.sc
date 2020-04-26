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

/****Field****/
trait IField{
  val index: Int
  val name: String

  def actOnPlayer(player: Player): String
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

  val streets = List[Street]()

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

val player2 = Player("Yvonne", 0, 0, false, 5)
player2.money


/********** Enums ********/

object NeighbourhoodTypes extends Enumeration {
  val Brown, Blue, Pink, Orange, Red, Yellow, Green, Purple, Station, Utility = Value
}

val Neighbourhood = NeighbourhoodTypes.values

/********** Board ********/

abstract case class Board() {
  val players: List[Player] = List(Player("name1", 0), Player("Player2", 1))
  val currentPlayerIndex: Int = 0

  //def initializeBoard(namePlayer: List[String]): Unit = {
  val allfields = List[Field](
    SpecialField(0, "Los"),
    Street(1, "Badstrasse", NeighbourhoodTypes.Brown, 60, 2),
    ChanceCard(2, "Gemeinschaftsfeld"),
    Street(3, "Turmstrasse", NeighbourhoodTypes.Brown, 60, 4),
    Tax(4, "Einkommenssteuer", 200),
    Street(5, "Südbahnhof", NeighbourhoodTypes.Station, 200, 25),
    Street(6, "Chausseestrasse", NeighbourhoodTypes.Blue, 100, 6),
    ChanceCard(7, "Ereignisfeld"),
    Street(8, "Elisenstrasse", NeighbourhoodTypes.Blue, 100, 6),
    Street(9, "Poststrasse", NeighbourhoodTypes.Blue, 120, 8),
    SpecialField(10, "Gefägnis"),
    Street(11, "Seestraße", NeighbourhoodTypes.Pink, 140, 10),
    Street(12, "Elektrizitätswerk", NeighbourhoodTypes.Utility, 150, 0),
    Street(13, "Hafenstrasse", NeighbourhoodTypes.Pink, 140, 10),
    Street(14, "Neuestrasse", NeighbourhoodTypes.Pink, 160, 12),
    Street(15, "Westbahnhof", NeighbourhoodTypes.Station, 200, 25),
    Street(16, "Münchner Strasse", NeighbourhoodTypes.Orange, 180, 14),
    ChanceCard(17, "Gemeinschaftsfeld"),
    Street(18, "Wiener Strasse", NeighbourhoodTypes.Orange, 180, 14),
    Street(19, "Berliner Strasse", NeighbourhoodTypes.Orange, 200, 16),
    SpecialField(20, "Frei Parken"),
    Street(21, "Theaterstrasse", NeighbourhoodTypes.Red, 220, 18),
    ChanceCard(22, "Ereignisfeld"),
    Street(23, "Museumstrasse", NeighbourhoodTypes.Red, 220,18),
    Street(24, "Opernplatz", NeighbourhoodTypes.Red, 240, 20),
    Street(25, "Nordbahnhof", NeighbourhoodTypes.Station, 200, 25),
    Street(26, "Lessingstrasse", NeighbourhoodTypes.Yellow, 260, 22),
    Street(27, "Schillerstrasse0", NeighbourhoodTypes.Yellow, 260, 22),
    Street(28, "Elektrizitätswerk", NeighbourhoodTypes.Utility, 150, 0),
    Street(29, "Goethestrasse", NeighbourhoodTypes.Yellow, 280, 24),
    Street(30, "Rathhausplatz", NeighbourhoodTypes.Green, 300, 26),
    Street(31, "Hauptstrasse", NeighbourhoodTypes.Green, 300, 26),
    ChanceCard(32, "Gemeinschaftsfeld"),
    Street(33, "Bahnhofstrasse", NeighbourhoodTypes.Green, 320, 28),
    Street(34, "Hauptbahnhof", NeighbourhoodTypes.Station, 200, 25),
    ChanceCard(35, "Ereignisfeld"),
    Street(36, "Parkstrasse", NeighbourhoodTypes.Purple, 350, 35),
    Tax(37, "Zusatzsteuer", 100),
    Street(38, "Schlossallee", NeighbourhoodTypes.Purple, 400, 50)
  )
}

val board1 = Board()

trait IField{
  val name: String
  val index: Int
  def ActOnPlayer(player: Player)
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
    Board().players[Player](Board().currentPlayerIndex).decrementMoney(30);
    Board().players[Player]((Board().currentPlayerIndex + 1) % 2).incrementMoney(30)
    "The other player is smarter. \nGive him 30."
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
      String.format("%s\n is owned by %s.\nYou paid him %d", name, player.index, rent)
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
