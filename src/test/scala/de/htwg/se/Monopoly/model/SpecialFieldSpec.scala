package de.htwg.se.Monopoly.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SpecialFieldSpec extends WordSpec with Matchers {
  "A SpecialField" when {
    "new" should {
      val start = SpecialField(0, "Los")
      val visitJail = SpecialField(10, "visit jail")
      val freeParking = SpecialField(20, "free parking")
      val goJail = SpecialField(30, "go to jail")
      val player = new Player("Yvonne")
      "start round" in {
        start.actOnPlayer(player) should be (SpecialField(0, "Los"))
      }
      "visit jail" in {
        visitJail.actOnPlayer(player) should be (SpecialField(10, "visit jail"))
      }
      "land on free parking" in {
        freeParking.actOnPlayer(player) should be (SpecialField(20, "free parking"))
      }
      "go to jail" in {
        goJail.actOnPlayer(player) should be (SpecialField(30, "go to jail"))
      }
      "return string" in {
        start.toString should be("0: Los")
      }
    }
    "unapply" should {
      val start = SpecialField(0, "Los")
      "test for unapply method" in {
        SpecialField.unapply(start).get should be((0, "Los"))
      }
    }
  }
}
