package de.htwg.se.MonopolyWithInsurance.model.fileIOComonent

import de.htwg.se.MonopolyWithInsurance.controller.IController

trait IFileIO {
  def load(): IController
  def save(controller: IController): Unit
}
