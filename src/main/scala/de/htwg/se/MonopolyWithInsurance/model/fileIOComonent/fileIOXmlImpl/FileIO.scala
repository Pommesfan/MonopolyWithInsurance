package de.htwg.se.MonopolyWithInsurance.model.fileIOComonent.fileIOXmlImpl

import java.awt.Color

import de.htwg.se.MonopolyWithInsurance.controller.IController
import de.htwg.se.MonopolyWithInsurance.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.MonopolyWithInsurance.model.NeighbourhoodTypes._
import de.htwg.se.MonopolyWithInsurance.model.NeighbourhoodTypes
import de.htwg.se.MonopolyWithInsurance.model.boardComponent.IBoard
import de.htwg.se.MonopolyWithInsurance.model.boardComponent.boardBaseImpl.Board
import de.htwg.se.MonopolyWithInsurance.model.fieldComponent.fieldBaseImpl.{ChanceCard, Field, SpecialField, Street, Tax}
import de.htwg.se.MonopolyWithInsurance.model.fieldComponent.{IField, fieldBaseImpl}
import de.htwg.se.MonopolyWithInsurance.model.fileIOComonent.IFileIO
import de.htwg.se.MonopolyWithInsurance.model.playerComponent.IPlayer
import de.htwg.se.MonopolyWithInsurance.model.playerComponent.playerBaseImpl.Player

import scala.xml.{Elem, Node, NodeSeq, PrettyPrinter}

class FileIO extends IFileIO{

  override def load(): IController = {
    val file = scala.xml.XML.loadFile("controller.xml")
    controllerFromXml(file)
  }

  def controllerFromXml(file: Elem): IController = {
    val controller = new Controller(boardFromXml(file \ "board"), playersFromXml(file \ "players"))
    for (f <- controller.board.fields) {
      f match {
        case s: Street =>
          if(s.owner != null & s.owner.name != "") {
            var playerIndex = -1
            for (p <- controller.players) {
              if (s.owner.name == p.name) {
                playerIndex = p.index
              }
            }
            val street = fieldBaseImpl.Street(s.index, s.name, s.neighbourhoodTypes, s.price, s.rent, controller.players(playerIndex))
            controller.board = Board(controller.board.fields.updated(s.index, street))
          } else {
            val street = fieldBaseImpl.Street(s.index, s.name, s.neighbourhoodTypes, s.price, s.rent, null)
            controller.board = Board(controller.board.fields.updated(s.index, street))
          }
        case _ =>
      }
    }
    controller.currentPlayerIndex = (file \ "currentPlayerIndex").text.toInt
    controller.actualField = controller.board.fields((file \ "actualField").text.toInt)
    val listRolledNumber = (file \ "rolledNumber").text.split("\\(|\\,|\\)" )
    controller.rolledNumber = (listRolledNumber(1).toInt, listRolledNumber(2).toInt)
    controller.history = Vector[String]((file \ "history").text)
    controller
  }

  def boardFromXml(seq: NodeSeq): Board = {
    var board = Vector[Field]()

    val fieldSeq = seq \\ "field"
    for (f <- fieldSeq) {
      val fType = (f \ "@type").text
      fType match {
        case "class de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl.Street" =>
          board = board :+ Street(
            (f \ "index").text.toInt,
            (f \ "name").text,
            neighbourhoodTypes((f \ "neighbourhoodTypes").text),
            (f \ "price").text.toInt,
            (f \ "rent").text.toInt,
            new Player((f \ "owner").text)
          )
        case "class de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl.ChanceCard" =>
          board = board :+ ChanceCard(
            (f \ "index").text.toInt,
            (f \ "name").text,
            (f \ "cardIndex").text.toInt,
            (f \ "getMoney").text.toInt,
            (f \ "giveMoney").text.toInt,
            (f \ "otherPlayerIndex").text.toInt,
            (f \ "info").text
          )
        case "class de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl.Tax" =>
          board = board :+ Tax(
            (f \ "index").text.toInt,
            (f \ "name").text,
            (f \ "taxAmount").text.toInt
          )
        case "class de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl.SpecialField" =>
          board = board :+ SpecialField(
            (f \ "index").text.toInt,
            (f \ "name").text
          )
      }
    }
    Board(board)
  }

  def neighbourhoodTypes(s: String): NeighbourhoodTypes.Value = {
    val color = Map("Brown" -> Brown, "Blue" -> Blue, "Pink" -> Pink, "Orange" -> Orange, "Red" -> Red,
      "Yellow" -> Yellow, "Green" -> Green, "Purple" -> Purple, "Station" -> Station,
      "Utility" -> Utility)
    color(s)
  }

  def playersFromXml(seq: NodeSeq): Vector[Player] = {
    var players = Vector[Player]()
    val playersSeq = seq \\ "player"
    for (p <- playersSeq) {
      val colorList = (p \ "color").text.split(" ")
      val col = new Color(colorList(0).toInt, colorList(1).toInt, colorList(2).toInt, 255)
      players = players :+ new Player(
        (p \ "name").text,
        (p \ "index").text.toInt,
        (p \ "currentPosition").text.toInt,
        (p \ "inJail").text.toInt,
        (p \ "money").text.toInt,
        (p \ "figure").text,
        col,
        (p \ "pasch").text.toInt,
        None
      )
    }
    players
  }

  override def save(controller: IController): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("controller.xml"))
    val prettyPrinter = new PrettyPrinter(200, 5)
    val xml = prettyPrinter.format(controllerToXml(controller))
    pw.write(xml)
    pw.close()
  }

  def controllerToXml(controller: IController): Node = {
    var c: Node =
      <controller>
        <currentPlayerIndex>{controller.currentPlayerIndex}</currentPlayerIndex>
        <actualField>{controller.actualField.index}</actualField>
        <context>{controller.context}</context>
        <rolledNumber>{controller.rolledNumber}</rolledNumber>
        <history>{controller.history}</history>
      </controller>
    c = addNode(c, boardToXml(controller.board))
    c = addNode(c, playersToXml(controller.players))
    c
  }

  def boardToXml(board: IBoard): Node = {
    var boardNode: Node = <board></board>
    for (f <- board.fields) {
      val field = fieldToXmL(f)
      boardNode = addNode(boardNode, field)
    }
    boardNode
  }

  def fieldToXmL(field: IField): Elem = {
    field match {
      case s: Street =>
        <field type={s.getClass.toString}>
          <index>{s.index}</index>
          <name>{s.name}</name>
          <neighbourhoodTypes>{s.neighbourhoodTypes}</neighbourhoodTypes>
          <price>{s.price}</price>
          <rent>{s.rent}</rent>
          <owner>{s.owner}</owner>
        </field>
      case ch: ChanceCard =>
        <field type={ch.getClass.toString}>
          <index>{ch.index}</index>
          <name>{ch.name}</name>
          <cardIndex>{ch.cardIndex}</cardIndex>
          <getMoney>{ch.getMoney}</getMoney>
          <giveMoney>{ch.giveMoney}</giveMoney>
          <otherPlayerIndex>{ch.otherPlayerIndex}</otherPlayerIndex>
          <info>{ch.info}</info>
        </field>
      case sp: SpecialField =>
        <field type={sp.getClass.toString}>
          <index>{sp.index}</index>
          <name>{sp.name}</name>
        </field>
      case t: Tax =>
        <field type={t.getClass.toString}>
          <index>{t.index}</index>
          <name>{t.name}</name>
          <taxAmount>{t.taxAmount}</taxAmount>
        </field>
    }
  }

  def playersToXml(players: Vector[IPlayer]): Node = {
    var playersNode: Node = <players></players>
    for (p <- players) {
      val player = playerToXml(p)
      playersNode = addNode(playersNode, player)
    }
    playersNode
  }

  def playerToXml(player: IPlayer): Elem = {
    <player>
      <name>{player.name}</name>
      <index>{player.index}</index>
      <currentPosition>{player.currentPosition}</currentPosition>
      <inJail>{player.inJail}</inJail>
      <money>{player.money}</money>
      <figure>{player.figure}</figure>
      <color>{"" + player.color.getRed + " " + player.color.getGreen + " " + player.color.getBlue}</color>
      <pasch>{player.pasch}</pasch>
    </player>
  }

  def addNode(to: Node, newNode: Node) = to match {
    case Elem(prefix, label, attributes, scope, child@_*) =>
      Elem(prefix, label, attributes, scope, child ++ newNode: _*)
    case _ => to
  }
}
