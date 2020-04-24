import de.htwg.se.Monopoly.model.Player

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/*case class Cell(column:Int, row:Int)

//neue Zelle = new Cell
val cell1 = Cell(4,5)
cell1.column
cell1.row

//case class Field(cells: Array[Cell])

val field0 = Field(Array.ofDim[Cell](1))
field1.cells(0)=cell1
field1.cells(0).column
field1.cells(0).row*/

//Main Method
object Main {
  def main(args: Array[String]): Unit =
    println("I'm the main method")
}

//multidimensional-array

val rows = 2
val cols = 3

val a = Array.ofDim[String](rows, cols)

a(0)(0) = "a"
a(0)(1) = "b"
a(0)(2) = "c"
a(1)(0) = "d"
a(1)(1) = "e"
a(1)(2) = "f"

val x = a(0)(0)

for {
  i <- 0 until rows
  j <- 0 until cols
} println(s"($i)($j) = ${a(i)(j)}")

/*********************Test Vektor************************/

trait Animal

case class Dog(name: String, old: Int) extends Animal

case class Cat(name: String, old: Int) extends Animal

val animalHouse: Vector[Animal] = Vector(
  Dog("Rover", 11),
  Cat("Felix", 1)
)

/**********************Field Types**********************/

case class Field(value: Int) {
  // val number = value + 2
  def isSet: Boolean = value != 0
}

val field0 = Field(0)
val field1 = Field(1)
val field2 = Field(2)
val field3 = Field(3)

case class MatchField[T](fields: Vector[Vector[T]]){
  def this(size: Int, filling: T) = this(Vector.tabulate(size, size){(row, col) => filling})
  val size: Int = fields.size
  def field(row: Int, col: Int):T = fields (row)(col)
  def replaceField(row: Int, col: Int, field:T):MatchField[T] = copy(fields.updated(row, fields(row).updated(col, field)))
  //def fill (filling: T):MatchField[T] = copy(Vector.tabulate(size, size){(row, col) => filling})
}

val matchfield1 = MatchField(Vector(Vector(field0, field1, field2, field3), Vector(field0, field1, field2, field3), Vector(field0, field1, field2, field3)))

matchfield1.fields(0)(3).value
matchfield1.size  //Anzahl von Vector(field0, field1, field2, field3)
matchfield1.field(0, 1)

//Zugriff auf Vector(field0, field1, field2, field3), steht für Reihe
matchfield1.fields(0)
matchfield1.fields(1)
matchfield1.fields(2)

val replaced = matchfield1.replaceField(1, 3, Field(6))
replaced.field(1, 3).value
