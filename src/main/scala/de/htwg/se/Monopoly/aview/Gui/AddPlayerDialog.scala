package de.htwg.se.Monopoly.aview.Gui

import de.htwg.se.Monopoly.controller.{Controller, DialogClosed}

import scala.collection.mutable.ArrayBuffer
import scala.swing.event.{ButtonClicked, EditDone}
import scala.swing.{BorderPanel, BoxPanel, Button, ComboBox, Dialog, Dimension, FlowPanel, GridPanel, Label, Orientation, Swing, TextField, Window}

case class AddPlayerDialog(parent: Window, controller: Controller) extends Dialog {
  title = "Add new Player"
  preferredSize = new Dimension(700, 500)
  visible = true

  var players: ArrayBuffer[String] = new ArrayBuffer[String]()

  val okButton = new Button("Ok")
  val cancelButton = new Button("Cancel")
  val nameField1: TextField = new TextField(32) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}
  val figure1: ComboBox[String] = new ComboBox(List("Cat", "Dog")) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}

  val nameField2: TextField = new TextField(32) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}
  val figure2: ComboBox[String] = new ComboBox(List("Cat", "Dog")) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}

  val nameField3: TextField = new TextField(32) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}
  val figure3: ComboBox[String] = new ComboBox(List("Cat", "Dog")) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}

  val nameField4: TextField = new TextField(32) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}
  val figure4: ComboBox[String] = new ComboBox(List("Cat", "Dog")) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}

  val nameField5: TextField = new TextField(32) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}
  val figure5: ComboBox[String] = new ComboBox(List("Cat", "Dog")) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}

  val nameField6: TextField = new TextField(32) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}
  val figure6: ComboBox[String] = new ComboBox(List("Cat", "Dog")) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}

  val nameField7: TextField = new TextField(32) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}
  val figure7: ComboBox[String] = new ComboBox(List("Cat", "Dog")) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}

  val nameField8: TextField = new TextField(32) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}
  val figure8: ComboBox[String] = new ComboBox(List("Cat", "Dog")) {maximumSize = new Dimension(Short.MaxValue, preferredSize.height)}


  val newPlayerPanel = new GridPanel(9, 2) {
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Spielername 1: ")
        contents += Swing.HStrut(5)
        contents += nameField1
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Figure: ")
        contents += Swing.HStrut(20)
        contents += figure1
      }
      for (e <- contents)
        e.xLayoutAlignment = 0.0
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Spielername 2: ")
        contents += Swing.HStrut(5)
        contents += nameField2
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Figure: ")
        contents += Swing.HStrut(20)
        contents += figure2
      }
      for (e <- contents)
        e.xLayoutAlignment = 0.0
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Spielername 3: ")
        contents += Swing.HStrut(5)
        contents += nameField3
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Figure: ")
        contents += Swing.HStrut(20)
        contents += figure3
      }
      for (e <- contents)
        e.xLayoutAlignment = 0.0
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Spielername 4: ")
        contents += Swing.HStrut(5)
        contents += nameField4
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Figure: ")
        contents += Swing.HStrut(20)
        contents += figure4
      }
      for (e <- contents)
        e.xLayoutAlignment = 0.0
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Spielername 5: ")
        contents += Swing.HStrut(5)
        contents += nameField5
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Figure: ")
        contents += Swing.HStrut(20)
        contents += figure5
      }
      for (e <- contents)
        e.xLayoutAlignment = 0.0
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Spielername 6: ")
        contents += Swing.HStrut(5)
        contents += nameField6
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Figure: ")
        contents += Swing.HStrut(20)
        contents += figure6
      }
      for (e <- contents)
        e.xLayoutAlignment = 0.0
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Spielername 7: ")
        contents += Swing.HStrut(5)
        contents += nameField7
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Figure: ")
        contents += Swing.HStrut(20)
        contents += figure7
      }
      for (e <- contents)
        e.xLayoutAlignment = 0.0
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Spielername 8: ")
        contents += Swing.HStrut(5)
        contents += nameField8
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Figure: ")
        contents += Swing.HStrut(20)
        contents += figure8
      }
      for (e <- contents)
        e.xLayoutAlignment = 0.0
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    contents += new BorderPanel {
      add(new FlowPanel {
        contents += okButton
        contents += cancelButton
      },
        BorderPanel.Position.South)
    }
  }
  contents = newPlayerPanel

  listenTo(
    nameField1,
    nameField2,
    nameField3,
    nameField4,
    nameField5,
    nameField6,
    nameField7,
    nameField8,
    okButton,
    cancelButton
  )

  /**
   * Beide Dialoge zu einem Zusammenfügen
   * und bei okButton controller.setPlayer
   */

  reactions += {
    case EditDone(`nameField1`) => addPlayer(nameField1.text)
    case EditDone(`nameField2`) => addPlayer(nameField2.text)
    case EditDone(`nameField3`) => addPlayer(nameField3.text)
    case EditDone(`nameField4`) => addPlayer(nameField4.text)
    case EditDone(`nameField5`) => addPlayer(nameField5.text)
    case EditDone(`nameField6`) => addPlayer(nameField6.text)
    case EditDone(`nameField7`) => addPlayer(nameField7.text)
    case EditDone(`nameField8`) => addPlayer(nameField8.text)
    case EditDone(`figure1`) =>
    case ButtonClicked(`okButton`) => controller.setPlayers(players.toArray); DialogClosed(this, cancel = false); visible=false
    case ButtonClicked(`cancelButton`) => DialogClosed(this, cancel = true); visible=false
  }

  def addPlayer(s: String): Unit = {
    println(s)
    players.append(s)
  }
}