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
        start.actOnPlayer(player) should be ("You landed on Go \nCollect 200!")
      }
      "visit jail" in {
        visitJail.actOnPlayer(player) should be ("You are visiting your \ndear friend in Jails.")
      }
      "land on free parking" in {
        freeParking.actOnPlayer(player) should be ("You landed on Free Parking. \nNothing happens.")
      }
      "go to jail" in {
        goJail.actOnPlayer(player) should be ("You are in jail! \nYou will skip the next three turns")
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
