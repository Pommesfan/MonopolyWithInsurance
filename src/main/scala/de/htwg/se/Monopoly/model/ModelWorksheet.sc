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

case class Field(value: Int) {
  def isSet: Boolean = value != 0
}

val field0 = Field(0)
val field1 = Field(1)
val field2 = Field(2)
val field3 = Field(3)

object GameFieldObject{
  val field0  = Field(0)
  val field1  = Field(1)
  val field2  = Field(2)
  val field3  = Field(3)
  val field4  = Field(4)
  val field5  = Field(5)
  val field6  = Field(6)
}
//GameField.field0, GameField.field1, GameField.field2, GameField.field3, GameField.field4, GameField.field5, GameField.field6
case class MatchField[T](fields: Vector[T]){
  val size: Int = fields.size
  def field(index: Int):T = fields (index)
  def replaceField(index: Int, field: T): MatchField[T] = copy(fields.updated(index, field))
}

case class GameField(fields: MatchField[Field]){
  def this() {
    this(new MatchField[Field](Vector(GameFieldObject.field0,
      GameFieldObject.field1, GameFieldObject.field2, GameFieldObject.field3,
      GameFieldObject.field4, GameFieldObject.field5, GameFieldObject.field6)))
  }
}

/****Matchfield1****/
val matchfield1 = MatchField(Vector(field0, field1, field2, field3))

matchfield1.fields(0).value
matchfield1.size  //Anzahl von Vector(field0, field1, field2, field3)
matchfield1.field( 3)

//Zugriff auf Vector(field0, field1, field2, field3), steht für Reihe
matchfield1.fields(0)
matchfield1.fields(1)
matchfield1.fields(2)

val replaced = matchfield1.replaceField(0, Field(5))
replaced.field( 0).value

replaced.field( 2)

/****Gamefield****/
val gamefield1 = new GameField()
gamefield1.fields.replaceField(1, Field(3))