package de.htwg.se.Monopoly.model

case class Player(name: String,
                  override val index: Int,
                  override val currentPosition: Int = 0,
                  override val inJail: Boolean = false,
                  override val money: Int = Variable.INITIAL_PLAYER_MONEY) extends IPlayer {

   def this(name: String) = this(name, 0, 0, false, Variable.INITIAL_PLAYER_MONEY)

   val streets: List[Street] = List[Street]()

   override def setPosition(newPosition: Int): Player = {
      if (newPosition < 0) {
         val newCurrentPosition = newPosition + Variable.TOTAL_NUMBER_OF_FIELDS + 1
         copy(currentPosition = newCurrentPosition)
      } else {
         if (newPosition >= Variable.TOTAL_NUMBER_OF_FIELDS) {
            val newCurrentPosition = newPosition - Variable.TOTAL_NUMBER_OF_FIELDS
            val newMoney = money + Variable.MONEY_NEW_ROUND
            copy(currentPosition = newCurrentPosition, money = newMoney)
         } else {
            copy(currentPosition = newPosition)
         }
      }
   }

   def incrementMoney(amount: Int): Player = copy(money = money + amount)
   def decrementMoney(amount: Int): Player = copy(money = money - amount)
   override def toString:String = name
}

