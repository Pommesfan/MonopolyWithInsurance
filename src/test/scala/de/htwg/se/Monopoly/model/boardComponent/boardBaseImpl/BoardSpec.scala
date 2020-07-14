package de.htwg.se.Monopoly.model.boardComponent.boardBaseImpl

import de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl.{SpecialField, Street}
import de.htwg.se.Monopoly.model.{NeighbourhoodTypes, Variable}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoardSpec extends WordSpec with Matchers {
  "A Board" when {
    val board = Board(Variable.START_BOARD)
    "new" should {
      "have all fields" in {
        board.fields should have length(40)
        board.fields(3) should be (Street(3, "Turmstrasse", NeighbourhoodTypes.Brown, 60, 4))
        board.fields(30) should be (SpecialField(30, "Gefängnis: Gehen Sie ins Gefängnis"))
      }
    }
    "unapply" when {
      "test for unapply method" in {
        Board.unapply(board)
      }
    }
    "print Grid" in {
      val stream = new java.io.ByteArrayOutputStream()
      board.toString should be
      Console.withOut(stream) {
        print(
          "index  name                      type       price rent  owner               \n" +
            "0      Los                      \n" +
            "1      Badstrasse                Brown      60    2     null                \n" +
            "2      Gemeinschaftsfeld        \n" +
            "3      Turmstrasse               Brown      60    4     null                \n" +
            "4      Einkommenssteuer                     200\n" +
            "5      Südbahnhof                Station    200   25    null                \n" +
            "6      Chausseestrasse           Blue       100   6     null                \n" +
            "7      Ereignisfeld             \n" +
            "8      Elisenstrasse             Blue       100   6     null                \n" +
            "9      Poststrasse               Blue       120   8     null                \n" +
            "10     Gefängnis                \n" +
            "11     Seestraße                 Pink       140   10    null                \n" +
            "12     Elektrizitätswerk         Utility    150   0     null                \n" +
            "13     Hafenstrasse              Pink       140   10    null                \n" +
            "14     Neuestrasse               Pink       160   12    null                \n" +
            "15     Westbahnhof               Station    200   25    null                \n" +
            "16     Münchner Strasse          Orange     180   14    null                \n" +
            "17     Gemeinschaftsfeld        \n" +
            "18     Wiener Strasse            Orange     180   14    null                \n" +
            "19     Berliner Strasse          Orange     200   16    null                \n" +
            "20     Frei Parken              \n" +
            "21     Theaterstrasse            Red        220   18    null                \n" +
            "22     Ereignisfeld             \n" +
            "23     Museumstrasse             Red        220   18    null                \n" +
            "24     Opernplatz                Red        240   20    null                \n" +
            "25     Nordbahnhof               Station    200   25    null                \n" +
            "26     Lessingstrasse            Yellow     260   22    null                \n" +
            "27     Schillerstrasse           Yellow     260   22    null                \n" +
            "28     Elektrizitätswerk         Utility    150   0     null                \n" +
            "29     Goethestrasse             Yellow     280   24    null                \n" +
            "30     Gefängnis: Gehen Sie ins Gefängnis\n" +
            "31     Rathhausplatz             Green      300   26    null                \n" +
            "32     Hauptstrasse              Green      300   26    null                \n" +
            "33     Gemeinschaftsfeld        \n" +
            "34     Bahnhofstrasse            Green      320   28    null                \n" +
            "35     Hauptbahnhof              Station    200   25    null                \n" +
            "36     Ereignisfeld             \n" +
            "37     Parkstrasse               Purple     350   35    null                \n" +
            "38     Zusatzsteuer                         100\n" +
            "39     Schlossallee              Purple     400   50    null                \n" +
            "\n" +
            "Players:" +
            "\nindex  name                      money      position\n" +
            "0      player1                   1500       0    \n" +
            "1      player2                   1500       0    \n")
      }
    }
  }

  "New Board" when {
    val board = new Board()
    "new created" should {
      "have all fields " in {
        board.fields should have length (40)
        board.fields(3) should be(Street(3, "Turmstrasse", NeighbourhoodTypes.Brown, 60, 4))
        board.fields(30) should be(SpecialField(30, "Gefängnis: Gehen Sie ins Gefängnis"))
      }
    }
  }
}
