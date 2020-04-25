import de.htwg.se.Monopoly.model.Player

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

//Main Method
object Main {
  def main(args: Array[String]): Unit =
    println("I'm the main method")
}

/*********************Test Vektor************************/

trait Animal

case class Dog(name: String, old: Int) extends Animal

case class Cat(name: String, old: Int) extends Animal

val animalHouse: Vector[Animal] = Vector(
  Dog("Rover", 11),
  Cat("Felix", 1)
)

/**********************Match Field**********************/
/****Differend Field Types ****/
case class Fieldtest(value: Int) {
  def isSet: Boolean = value != 0
}

val fieldtest0 = Fieldtest(0)
val fieldtest1 = Fieldtest(1)
val fieldtest2 = Fieldtest(2)
val fieldtest3 = Fieldtest(3)

trait Field {
  val index: Int
  val name: String
}

case class Street(index: Int, name: String, owner: String, price: Int, rent: Array[Int],
                  mortgageValue: Int, toDissolveMortgage: Int, color: String, priceHouse: Int,
                  priceHotel: Int, numberOfHouses: Int, hotel: Boolean) extends Field{}

case class RailwayStation(index: Int, name: String, owner: String, price: Int, rent: Array[Int],
                          mortgageValue: Int, toDissolveMortgage: Int, color: String) extends Field{}

case class SupplyFactory(index: Int, name: String, owner: String, price: Int, rent: Array[Int],
                         mortgageValue: Int, toDissolveMortgage: Int, color: String) extends Field{
  def rent(num: Int, ownSupllyFactorys: Int): Int = {
    if(ownSupllyFactorys < 2){
      num * 4
    } else {
      num * 10
    }
  }
}

case class Action(index: Int, name: String) extends Field


/**** Default Playboard ****/


val field0 = Action(0, "Los")
val field1 = Street(1, "Badstrasse", "", 60, Array(2,10,30,90,160,250), 30, 30, "Braun", 50, 50, 0, false)
val field2 = Action(2, "Gemeinschaftsfeld")
val field3 = Street(3, "Turmstrasse", "", 60, Array(4,20,60,180,320,450), 30, 30, "Braun", 50, 50, 0, false)
val field4 = Action(4, "Einkommenssteuer")
val field5 = RailwayStation(5, "Südbahnhof", "", 200, Array(25,50,100,200), 100, 100, "Bahnhof")
val field6 = Street(6, "Chausseestrasse", "", 100, Array(6,30,90,270,400,550), 50, 50, "Blau", 50, 50, 0, false)
val field7 = Action(7, "Ereignisfeld")
val field8 = Street(8, "Elisenstrasse", "", 100, Array(6,30,90,270,400,550), 50, 50, "Blau", 50, 50, 0, false)
val field9 = Street(9, "Postsstrasse", "",120, Array(8,40,100,300,450,600), 60, 60, "Blau", 50, 50, 0, false)
val field10 = Action(10, "Gefängnis")
val field11 = Street(11, "Seestrasse", "", 140, Array(10,50,150,450,625,750), 70, 70, "pink", 100, 100, 0, false)
val field12 = SupplyFactory(12, "Elektrizitätswerk", "", 150, Array(), 75, 75, "Supply")
val field13 = Street(13, "Hafenstrasse", "", 140, Array(10,50,150,450,625,750), 70, 70, "pink", 100, 100, 0, false)
val field14 = Street(14, "Neuestrasse", "", 160, Array(12,60,180,500,700,900), 80, 80, "pink", 100, 100, 0, false)
val field15 = RailwayStation(15, "Westbahnhof", "", 200, Array(25, 50, 100, 200), 100, 100, "Bahnhof")
val field16 = Street(16, "Münchner Strasse", "", 180, Array(14,70,200,550,750,950), 90, 90, "Orange", 100, 100, 0, false)
val field17 = Action(17, "Gemeinschaftsfeld")
val field18 = Street(18, "Wiener Strasse", "", 180, Array(14,70,200,550,750,950), 90, 90, "Orange", 100, 100, 0, false)
val field19 = Street(19, "Berliner Strasse", "", 200, Array(16,80,220,600,800,1000), 100, 100, "Orange", 100, 100, 0, false)
val field20 = Action(20, "FreiParken")
val field21 = Street(21, "Theaterstrasse", "", 220, Array(18,90,250,700,875,1050), 110, 110, "Rot", 150, 150, 0, false)
val field22 = Action(22, "Ereignisfeld")
val field23 = Street(23, "Museumstrasse", "", 220, Array(18,90,250,700,875,1050), 110, 110, "Rot", 150, 150, 0, false)
val field24 = Street(24, "Opernplatz", "", 240, Array(20,100,300,750,925,1100), 120, 120, "Rot", 150, 150, 0, false)
val field25 = RailwayStation(25, "Nordbahnhof", "", 200, Array(25,50,100,200), 100, 100, "Bahnhof")
val field26 = Street(26, "Lessingstrasse", "", 260, Array(22,110,330,800,975,1150), 130, 130, "Gelb", 150, 150, 0, false)
val field27 = Street(27, "Schillerstrasse", "", 260, Array(22,110,330,800,975,1150), 130, 130, "Gelb", 150, 150, 0, false)
val field28 = SupplyFactory(28, "Elektrizitätswerk", "", 150, Array(), 75, 75, "Supply")
val field29 = Street(29, "Goethestrasse", "", 280, Array(24,120,360,850,1025,1200), 140, 140, "Gelb", 150, 150, 0, false)
val field30 = Street(30, "Rathhausplatz", "", 300, Array(26,130,390,900,1100,1275), 150, 150, "Grün", 200, 200, 0, false)
val field31 = Street(31, "Hauptstrasse", "", 300, Array(26,130,390,900,1100,1275), 150, 150, "Grün", 200, 200, 0, false)
val field32 = Action(32, "Gemeinschaftsfeld")
val field33 = Street(33, "Bahnhofstrasse", "", 320, Array(28,150,450,1000,1200,1400), 160, 160, "Grün", 200, 200, 0, false)
val field34 = RailwayStation(34, "Hauptbahnhof", "", 200, Array(25,50,100,200), 100, 100, "Bahnhof")
val field35 = Action(35, "Ereignisfeld")
val field36 = Street(36, "Parkstrasse", "", 350, Array(35,175,500,1100,1300,1500), 175, 175, "Lila", 200, 200, 0, false)
val field37 = Action(37, "Zusatzsteuer")
val field38 = Street(38, "Schlossallee", "", 400, Array(50,200,600,1400,1700,2000), 200, 200, "Lila", 200, 200, 0, false)


object GameFieldObject extends Enumeration {
  val field0, field1, field2 = Value
}

//GameField.field0, GameField.field1, GameField.field2, GameField.field3, GameField.field4, GameField.field5, GameField.field6
case class MatchField[T](fields: Vector[T]){
  val size: Int = fields.size
  def field(index: Int):T = fields (index)
  def replaceField(index: Int, field: T): MatchField[T] = copy(fields.updated(index, field))
}

case class GameField(fields: MatchField[Field]){
  def this() {
    this(new MatchField[Field](Vector(field0)))
  }
}

/****Matchfield1****/
val matchfield1 = MatchField(Vector(field0, field1, field2, field3))

//matchfield1.test()

matchfield1.fields(0)
matchfield1.size  //Anzahl von Vector(field0, field1, field2, field3)
matchfield1.field( 3)

//Zugriff auf Vector(field0, field1, field2, field3), steht für Reihe
matchfield1.fields(0)
matchfield1.fields(1)
matchfield1.fields(2)

val replaced = matchfield1.replaceField(1, field6)
replaced.field( 1)

replaced.field( 2)

/****Gamefield****/
val gamefield1 = new GameField()
