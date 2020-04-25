import scala.util.Random
//new ModelWorksheet, because the other one is for testing new structures

/********** Interfaces ********/

/****Player****/
trait IPlayer{
  val currentPosition: Int
  val index: Int
  val inJail: Boolean
  val money: Int

  def setPosition(newPosition: Int): Player
}

/****Field****/
trait IField{
  val index: Int
  val name: String

  // def actOnPlayer(player: Player)
}

/********** Player ********/
val INITIAL_PLAYER_MONEY = 1500
val TOTAL_NUMBER_OF_FIELDS = 40

case class Player(name: String,
                  override val index: Int = 0,
                  override val currentPosition: Int = 0,
                  override val inJail: Boolean = false,
                  override val money: Int = INITIAL_PLAYER_MONEY) extends IPlayer {

  def this(name: String) = this(name, 0, 0, false, INITIAL_PLAYER_MONEY)
  def setPosition(newPosition: Int): Player = copy(currentPosition = newPosition)
  def incrementMoney(amount: Int): Player = copy(money = money + amount)
  def decrementMoney(amount: Int): Player = copy(money = money + amount)
  override def toString:String = name
}

val player1 = Player("Yvonne")
player1.money

val player2 = Player("Yvonne", 0, 0, false, 5)
player2.money


/********** Enums ********/
