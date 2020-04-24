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