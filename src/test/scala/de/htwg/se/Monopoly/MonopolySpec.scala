package de.htwg.se.Monopoly

import java.io.ByteArrayInputStream

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MonopolySpec extends WordSpec with Matchers {
  "Monopoly" when {
    "new" should {
      val monopoly = Monopoly
      "start and exit game" in {
        val in = new ByteArrayInputStream("help\nexit".getBytes)
        Console.withIn(in) {
          monopoly.main(Array[String]())
        }
      }
      "accept text input as argument without readline loop, to test it from command line " in {
        Monopoly.main(Array[String]("exit"))
      }
    }
  }
}