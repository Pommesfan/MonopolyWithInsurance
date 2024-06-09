package de.htwg.se.MonopolyWithInsurance

import com.google.inject.AbstractModule
import de.htwg.se.MonopolyWithInsurance.controller.IController
import de.htwg.se.MonopolyWithInsurance.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.MonopolyWithInsurance.model.boardComponent._
import de.htwg.se.MonopolyWithInsurance.model.boardComponent.boardBaseImpl.Board
import de.htwg.se.MonopolyWithInsurance.model.fileIOComonent._
import net.codingwell.scalaguice.ScalaModule

class MonopolyModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[IController].to[Controller]
    bind[IBoard].to[Board]

    bind[IFileIO].to[fileIOJsonImpl.FileIO]
  }
}