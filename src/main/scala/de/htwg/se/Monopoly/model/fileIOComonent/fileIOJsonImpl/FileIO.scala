package de.htwg.se.Monopoly.model.fileIOComonent.fileIOJsonImpl
import java.awt.Color

import de.htwg.se.Monopoly.controller.IController
import de.htwg.se.Monopoly.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Monopoly.model.NeighbourhoodTypes
import de.htwg.se.Monopoly.model.NeighbourhoodTypes._
import de.htwg.se.Monopoly.model.boardComponent.IBoard
import de.htwg.se.Monopoly.model.boardComponent.boardBaseImpl.Board
import de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl._
import de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl
import de.htwg.se.Monopoly.model.fileIOComonent.IFileIO
import de.htwg.se.Monopoly.model.playerComponent.IPlayer
import de.htwg.se.Monopoly.model.playerComponent.playerBaseImpl.Player
import play.api.libs.json.{JsArray, JsLookupResult, JsNumber, JsObject, JsValue, Json}

import scala.io._

class FileIO extends IFileIO{
  override def load(): IController = {
    val source: String = Source.fromFile("controller.json").getLines().mkString
    val json: JsValue = Json.parse(source)
    controllerFromJson(json)
  }

  def controllerFromJson(value: JsValue): IController = {
    val controller = new Controller(boardFromJson( value \ "controller" \ "board"), playersFromJson(value \ "controller" \ "players"))
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
    controller.currentPlayerIndex = (value \ "controller" \ "currentPlayerIndex").as[Int]
    controller.actualField = controller.board.fields((value \ "controller" \ "actualField").as[Int])
    controller.rolledNumber = ((value \ "controller" \ "rolledNumber_1").as[Int], (value \ "controller" \ "rolledNumber_1").as[Int])
    controller.history = (value \ "controller" \ "history").as[Vector[String]]
    controller
  }

  def boardFromJson(seq: JsLookupResult): Board = {
    var board = Vector[Field]()

    val fieldSeq = (seq \\ "field")
    for (f <- fieldSeq) {
      val fType = (f \ "type").as[String]
      fType match {
        case "street" =>
          board = board :+ Street(
            (f \ "index").as[Int],
            (f \ "name").as[String],
            neighbourhoodTypes((f \ "neighbourhoodTypes").as[String]),
            (f \ "price").as[Int],
            (f \ "rent").as[Int],
            new Player((f \ "owner").as[String])
          )
        case "chanceCard" =>
          board = board :+ ChanceCard(
            (f \ "index").as[Int],
            (f \ "name").as[String],
            (f \ "cardIndex").as[Int],
            (f \ "getMoney").as[Int],
            (f \ "giveMoney").as[Int],
            (f \ "otherPlayerIndex").as[Int],
            (f \ "info").as[String]
          )
        case "tax" =>
          board = board :+ Tax(
            (f \ "index").as[Int],
            (f \ "name").as[String],
            (f \ "taxAmount").as[Int]
          )
        case "specialField" =>
          board = board :+ SpecialField(
            (f \ "index").as[Int],
            (f \ "name").as[String]
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

  def playersFromJson(seq: JsLookupResult): Vector[Player] = {
    var players = Vector[Player]()
    val playersSeq = (seq \\ "player")
    for (p <- playersSeq) {
      val colorList = (p \ "color").as[String].split(" ")
      val col = new Color(colorList(0).toInt, colorList(1).toInt, colorList(2).toInt, 255)
      players = players :+ new Player(
        (p \ "name").as[String],
        (p \ "index").as[Int],
        (p \ "currentPosition").as[Int],
        (p \ "inJail").as[Int],
        (p \ "money").as[Int],
        (p \ "figure").as[String],
        col,
        (p \ "pasch").as[Int]
      )
    }
    players
  }

  override def save(controller: IController): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("controller.json"))
    pw.write(Json.prettyPrint(controllerToJson(controller)))
    pw.close()
  }

  def controllerToJson(controller: IController): JsObject = {
    Json.obj(
      "controller" -> Json.obj(
        "currentPlayerIndex" -> JsNumber(controller.currentPlayerIndex),
        "actualField" -> JsNumber(controller.actualField.index),
        //"context" -> ,
        "rolledNumber_1" -> JsNumber(controller.rolledNumber._1),
        "rolledNumber_2" -> JsNumber(controller.rolledNumber._2),
        "history" -> Json.toJson(controller.history),
        "board" -> boardToJson(controller.board),
        "players" -> playersToJson(controller.players),
      )
    )
  }

  def boardToJson(board: IBoard):JsArray = {
    var jSonObject = Json.arr()
    for (f <- board.fields) {
      f match {
        case s: Street => jSonObject = jSonObject :+ Json.obj("field" -> Json.toJson(streetToJson(s)))
        case ch: ChanceCard => jSonObject = jSonObject :+ Json.obj("field" -> Json.toJson(chanceCardToTax(ch)))
        case sp: SpecialField => jSonObject = jSonObject :+ Json.obj("field" -> Json.toJson(specialFieldToJson(sp)))
        case t: Tax => jSonObject = jSonObject :+ Json.obj("field" -> Json.toJson(taxToJson(t)))
        case _ =>
      }
    }
    jSonObject
  }

  def streetToJson(s: Street): JsObject = Json.obj(
    "type" -> "street",
    "index" -> s.index,
    "name" -> s.name,
    "neighbourhoodTypes" -> s.neighbourhoodTypes.toString,
    "price" -> s.price,
    "rent" -> s.rent,
    "owner" -> {
      var owner = ""
      if (s.owner != null) {
        owner = s.owner.toString
      }
      owner
    }
  )

  def chanceCardToTax(c: ChanceCard): JsObject = Json.obj(
    "type" -> "chanceCard",
    "index" -> c.index,
    "name" -> c.name,
    "cardIndex" -> c.cardIndex,
    "getMoney" -> c.getMoney,
    "giveMoney" -> c.giveMoney,
    "otherPlayerIndex" -> c.otherPlayerIndex,
    "info" -> c.info
  )

  def specialFieldToJson(sp: SpecialField): JsObject = Json.obj(
    "type" -> "specialField",
    "index" -> sp.index,
    "name" -> sp.name
  )

  def taxToJson(t: Tax): JsObject = Json.obj(
    "type" -> "tax",
    "index" -> t.index,
    "name" -> t.name,
    "taxAmount" -> t.taxAmount
  )

  def playersToJson(players: Vector[IPlayer]): JsArray = {
    var jSonArray = Json.arr()
    for (p <- players) {
      jSonArray = jSonArray :+ Json.obj("player" -> Json.toJson(playerToJson(p)))
    }
    jSonArray
  }

  def playerToJson(p: IPlayer): JsObject = Json.obj(
    "name" -> p.name,
    "index" -> p.index,
    "currentPosition" -> p.currentPosition,
    "inJail" -> p.inJail,
    "money" -> p.money,
    "figure" -> p.figure,
    "color" -> ("" + p.color.getRed.toString + " " + p.color.getGreen.toString + " " + p.color.getBlue.toString),
    "pasch" -> p.pasch
  )
}
