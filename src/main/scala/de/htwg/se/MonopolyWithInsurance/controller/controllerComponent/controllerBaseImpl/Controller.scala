package de.htwg.se.MonopolyWithInsurance.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.htwg.se.MonopolyWithInsurance.MonopolyModule
import de.htwg.se.MonopolyWithInsurance.controller.InsuranceImplement.{InsuranceA, InsuranceB}
import de.htwg.se.MonopolyWithInsurance.controller._
import de.htwg.se.MonopolyWithInsurance.model.Variable
import de.htwg.se.MonopolyWithInsurance.model.boardComponent.{IBoard, boardBaseImpl}
import de.htwg.se.MonopolyWithInsurance.model.boardComponent.boardBaseImpl.Board
import de.htwg.se.MonopolyWithInsurance.model.fieldComponent.IField
import de.htwg.se.MonopolyWithInsurance.model.fieldComponent.fieldBaseImpl.{ChanceCard, SpecialField, Street, Tax}
import de.htwg.se.MonopolyWithInsurance.model.playerComponent.IPlayer
import de.htwg.se.MonopolyWithInsurance.model.playerComponent.playerBaseImpl.{NewPlayerFactoryMethod, Player}
import de.htwg.se.MonopolyWithInsurance.util.UndoManager
import de.htwg.se.MonopolyWithInsurance.model.fieldComponent.fieldBaseImpl
import de.htwg.se.MonopolyWithInsurance.model.fileIOComonent.IFileIO

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.swing.Publisher

class Controller(var board: IBoard, var players: Vector[IPlayer]) extends IController with Publisher {

  private val undoManager = new UndoManager
  var undoJail = false
  var currentPlayerIndex: Int = 0
  var actualField: IField = SpecialField(0, "Los")
  var context = new Context()
  var rolledNumber: (Int, Int) = (0, 0)
  var history: Vector[String] = Vector[String]()
  val injector = Guice.createInjector(new MonopolyModule)
  val fileIo = injector.getInstance(classOf[IFileIO])

  @Inject()
  def this() = {
    this(Board(Variable.START_BOARD), Vector[Player]())
    publish(new NewGameEvent)
  }

  def setPlayers(list: Array[String]): Unit = {
    var player = new ListBuffer[Player]()
    var num = 0
    for (i <- list if i != "p" if num < 8) {
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

  def getActualPlayer: IPlayer = {
    players(currentPlayerIndex)
  }

  def movePlayer(rolledEyes: Int): Unit = {
    val actualPlayer = getActualPlayer
    undoJail = false
    if (actualPlayer.inJail != 0 & actualPlayer.pasch == 0) {
      decrementJailCounter(actualPlayer)
      publish(new WaitForNextPlayer)
    } else if (actualPlayer.inJail == 0) {
      setPlayer(actualPlayer, rolledEyes)
    } else {
      players = players.updated(actualPlayer.index, actualPlayer.setJailCounterZero())
      undoJail = true
      context.nextPlayer()
      setPlayer(getActualPlayer, rolledEyes)
    }
  }

  def decrementJailCounter(p: IPlayer): Unit = {
    players = players.updated(p.index, p.decrementJailCounter())
    board.fields(p.currentPosition)
    context.nextPlayer()
    publish(DecrementJailCounter(players(currentPlayerIndex).inJail))
  }

  def payToLeaveJail(p: IPlayer): Unit = {
    context.nextPlayer()
    players = players.updated(p.index, p.setJailCounterZero().decrementMoney(50))
    rollDice()
  }

  def gameOver(player: IPlayer): Boolean = {
    if (player.money > 0) {
      return true
    }
    context.gameOver(this)
    calculateAssets(player)
    publish(new GameOver)
    false
  }

  def calculateAssets(player: IPlayer): Unit = {
    for (field <- board.fields){
      field match {
        case s: Street =>
          if (s.owner != null & !player.equals(s.owner)) {
            val owner: IPlayer = players(s.owner.index)
            players = players.updated(owner.index, owner.incrementMoney(s.price))
          }
        case _ =>
      }
    }
    players = players.sortWith(_.money > _.money)
  }

  def setPlayer(p: IPlayer, n: Int): IField = {
    players = players.updated(p.index, p.setPosition(p.currentPosition + n))
    val field = board.getField(players(currentPlayerIndex),
      players(currentPlayerIndex).currentPosition)
    actualField = field
    context.rollDice(this)
    publish(new LandedOnField)
    field match {
      case s: Street => handleStreet(s)
      case c: ChanceCard => handleChanceCard(c)
      case sp: SpecialField => handleSpecialField(sp)
      case t: Tax => handleTax(t, n)
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
        players = players.updated(s.owner.index, players(s.owner.index).incrementMoney(rent))
        if (!gameOver(players(currentPlayerIndex))) {
          return
        }
        context.nextPlayer()
        publish(new WaitForNextPlayer)
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

  def handleTax(t: Tax, rolledEyes:Int = -1): Unit = {
    context.state match {
      case _ =>
        if(players(currentPlayerIndex).insurance.nonEmpty && rolledEyes > 0) {
          val insurance = players(currentPlayerIndex).insurance.get
          val amount = insurance.absorbTax(t.taxAmount, rolledEyes)
          players = players.updated(currentPlayerIndex, players(currentPlayerIndex).incrementMoney(amount))
          publish(InsurancePays(amount))
        }
        players = players.updated(currentPlayerIndex, players(currentPlayerIndex).decrementMoney(t.taxAmount))
        if (!gameOver(players(currentPlayerIndex))) {
          return
        }
        publish(new WaitForNextPlayer)
    }
  }

  def nextPlayer(): Unit = {
    if (currentPlayerIndex + 1 < players.length) {
      currentPlayerIndex = currentPlayerIndex + 1
    } else {
      currentPlayerIndex = 0
    }
    actualField = board.fields(players(currentPlayerIndex).currentPosition)
    context.nextPlayer()
    publish(new NextPlayer)
    if(getActualPlayer.inJail != 0) {
      publish(new PayToLeave)
      context.payForJail(this)
    }
  }

  def ownAllFieldsOfType(field: IField): Boolean = {
    var ownAllStreetTypes = true
    field match {
      case street: Street =>
        val sameNeighbourhood = board.getFieldsSameNeighbourhoodType(street.neighbourhoodTypes)
        val owner = getActualPlayer
        for (s <- sameNeighbourhood if ownAllStreetTypes; if !street.equals(s)) {
          s match {
            case street: Street =>
              if (street.owner == null | owner.equals(street.owner)) {
                ownAllStreetTypes = false
              }
          }
        }
        for (s <- sameNeighbourhood if ownAllStreetTypes) {
          s match {
            case street: Street =>
              val newStreet = fieldBaseImpl.Street(street.index, street.name, street.neighbourhoodTypes, street.price, street.rent * 2, owner)
              board = Board(board.fields.updated(s.index, newStreet))
          }
        }
    }
    ownAllStreetTypes
  }

  def buyStreet(): Unit = {
    actualField match {
      case s: Street =>
        if (players(currentPlayerIndex).money - s.price < 0){
          publish(new NotEnoughMoney)
        } else {
          players = players.updated(currentPlayerIndex, players(currentPlayerIndex).decrementMoney(s.price))
          val street = fieldBaseImpl.Street(s.index, s.name, s.neighbourhoodTypes, s.price, s.rent, players(currentPlayerIndex))
          board = boardBaseImpl.Board(board.fields.updated(s.index, street))
          ownAllFieldsOfType(s)
          publish(new BoughtStreet)
        }
    }
    context.nextPlayer()
    publish(new WaitForNextPlayer)
  }

  override def save(): Unit = {
    fileIo.save(this)
    publish(new SaveEvent)
  }

  override def load(): Unit = {
    val c = fileIo.load()
    this.board = c.board
    this.players = c.players
    this.currentPlayerIndex = c.currentPlayerIndex
    this.actualField = c.actualField
    this.rolledNumber = c.rolledNumber
    publish(new LoadEvent)
  }

  def undo(): Unit = {
    undoManager.undoStep
    publish(new UndoEvent)
  }

  def redo(): Unit = {
    undoManager.redoStep
    publish(new RedoEvent)
    publish(new WaitForNextPlayer)
  }

  def exit(): Unit = {
    publish(new ExitGame)
  }

  def gameToString(): String = {
    val string = new mutable.StringBuilder("")
    string ++= board.toString
    string ++= "\nPlayers:\n%-6s %-25s %-10s %-5s\n".format("index", "name", "money", "position")
    for (p <- players) {
      string ++= "%-6s %-25s %-10s %-5s\n".format(p.index, p.name, p.money, p.currentPosition)
    }
    string ++= actualField.toString
    string.toString()
  }

  def setInsurance(idx:Int): Unit = if(players(currentPlayerIndex).insurance == None) {
    val insurance = idx match {
      case 1 => new InsuranceA
      case 2 => new InsuranceB
    }
    players = players.updated(currentPlayerIndex, players(currentPlayerIndex).setInsurance(insurance).decrementMoney(insurance.startCost))
    publish(SignInsurance(insurance.startCost))
  }

  def resignInsurance: Unit =  players = players.updated(currentPlayerIndex, players(currentPlayerIndex).removeInsurance)
}
