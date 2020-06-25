package de.htwg.se.Monopoly.aview.Gui

import java.awt.geom.{GeneralPath, Rectangle2D}

import de.htwg.se.Monopoly.controller.{Controller, DecrementJailCounter, DiceRolled, HandleStreet, LandedOnField, MoneyTransaction, NewGameEvent, NextPlayer, OwnStreet, PlayerSet, WaitForNextPlayer}
import java.awt.{Color, Image}
import java.awt.image.BufferedImage
import java.io.File

import scala.swing.{BoxPanel, Button, Dialog, Dimension, FlowPanel, Frame, Graphics2D, Label, Menu, MenuBar, Orientation, Panel, ScrollPane, Swing, TextArea, _}
import javax.swing.ImageIcon

import scala.swing.Swing.{CompoundBorder, EmptyBorder, LineBorder}
import scala.swing.event.{ButtonClicked, Key}

class SwingGui(controller: Controller) extends MainFrame {
  listenTo(controller)
  val pathCar = "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Car.png"
  val pathCat = "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Cat.png"
  val pathDog = "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Dog.png"
  val pathFingerhut = "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Fingerhut.png"
  val pathHut = "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/hut.png"
  val pathShip = "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Ship.png"
  val pathShoe = "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Shoe.png"
  val pathWheelbarrow = "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/wheelbarrow.png"

  val pathBoard = "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Monopoly_board.jpg"


  title = "Monopoly"
  preferredSize = new Dimension(1050, 770)

  def shapePanel(width: Int, height: Int, color: Color = Color.lightGray): Panel = new Panel {
    val ax = 0
    val bx = 15
    val cy = 0
    preferredSize = new Dimension(width, height)
    xLayoutAlignment = 0
    yLayoutAlignment = 0
    border = LineBorder(java.awt.Color.BLACK, 1)
    override def paint(g: Graphics2D): Unit = {
      g.setColor(color)
      g.fillPolygon(Array(ax, bx, cy), Array(ax, cy, bx), 3)
    }
  }

  def scaledImageIcon(path: String, width: Int, height: Int): ImageIcon = {
    val orig = new ImageIcon(path)
    val scaledImage = orig.getImage.getScaledInstance(width, height, Image.SCALE_DEFAULT)
    new ImageIcon(scaledImage)
  }

  def scaledImage(path: String, width: Int, height: Int): Image = {
    javax.imageio.ImageIO.read(new File(path)).getScaledInstance(width, height, Image.SCALE_DEFAULT)
  }

  def getPath(figure: String): String = {
    figure match{
      case "Car" => pathCar
      case "Cat" => pathCat
      case "Dog" => pathDog
      case "Fingerhut" => pathFingerhut
      case "Hut" => pathHut
      case "Ship" => pathShip
      case "Shoe" => pathShoe
      case "Wheelbarrow" => pathWheelbarrow
    }
  }


  val diceButton = new Button(Action("Würfeln") { controller.rollDice()})
  def dicePanel: FlowPanel = new FlowPanel {
    contents += diceButton
    contents += Swing.HStrut(10)
    val dice1: String = rolledDice(controller.rolledNumber._1)
    val dice2: String = rolledDice(controller.rolledNumber._2)
    contents +=  new Label() {icon = scaledImageIcon(dice1, 80, 80)}
    contents += Swing.HStrut(10)
    contents +=  new Label() {icon = scaledImageIcon(dice2, 80, 80)}
    border = EmptyBorder(5)
  }

  def rolledDice(i: Int): String = {
    i match {
      case 1 => "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Dice_1.png"
      case 2 => "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Dice_2.png"
      case 3 => "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Dice_3.png"
      case 4 => "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Dice_4.png"
      case 5 => "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Dice_5.png"
      case 6 => "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Dice_6.png"
      case _ => "src/main/scala/de/htwg/se/monopoly/aview/Gui/images/Dice_0.png"
    }
  }

  val textArea: TextArea = new TextArea {
    text = controller.actualField.toString
    resizable = false
    rows = 1
    lineWrap = true
    wordWrap = true
    editable = false
    border = LineBorder(java.awt.Color.BLACK,1)
  }
  val buyButton: Button = new Button(Action("Kaufen") {controller.buyStreet()}) {enabled = false}
  val notBuyButton: Button = new Button(Action("Nicht Kaufen"){controller.publish(new WaitForNextPlayer)}) {enabled = false}
  val nextPlayerButton: Button = new Button(Action("Zug beenden") {controller.nextPlayer()}) {enabled = false}

  def interactionPanel: GridPanel = new GridPanel(3, 1) {
    val buttonPanel: GridPanel = new GridPanel(1, 2) {
      preferredSize = new Dimension(200, 30)
      contents += buyButton
      contents += notBuyButton
      border = EmptyBorder(2, 0, 2, 0)
    }
    contents += textArea
    contents += buttonPanel
    contents += nextPlayerButton
    border = CompoundBorder(CompoundBorder(EmptyBorder(10), LineBorder(java.awt.Color.BLACK, 1)), EmptyBorder(5))
  }

  def playerPanel(namePlayer: String, index: Int, money: Int, jail: Int, figure: String, color: Color): GridBagPanel = new GridBagPanel {
    border = CompoundBorder(EmptyBorder(10), LineBorder(java.awt.Color.BLACK, 1))
    def constraints(x: Int, y: Int,
                    insets: Insets = new Insets(0, 0, 0, 0),
                    gridwidth: Int = 1, gridheight: Int = 1,
                    weightx: Double = 0.0, weighty: Double = 0.0,
                    fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None)
    : Constraints = {
      val c = new Constraints
      c.gridx = x
      c.gridy = y
      c.gridwidth = gridwidth
      c.gridheight = gridheight
      c.weightx = weightx
      c.weighty = weighty
      c.fill = fill
      c.anchor = GridBagPanel.Anchor.Center
      c.insets = insets
      c
    }

    add(shapePanel(20, 20, color = color), constraints(0, 0))
    add(new Label() {text = namePlayer}, constraints(1, 0, insets = new Insets(0, 10, 0, 10)))
    add(new Label() {text = money.toString}, constraints(2, 0, insets = new Insets(0, 10, 0, 10)))
    add(new Label() {text = jail.toString}, constraints(3, 0, insets = new Insets(0, 10, 0, 10)))
    add(new Label() {icon = scaledImageIcon(getPath(figure), 30, 30)}, constraints(4, 0, insets = new Insets(0, 10, 0, 10)))
  }


  def controllPanel() = new BoxPanel(Orientation.Vertical) {
    contents += dicePanel
    contents += Swing.VStrut(2)
    contents += interactionPanel
    border = LineBorder(java.awt.Color.BLACK, 1)
    for(n <- controller.players) {
      contents += playerPanel(n.name, n.index, n.money, n.inJail, n.figure, n.color)
    }
  }

  def board: BoxPanel = new BoxPanel(Orientation.Horizontal) {
    contents += new Label() {icon = scaledImageIcon(pathBoard, 690, 690)}
  }

  def boardPanel: GridBagPanel = new GridBagPanel {
    def constraints(x: Int, y: Int,
                    insets: Insets = new Insets(0, 0, 0, 0),
                    gridwidth: Int = 1, gridheight: Int = 1,
                    weightx: Double = 0.0, weighty: Double = 0.0,
                    fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None)
    : Constraints = {
      val c = new Constraints
      c.gridx = x
      c.gridy = y
      c.gridwidth = gridwidth
      c.gridheight = gridheight
      c.weightx = weightx
      c.weighty = weighty
      c.fill = fill
      c.anchor = GridBagPanel.Anchor.NorthWest
      c.insets = insets
      c
    }

    add(panel, constraints(0 , 0, gridwidth= 11, gridheight = 11))
    add(board, constraints(0 , 0, gridwidth= 11, gridheight = 11))
  }

  var paths = List[GeneralPath]()
  var currentPath = new GeneralPath
  val panel: Panel = new Panel {
    preferredSize = new Dimension(690, 690)
    minimumSize = new Dimension(690, 690)
    border = LineBorder(java.awt.Color.BLACK, 1)
    requestFocus()

    val figurePosition: List[(Int, Int)] =
      List((10,5), (85, 5), (140, 5), (200, 5), (255, 5), (310, 5), (365, 5), (425, 5), (480, 5), (535, 5),
        (635, 5), (615, 80), (615, 135), (615, 195), (615, 250), (615, 305), (615, 360), (615, 420), (615, 475), (615, 530),
        (615, 615), (535, 615), (480, 615), (425, 615), (365, 615), (310, 615), (255, 615), (200, 615), (140, 615), (85, 615),
        (10, 530), (10, 475), (10, 420), (10, 360), (10, 305), (10, 250), (10, 195), (10, 135), (10, 80))
    var figures: Vector[(Image, Int, Int)] = Vector[(Image, Int, Int)]() //imgX, imgY, scaledImage

    val polygonPosition: List[(Int, Int)] =
      List[(Int, Int)]((0, 0), (92, 0), (148,0), (204, 0), (260, 0), (316, 0), (372, 0), (428, 0), (484, 0), (540, 0), (596, 0),
        (612, 92), (612, 148), (612, 204), (612, 260), (612, 316), (612, 372), (612, 428), (612, 484), (612, 540), (596, 596),
        (540, 612), (484, 612), (428, 612), (372, 612), (316, 612), (260, 612), (204, 612), (148, 612), (92, 612), (0, 596),
        (0, 540), (0, 484), (0, 428), (0, 372), (0, 316), (0, 260), (0, 204), (0, 148), (0, 92))
    var polygons: Vector[(Color, Int, Int)] = Vector[(Color, Int, Int)]()

    def addFigures(): Unit = {
      for(p <- controller.players) {
        figures = figures :+ (scaledImage(getPath(p.figure), 70, 70), figurePosition(0)._1, figurePosition(0)._2)
      }
    }

    def addOwnerPolygon(color: Color): Unit = {
      val fieldIndex = controller.actualField.index
      val field = polygonPosition(fieldIndex)
      polygons = polygons :+ (color, field._1, field._2)
    }

    override def paint(g: Graphics2D): Unit = {
      for(path <- paths) {
        g.draw(path)
      }
      g.draw(currentPath)
      for(f <- figures) {
        g.drawImage(f._1, f._2, f._3, null)
      }
      for(p <- polygons) {
        val x = p._2
        val y = p._3
        g.setColor(p._1)
        g.fillPolygon(Array(x, x + 15, x), Array(y, y, y + 15), 3)
      }
    }

    listenTo(controller, buyButton)
    reactions += {
      case ButtonClicked(`buyButton`) =>
        addOwnerPolygon(controller.players(controller.currentPlayerIndex).color)
        repaint()
      case e: PlayerSet =>
        addFigures()
        repaint()
      case e: LandedOnField =>
        val oldField = controller.actualField
        val index = controller.currentPlayerIndex
        figures = figures.updated(index, (figures(index)._1, figurePosition(oldField.index)._1,  figurePosition(oldField.index)._2))
        repaint()
    }
  }

  val mainPanel = new FlowPanel {
    contents += boardPanel
    contents += controllPanel
  }

  contents = mainPanel

  menuBar = new MenuBar { menu =>
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {AddPlayerDialog(SwingGui.this, controller)})
      contents += new MenuItem(Action("Open") { })
      contents += new MenuItem(Action("Save") { })
      contents += new MenuItem(Action("Exit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo })
      contents += new MenuItem(Action("Redo") { controller.redo })
    }
  }

  visible = true

  reactions += {
    case event: NewGameEvent =>
    case event: PlayerSet => redraw
    case event: LandedOnField =>
    case event: OwnStreet =>
    case event: HandleStreet => enableButtons(b1 = true, b2 = false, b3 = false)
    case event: DiceRolled => redraw
    case event: MoneyTransaction =>
    case event: DecrementJailCounter =>
    case event: NextPlayer => enableButtons(b1 = false, b2 = false, b3 = true)
    case event: WaitForNextPlayer => redraw; enableButtons(b1 = false, b2 = true, b3 = false)
  }

  def redraw: Unit = {
    mainPanel.contents.clear()
    contents = new FlowPanel {
      contents += boardPanel
      contents += controllPanel
    }
    repaint()
  }

  def enableButtons(b1: Boolean, b2: Boolean, b3: Boolean): Unit = {
    buyButton.enabled = b1
    notBuyButton.enabled = b1
    nextPlayerButton.enabled = b2
    diceButton.enabled = b3
    textArea.text = controller.actualField.toString
  }
}
