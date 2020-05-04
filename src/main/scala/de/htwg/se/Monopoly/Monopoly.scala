package de.htwg.se.Monopoly

import de.htwg.se.Monopoly.model.Player

object Monopoly {
  def main(args: Array[String]): Unit = {
    val student = new Player("Yvonne")
    println("Hello, " + student.name)
  }
}
