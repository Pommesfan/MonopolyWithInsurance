package de.htwg.se.Monopoly.model.fileIOComponent.fileIOJsonImpl

import de.htwg.se.Monopoly.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Monopoly.model.fileIOComonent.fileIOJsonImpl
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FileIOSpec extends WordSpec with Matchers {
  "A Json FileIO" should {
    val controller = new Controller()
    val jsonIO = new fileIOJsonImpl.FileIO()

    "The save function creates or overwrite the json file with the current settings" in {
      jsonIO.save(controller)
    }
    "Load a state, will create the old state to continue playing there" in {
      val load_controller = jsonIO.load()
      load_controller should be equals (controller)
      load_controller.actualField should be equals(controller.actualField)
      load_controller.board should be equals(controller.board)
      load_controller.context should be equals(controller.context)
      load_controller.currentPlayerIndex should be equals(controller.currentPlayerIndex)
      load_controller.history should be equals(controller.history)
      load_controller.players should be equals(controller.players)
      load_controller.rolledNumber should be equals(controller.rolledNumber)
    }
  }
}
