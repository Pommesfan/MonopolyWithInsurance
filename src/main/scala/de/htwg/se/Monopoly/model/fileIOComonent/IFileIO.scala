package de.htwg.se.Monopoly.model.fileIOComonent

import de.htwg.se.Monopoly.controller.IController

trait IFileIO {
  def load(): IController
  def save(controller: IController): Unit
}
