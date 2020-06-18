package de.htwg.se.Monopoly.controller

import de.htwg.se.Monopoly.controller.GameStatus._
import de.htwg.se.Monopoly.model._
import de.htwg.se.Monopoly.util.{Observable, UndoManager}

import scala.collection.mutable

class Controller(var board: Board, var players: Vector[Player] = Vector()) extends Observable{

  var gameStatus: GameStatus = IDLE
  private val undoManager = new UndoManager
  var currentPlayerIndex: Int = 0
  var actualField: Field = SpecialField(0, "Los")

  def setPlayers(player: Vector[Player]): Unit = {
    gameStatus = NEXT_PLAYER
    players = player
    notifyObservers
  }

  def rollDicePrivate(): Unit = {
    val firstRolledNumber = Dice().roll
    val secondRolledNumber = Dice().roll
    if (firstRolledNumber == secondRolledNumber) {
      gameStatus = DOUBLETS
    } else {
      gameStatus = ROLLED
    }
    movePlayer(firstRolledNumber + secondRolledNumber)
  }

  def movePlayer(rolledEyes: Int): Field = {
    val actualPlayer = players(currentPlayerIndex)
    if (actualPlayer.inJail != 0) {
      decrementJailCounter(actualPlayer)
    } else {
      setPlayer(actualPlayer, rolledEyes)
    }
  }

  def decrementJailCounter(p: Player): Field = {
    players = players.updated(p.index, p.decrementJailCounter())
    board.fields(p.currentPosition)
  }

  def setPlayer(p: Player, n: Int): Field = {
    players = players.updated(p.index, p.setPosition(p.currentPosition + n))
    val  (field, gameState) = board.move(players(currentPlayerIndex),
      players(currentPlayerIndex).currentPosition)
    actualField = field
    print("\n You landed on " + field + "\n")
    handleNewPosition(gameState, field)
    field
  }

  def handleNewPosition(status: GameStatus, field: Field): Unit = {
    gameStatus = NEXT_PLAYER
    field match {
      case s: Street =>
        if (status == ALREADY_OWNED) {
          nextPlayer()
        } else if (status == CAN_BE_BOUGHT) {
          gameStatus = status
          notifyObservers
        } else if (status == OWNED_BY_OTHER_PLAYER) {
          players = players.updated(currentPlayerIndex, players(currentPlayerIndex).decrementMoney(s.rent))
          players = players.updated(s.owner.index, s.owner.incrementMoney(s.rent))
          nextPlayer()
        }
      case c: ChanceCard =>
        players = players.updated(currentPlayerIndex, players(currentPlayerIndex).incrementMoney(c.getMoney))
        if (c.otherPlayerIndex != -1) {
          players = players.updated(c.otherPlayerIndex, players(c.otherPlayerIndex).incrementMoney(c.giveMoney))
        }
        nextPlayer()
      case sp: SpecialField =>
        if(status == GO_TO_JAIL) {
          players = players.updated(currentPlayerIndex, players(currentPlayerIndex).goToJail())
        }
        nextPlayer()
      case t: Tax =>
        players = players.updated(currentPlayerIndex, players(currentPlayerIndex).decrementMoney(t.taxAmount))
        nextPlayer()
    }
  }


  def nextPlayer(): Unit = {
    if (currentPlayerIndex + 1 < players.length) {
      currentPlayerIndex = currentPlayerIndex + 1} else {currentPlayerIndex = 0}
    gameStatus = NEXT_PLAYER
    notifyObservers
  }

  def buyStreet(): Unit =  {
    actualField match {
      case s: Street =>
        players = players.updated(currentPlayerIndex, players(currentPlayerIndex).decrementMoney(s.price))
        val street = Street(s.index, s.name, s.neighbourhoodTypes, s.price, s.rent,
          players(currentPlayerIndex))
        board = Board(board.fields.updated(s.index, street))
    }
    nextPlayer()
    notifyObservers
  }

  def rollDice(): Unit = {
    undoManager.doStep(new RollDiceCommand(this))
  }

  def buyField(): Unit = {
    undoManager.doStep(new BuyCommand(this))
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
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
