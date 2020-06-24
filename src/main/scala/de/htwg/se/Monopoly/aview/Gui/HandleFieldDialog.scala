package de.htwg.se.Monopoly.aview.Gui

import de.htwg.se.Monopoly.controller.Controller

import scala.swing.{Dialog, Dimension, Window}

case class HandleFieldDialog(parent: Window, controller: Controller) extends Dialog {
  title = ""
  preferredSize = new Dimension(700, 500)
  visible = true
}
