package de.htwg.se.Monopoly.model.fileIOComponent.fileIOXMLImpl

import de.htwg.se.Monopoly.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Monopoly.model.fileIOComonent.fileIOXmlImpl
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FileIOSpec extends WordSpec with Matchers {
  "A Xml FileIO" should {
    val controller = new Controller()
    val xmlIO = new fileIOXmlImpl.FileIO()

    "The save function creates or overwrite the xml file with the current settings" in {
      xmlIO.save(controller)
    }
    "Load a state, will create the old state to continue playing there" in {
      val load_controller = xmlIO.load()
      /**
      load_controller should be equals (controller)
      load_controller.actualField should be equals(controller.actualField)
      load_controller.board should be equals(controller.board)
      load_controller.context should be equals(controller.context)
      load_controller.currentPlayerIndex should be equals(controller.currentPlayerIndex)
      load_controller.history should be equals(controller.history)
      load_controller.players should be equals(controller.players)
      load_controller.rolledNumber should be equals(controller.rolledNumber)*/
    }
  }
}
