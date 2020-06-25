package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.model._
import de.htwg.se.Monopoly.util.UndoManager

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.swing.Publisher

class Controller(var board: Board, var players: Vector[Player] = Vector()) extends Publisher {

  private val undoManager = new UndoManager
  var currentPlayerIndex: Int = 0
  var actualField: Field = SpecialField(0, "Los")
  var context = new Context()
  var rolledNumber: (Int, Int) = (0, 0)

  def setPlayers(list: Array[String]): Unit = {
    var player = new ListBuffer[Player]()
    var num = 0
    for (i <- list if i != "p") {
      player += NewPlayerFactoryMethod.createNewPlayer(i.toString, num)
      num += 1
    }
    context.setPlayer()
    players = player.toVector
    publish(new PlayerSet)
  }

  def rollDice(): Unit = {
    undoManager.doStep(new RollDiceCommand(this))
  }

  def getActualPlayer(): Player = {
    players(currentPlayerIndex)
  }

  def movePlayer(rolledEyes: Int): Unit = {
    val actualPlayer = getActualPlayer
    if (actualPlayer.inJail != 0 & actualPlayer.pasch == 0) {
      decrementJailCounter(actualPlayer)
      publish(new WaitForNextPlayer)
    } else if (actualPlayer.inJail == 0) {
      setPlayer(actualPlayer, rolledEyes)
    } else {
      players = players.updated(actualPlayer.index, actualPlayer.setJailCounterZero())
      setPlayer(actualPlayer, rolledEyes)
    }
  }

  def decrementJailCounter(p: Player): Unit = {
    players = players.updated(p.index, p.decrementJailCounter())
    nextPlayer()
    board.fields(p.currentPosition)
    context.nextPlayer()
    publish(DecrementJailCounter(players(currentPlayerIndex).inJail))
  }

  def payToLeaveJail(p: Player): Unit = {
    context.nextPlayer()
    players = players.updated(p.index, p.setJailCounterZero().decrementMoney(50))
    movePlayer(rolledNumber._1 + rolledNumber._2)
  }

  def gameOver(player: Player): Boolean = {
    if (player.money > 0) {
      return true
    }
    context.gameOver(this)
    calculateAssets(player)
    publish(new GameOver)
    false
  }

  def calculateAssets(player: Player): Unit = {
    for (field <- board.fields){
      field match {
        case s: Street =>
          if (s.owner != null & !player.equals(s.owner)) {
            val owner: Player = players(s.owner.orNull.index)
            players = players.updated(owner.index, owner.incrementMoney(s.price))
          }
        case _ =>
      }
    }
    players = players.sortWith(_.money > _.money)
  }

  def setPlayer(p: Player, n: Int): Field = {
    players = players.updated(p.index, p.setPosition(p.currentPosition + n))
    val field = board.getField(players(currentPlayerIndex),
      players(currentPlayerIndex).currentPosition)
    actualField = field
    context.rollDice(this)
    print("\n You landed on " + field + "\n")
    field match {
      case s: Street => handleStreet(s)
      case c: ChanceCard => handleChanceCard(c)
      case sp: SpecialField => handleSpecialField(sp)
      case t: Tax => handleTax(t)
    }
    field
  }

  def handleStreet(s: Street): Unit = {
    context.state match {
      case _: NextPlayerState =>
        publish(new OwnStreet)
        publish(new WaitForNextPlayer)
      case _: BuyStreet =>
        publish(new HandleStreet)
      case _: PayOtherPlayer =>

        var rent = s.rent
        if (s.name == "Elektrizitätswerk" || s.name == "Wasserwerk") {
          rent = (rolledNumber._1 + rolledNumber._2) * 4
          if (ownAllFieldsOfType(s)) {
            rent = (rolledNumber._1 + rolledNumber._2) * 10
          }
        }
        publish(MoneyTransaction(rent))
        players = players.updated(currentPlayerIndex, players(currentPlayerIndex).decrementMoney(rent))
        players = players.updated(s.owner.orNull.index, players(s.owner.orNull.index).incrementMoney(rent))
        if (!gameOver(players(currentPlayerIndex))) {
          return
        }
        publish(new WaitForNextPlayer)
      case _: GoToJail =>
        players = players.updated(currentPlayerIndex, players(currentPlayerIndex).goToJail())
        publish(new GoToJailEvent)
      case _ =>
    }
  }

  def handleChanceCard(c: ChanceCard) : Unit = {
    publish(HandleChanceCard(c.info))
    context.state match {
      case _: GoToJail =>
        players = players.updated(currentPlayerIndex, players(currentPlayerIndex).goToJail())
        publish(new GoToJailEvent)
      case _ =>
        players = players.updated(currentPlayerIndex, players(currentPlayerIndex).incrementMoney(c.getMoney))
        if (c.otherPlayerIndex != -1) {
          players = players.updated(c.otherPlayerIndex, players(c.otherPlayerIndex).incrementMoney(c.giveMoney))
        }
        if (!gameOver(players(currentPlayerIndex))) {
          return
        }
        publish(new WaitForNextPlayer)
    }
  }

  def handleSpecialField(sp: SpecialField): Unit = {
    context.state match {
      case _: LandedOnGo => publish(new WaitForNextPlayer)
      case _: VisitJail => publish(new WaitForNextPlayer)
      case _: FreeParking => publish(new WaitForNextPlayer)
      case _: GoToJail =>
        players = players.updated(currentPlayerIndex, players(currentPlayerIndex).goToJail())
        publish(new GoToJailEvent)
    }
  }

  def handleTax(t: Tax): Unit = {
    context.state match {
      case _: GoToJail =>
        players = players.updated(currentPlayerIndex, players(currentPlayerIndex).goToJail())
        publish(new GoToJailEvent)
      case _ =>
        players = players.updated(currentPlayerIndex, players(currentPlayerIndex).decrementMoney(t.taxAmount))
        if (!gameOver(players(currentPlayerIndex))) {
          return
        }
        publish(new WaitForNextPlayer)
    }
  }

  def nextPlayer(): Unit = {
    if (currentPlayerIndex + 1 < players.length) {
      currentPlayerIndex = currentPlayerIndex + 1} else {currentPlayerIndex = 0}
    context.nextPlayer()
    publish(new NextPlayer)
    if(getActualPlayer.inJail != 0) {
      publish(new PayToLeave)
      context.payForJail(this)
    }
  }

  def ownAllFieldsOfType(street: Street): Boolean = {
    val sameNeighbourhood = board.getFieldsSameNeighbourhoodType(street.neighbourhoodTypes)
    val owner = getActualPlayer
    var ownAllStreetTypes = true
    for (s <- sameNeighbourhood if ownAllStreetTypes; if !street.equals(s); if s.owner == null | owner.equals(s.owner)) {
      ownAllStreetTypes = false
    }
    for (s <- sameNeighbourhood if ownAllStreetTypes) {
      val newStreet = Street(s.index, s.name, s.neighbourhoodTypes, s.price, s.rent * 2, Option(owner))
      board = Board(board.fields.updated(s.index, newStreet))
    }
    ownAllStreetTypes
  }

  def buyStreet(): Unit = {
    undoManager.doStep(new BuyCommand(this))
  }

  def undo: Unit = {
    undoManager.undoStep
  //  notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
  //  notifyObservers
  }

  def gameToString: String = {
    val string = new mutable.StringBuilder("")
    string ++= board.toString
    string ++= "\nPlayers:\n%-6s %-25s %-10s %-5s\n".format("index", "name", "money", "position")
    for (p <- players) {
      string ++= "%-6s %-25s %-10s %-5s\n".format(p.index, p.name, p.money, p.currentPosition)
    }
    string.toString()
  }
}
