package de.htwg.se.MonopolyWithInsurance.aview.Gui

import java.awt.geom.GeneralPath
import de.htwg.se.MonopolyWithInsurance.controller.{BoughtStreet, DecrementJailCounter, DiceRolled, ExitGame, GameOver, GoToJailEvent, HandleChanceCard, HandleStreet, IController, InsurancePays, LandedOnField, LoadEvent, MoneyTransaction, NewGameEvent, NextPlayer, NotEnoughMoney, OwnStreet, PayToLeave, PlayerSet, RedoEvent, SignInsurance, UndoEvent, UnsignInsurance, WaitForNextPlayer}

import java.awt.{Color, Image}
import java.io.File
import de.htwg.se.MonopolyWithInsurance.controller.controllerComponent.controllerBaseImpl.GameOverState
import de.htwg.se.MonopolyWithInsurance.model.fieldComponent.fieldBaseImpl.Street

import scala.swing.{BoxPanel, Button, Dialog, Dimension, FlowPanel, Graphics2D, Label, Menu, MenuBar, Orientation, Panel, ScrollPane, Swing, TextArea, _}
import javax.swing.ImageIcon
import javax.swing.border.BevelBorder
import scala.swing.Swing.{CompoundBorder, EmptyBorder, LineBorder}
import scala.swing.event.{ButtonClicked, Key}

class SwingGui(controller: IController) extends MainFrame {
  listenTo(controller)
  val prefix = "src/main/scala/de/htwg/se/MonopolyWithInsurance/"

  val pathCar = prefix + "aview/Gui/images/Car.png"
  val pathCat = prefix +"aview/Gui/images/Cat.png"
  val pathDog = prefix +"aview/Gui/images/Dog.png"
  val pathFingerhut = prefix +"aview/Gui/images/Fingerhut.png"
  val pathHut = prefix +"aview/Gui/images/hut.png"
  val pathShip = prefix +"aview/Gui/images/Ship.png"
  val pathShoe = prefix +"aview/Gui/images/Shoe.png"
  val pathWheelbarrow = prefix +"aview/Gui/images/wheelbarrow.png"

  val pathBoard = prefix +"aview/Gui/images/Monopoly_board.jpg"
  val gameOverPath = prefix +"aview/Gui/images/GameOver.png"

  val transparent = new Color(0, 0, 0, 0)
  title = "Monopoly mit Versicherung"
  preferredSize = new Dimension(1050, 770)

  def shapePanel(width: Int, height: Int, color: Color = transparent): Panel = new Panel {
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

  /**
   * DicePanel, teil vom ControllPanel
   * @return
   */
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

  /**
   * HistoryField
   * @return
   */
  val textArea: TextArea = new TextArea {
    text = "%s".format(controller.actualField.name)
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
  val goToJailButton: Button = new Button(Action("Gehe ins Gefängnis!") {controller.publish(new WaitForNextPlayer)}) {enabled = false}
  val payReleaseButton: Button = new Button(Action("Freikaufen (50$)") {controller.payToLeaveJail(controller.getActualPlayer)}) {enabled = false}
  val buttonInsuranceA: Button = new Button(Action("Versicherung A") {controller.setInsurance(1)}) {enabled = true}
  val buttonInsuranceB: Button = new Button(Action("Versicherung B") {controller.setInsurance(2)}) {enabled = true}

  def interactionPanel: GridPanel = new GridPanel(5, 1) {
    val buyBottunPanel: GridPanel = new GridPanel(1, 2) {
      preferredSize = new Dimension(200, 30)
      contents += buyButton
      contents += notBuyButton
      border = EmptyBorder(2, 0, 2, 0)
    }
    val jailButtonPanel: GridPanel = new GridPanel(1, 2) {
      preferredSize = new Dimension(200, 30)
      contents += goToJailButton
      contents += payReleaseButton
      border = EmptyBorder(2, 0, 2, 0)
    }
    val insuranceButtonPanel: GridPanel = new GridPanel(1, 2) {
      preferredSize = new Dimension(200, 30)
      contents += buttonInsuranceA
      contents += buttonInsuranceB
      border = EmptyBorder(2, 0, 2, 0)
    }
    contents += textArea
    contents += buyBottunPanel
    contents += jailButtonPanel
    contents += nextPlayerButton
    contents += insuranceButtonPanel
    border = CompoundBorder(CompoundBorder(EmptyBorder(10), LineBorder(java.awt.Color.BLACK, 1)), EmptyBorder(5))
  }

  /**
   * PlayerPanel
   * @param namePlayer
   * @param money
   * @param jail
   * @param figure
   * @return
   */

  def playerPanel(namePlayer: String, index: Int, money: Int, jail: Int, figure: String, color: Color): GridBagPanel = new GridBagPanel {
    if (controller.currentPlayerIndex == index & !controller.context.state.isInstanceOf[GameOverState]) {
      border = CompoundBorder(EmptyBorder(10), new BevelBorder(BevelBorder.RAISED))
    } else {
      border = CompoundBorder(EmptyBorder(10), EmptyBorder(1))
    }
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

  /**
   * ControllPanel with dice, history and Player
   * @return
   */
  def controllPanel() = new BoxPanel(Orientation.Vertical) {
    contents += dicePanel
    contents += Swing.VStrut(2)
    contents += interactionPanel
    border = LineBorder(java.awt.Color.BLACK, 1)
    for(n <- controller.players) {
      contents += playerPanel(n.name, n.index, n.money, n.inJail, n.figure, n.color)
    }
  }

  /**
   * Board Panel
   * @return
   */

  def board: BoxPanel = new BoxPanel(Orientation.Horizontal) {
    contents += new Label() {icon = scaledImageIcon(pathBoard, 690, 690)}
  }

  def gameHistory(v: Vector[String]) = new TextArea(10, 45) {
    maximumSize = new Dimension(200, 300)
    minimumSize = new Dimension(200, 300)
    for(v <- v) {
      text += v
    }
    resizable = false
    lineWrap = true
    wordWrap = true
    editable = false

    background = Color.WHITE
  }

  reactions += {
    case e: NewGameEvent => controller.history = controller.history :+ " Wilkommen zu einer neuen Runde Monopoly!\n"
    case e: PlayerSet => controller.history = controller.history :+ controller.getActualPlayer.name + " darf beginnen.\n"
    case e: DiceRolled => controller.history = controller.history :+ "Du hast eine " + controller.rolledNumber._1 + " und eine " + controller.rolledNumber._2 + " gewürfelt.\n"
    case e: HandleStreet => controller.history = controller.history :+ "Möchten Sie diese Straße kaufen?\n"
    case e: OwnStreet => controller.history = controller.history :+ "Diese Straße gehört dir.\n"
    case e: MoneyTransaction => controller.history = controller.history :+ "Zahle " + e.money + "$\n"
    case e: DecrementJailCounter => controller.history = controller.history :+ "Warte " + (e.counter +1) + " Runden bis du aus dem Gefägnis frei kommst\noder kaufe dich in der nächsten Runde frei.\n"
    case e: NextPlayer => controller.history = controller.history :+ controller.getActualPlayer.name +" ist dran.\n"
    case e: WaitForNextPlayer => controller.history = controller.history :+ "Zug beenden?\n\n"
    case e: LandedOnField => controller.history = controller.history :+ "Du landest auf Feld Nummer " + controller.actualField
    case e: GoToJailEvent => controller.history = controller.history :+ "Gehe ins Gefängnis (3xPasch /Feld Gehen ins Gefängnis /Ereigniskarte)\n"
    case e: PayToLeave => controller.history = controller.history :+ "Du befindest dich im Gefägnis.\nPasch würfeln oder Freikaufen.\n"
    case e: SignInsurance => controller.history = controller.history :+ ("Startgebühr Versicherungsabschluss: " + e.amount + "\n")
    case e: UnsignInsurance => controller.history = controller.history :+ "Versicherung gekündigt\n"
    case e: InsurancePays => controller.history = controller.history :+ "Die Versicherung zahlt: " + e.amount + "€\n"
    case e: HandleChanceCard => controller.history = controller.history :+ e.message
    case e: NotEnoughMoney => controller.history = controller.history :+ "Du kannst diese Straße nicht kaufen, da du nicht genug Geld besitzt.\n"
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
    add(shapePanel(120, 220), constraints(0, 0))
    add(new ScrollPane(gameHistory(controller.history)), constraints(1, 1))
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
        (615, 615), (535, 615), (480, 615), (425, 615), (365, 615), (310, 615), (255, 615), (200, 615), (140, 615), (85, 615), (10, 615),
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
        figures = figures :+ (scaledImage(getPath(p.figure), 70, 70), figurePosition(p.currentPosition)._1, figurePosition(p.currentPosition)._2)
      }
    }

    def addOwnerPolygon(): Unit = {
      for (field <- controller.board.fields) {
        val position = polygonPosition(field.index)
        polygons = polygons :+ (transparent, position._1, position._2)
      }
    }

    def updatePlayer(): Unit = {
      for(p <- controller.players) {
        if (p.inJail != 0) {
          figures = figures.updated(p.index, (figures(p.index)._1, 590, 15))
        } else {
          figures = figures.updated(p.index, (figures(p.index)._1, figurePosition(p.currentPosition)._1, figurePosition(p.currentPosition)._2))
        }
      }
      repaint()
    }

    def updateOwnerPolygon(): Unit = {
      for (field <- controller.board.fields) {
        field match {
          case s: Street =>
            if(s.owner != null) {
              polygons = polygons.updated(s.index, (s.owner.color, polygonPosition(s.index)._1, polygonPosition(s.index)._2))
            } else {
              polygons = polygons.updated(s.index, (transparent, polygonPosition(s.index)._1, polygonPosition(s.index)._2))
            }
          case _ =>
        }
      }
      repaint()
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

    listenTo(controller, buyButton, goToJailButton)
    reactions += {
      case e: BoughtStreet => updateOwnerPolygon()
      case e: PlayerSet =>
        addFigures()
        addOwnerPolygon()
        repaint()
      case e: LandedOnField => updatePlayer()
      case e: UndoEvent => updatePlayer(); updateOwnerPolygon()
      case e: RedoEvent => updatePlayer(); updateOwnerPolygon()
      case ButtonClicked(`goToJailButton`) => updatePlayer()
      case e: LoadEvent => addFigures(); addOwnerPolygon(); updateOwnerPolygon(); repaint
    }
  }

  case class GameOverDialog(parent: Window, controller: IController) extends Dialog(parent) {
    title = "Game Over"
    preferredSize = new Dimension(500, 700)
    visible = true

    val gameOverPanel:FlowPanel = new FlowPanel {
      contents += new Label {icon = scaledImageIcon(gameOverPath, 300, 150)}
    }

    val sortedPlayerPanel: GridBagPanel = new GridBagPanel {
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
      preferredSize = new Dimension(300, 500)
      var place = 0
      for(p <- controller.players) {
        place += 1
        add(new Label(place.toString + ". Platz"), constraints(0, place, fill = GridBagPanel.Fill.Both))
        add(playerPanel(p.name, p.index, p.money, p.inJail, p.figure, p.color), constraints(1, place, fill = GridBagPanel.Fill.Both))
      }
    }

    val buttonPanel: FlowPanel = new FlowPanel {
      maximumSize = new Dimension(50, 150)
      val exitButton = new Button(Action("Beenden") {dispose(); controller.publish(new ExitGame)})
      contents += exitButton
    }

    contents = new BorderPanel {
      add(gameOverPanel, BorderPanel.Position.North)
      add(sortedPlayerPanel, BorderPanel.Position.Center)
      add(buttonPanel, BorderPanel.Position.South)
    }

  }


  val mainPanel = new FlowPanel {
    contents += boardPanel
    contents += controllPanel
  }

  contents = mainPanel

  val menuItemUndo: MenuItem = new MenuItem(Action("Undo") { controller.undo() }) {enabled = false}
  val menuItemRedo: MenuItem = new MenuItem(Action("Redo") { controller.redo() }) {enabled = false}

  val menuItemSave: MenuItem = new MenuItem(Action("Save") { controller.save }) {enabled = false}
  val menuItemLoad: MenuItem = new MenuItem(Action("Load last Game") { controller.load }) {enabled = false}

  menuBar = new MenuBar { menu =>
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {AddPlayerDialog(SwingGui.this, controller)})
      contents += new MenuItem(Action("Open") { })
      contents += menuItemSave
      contents += menuItemLoad
      contents += new MenuItem(Action("Exit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += menuItemUndo
      contents += menuItemRedo
    }
  }

  visible = true

  reactions += {
    case e: NewGameEvent => enableButtons(b0 = true)
    case e: UndoEvent => enableButtons(b3 = true); redraw
    case e: RedoEvent => enableButtons(b2 = true); redraw
    case e: PlayerSet => enableButtons(b3 = true); redraw
    case e: LandedOnField => redraw
    case e: OwnStreet => redraw
    case e: HandleStreet => redraw
      enableButtons(b1 = true)
    case e: DiceRolled => redraw
    case e: MoneyTransaction => redraw
    case e: DecrementJailCounter =>
    case e: NextPlayer => enableButtons(b3 = true, b6 = true);
      enable_insurance_buttons(e.insurance == 1 || e.insurance == 0, e.insurance == 2 || e.insurance == 0);
      redraw
    case e: WaitForNextPlayer => enableButtons(b2 = true, b6 = true); redraw
    case e: GoToJailEvent => enableButtons(b4 = true); redraw
    case e: PayToLeave => enableButtons(b3 = true, b5 = true)
    case e: GameOver => GameOverDialog(SwingGui.this, controller)
    case e: ExitGame => System.exit(1)
    case e: LoadEvent => enableButtons(b3 = true); redraw
    case e: SignInsurance => enable_insurance_buttons(false, false)
    case e: UnsignInsurance => enable_insurance_buttons(false, false)
  }

  private def enable_insurance_buttons(b1: Boolean, b2: Boolean): Unit = {
    buttonInsuranceA.enabled = b1
    buttonInsuranceB.enabled = b2
  }

  def redraw: Unit = {
    mainPanel.contents.clear()
    contents = new FlowPanel {
      contents += boardPanel
      contents += controllPanel
    }
    repaint()
  }

  def enableButtons(b0: Boolean = false, b1: Boolean = false, b2: Boolean = false, b3: Boolean = false,
                    b4: Boolean = false, b5: Boolean = false, b6: Boolean = false): Unit = {
    buyButton.enabled = b1
    notBuyButton.enabled = b1
    nextPlayerButton.enabled = b2
    diceButton.enabled = b3
    goToJailButton.enabled = b4
    payReleaseButton.enabled = b5
    textArea.text = controller.actualField.name
    menuItemUndo.enabled = b6
    menuItemRedo.enabled = b6
    menuItemSave.enabled = b3
    menuItemLoad.enabled = b0
  }
}
