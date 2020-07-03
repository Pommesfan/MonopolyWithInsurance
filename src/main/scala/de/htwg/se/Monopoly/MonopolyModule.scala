package de.htwg.se.Monopoly

import com.google.inject.AbstractModule
import de.htwg.se.Monopoly.controller.IController
import de.htwg.se.Monopoly.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Monopoly.model.boardComponent._
import de.htwg.se.Monopoly.model.boardComponent.boardBaseImpl.Board
import de.htwg.se.Monopoly.model.fileIOComonent._
import net.codingwell.scalaguice.ScalaModule

class MonopolyModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[IController].to[Controller]
    bind[IBoard].to[Board]

    bind[IFileIO].to[fileIOXmlImpl.FileIO]
  }
}