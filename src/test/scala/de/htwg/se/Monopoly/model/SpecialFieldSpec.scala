package de.htwg.se.Monopoly.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SpecialFieldSpec extends WordSpec with Matchers {
  "A SpecialField" when {
    "new" should {
      val start = SpecialField(0, "Start")
      val visitJail = SpecialField(10, "visit jail")
      val freeParking = SpecialField(20, "free parking")
      val goJail = SpecialField(30, "go to jail")
      val player = new Player("Yvonne")
      "start round" in {
        start.actOnPlayer(player) should be (SpecialField(0, "Start"))
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
    }
    "unapply" should {
      val start = SpecialField(0, "Start")
      "test for unapply method" in {
        SpecialField.unapply(start).get should be((0, "Start"))
      }
    }
  }
}
