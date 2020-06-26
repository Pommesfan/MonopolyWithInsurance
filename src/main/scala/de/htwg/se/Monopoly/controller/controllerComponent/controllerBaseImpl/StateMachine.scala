package de.htwg.se.Monopoly.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.Monopoly.controller.IController
import de.htwg.se.Monopoly.model.fieldComponent.fieldBaseImpl.{ChanceCard, SpecialField, Street}

class Context {
  var state: State = _
  state = new StartState()

  def setPlayer(): Unit = {
    state.setPlayer(this)
  }

  def nextPlayer(): Unit = {
    state.nextPlayer(this)
  }

  def rollDice(controller: IController): Unit = {
    state.handleField(this, controller)
  }

  def setState(s: State): Unit = {
    state = s
  }

  def goToJail(): Unit = {
    state.goToJail(this)
  }

  def payForJail(controller: IController): Unit = {
    state.payForJail(this)
  }

  def gameOver(controller: IController): Unit = {
    state.gameOver(this)
  }
}

trait State {
  def setPlayer(context: Context) {}
  def nextPlayer(context: Context) {}
  def handleField(context: Context, controller: IController) {}
  def goToJail(context: Context) {}
  def payForJail(context: Context) {}
  def gameOver(context: Context) {}
}

class StartState() extends State {
  override def setPlayer(context: Context): Unit = {
    context.setState(new NextPlayerState)
  }
}

class NextPlayerState() extends State {
  override def handleField(context: Context, controller: IController): Unit = {
    val players = controller.players
    if (players(controller.currentPlayerIndex).pasch == 3) {
      context.setState(new GoToJail)
      return
    }
    controller.actualField match {
      case s: Street =>
        if (s.owner == null) {
          context.setState(new BuyStreet)
        } else if (players(controller.currentPlayerIndex).index.equals(s.owner.index)) {
          context.setState(new NextPlayerState)
        } else {
          context.setState(new PayOtherPlayer)
        }
      case sp: SpecialField =>
        if (sp.index == 0) {
          context.setState(new LandedOnGo)
        } else if (sp.index == 10){
          context.setState(new VisitJail)
        } else if (sp.index == 20){
          context.setState(new FreeParking)
        } else if (sp.index == 30) {
          context.setState(new GoToJail)
        }
      case cc: ChanceCard =>
        if (cc.cardIndex == 4) {
          context.setState(new GoToJail)
        }
      case _ => context.setState(new NextPlayerState)
    }
  }

  override def goToJail(context: Context): Unit = {
    context.setState(new GoToJail)
  }

  override def payForJail(context: Context): Unit = {
    context.setState(new PayForJail)
  }

  override def gameOver(context: Context): Unit = {
    context.setState(new GameOverState)
  }
}

class BuyStreet() extends State {
  override def nextPlayer(context: Context): Unit = {
    context.setState(new NextPlayerState)
  }
}

class PayOtherPlayer() extends State {
  override def nextPlayer(context: Context): Unit = {
    context.setState(new NextPlayerState)
  }

  override def gameOver(context: Context): Unit = {
    context.setState(new GameOverState)
  }
}

class GoToJail() extends State {
  override def nextPlayer(context: Context): Unit = {
    context.setState(new NextPlayerState)
  }
}

class LandedOnGo() extends State {
  override def nextPlayer(context: Context): Unit = {
    context.setState(new NextPlayerState)
  }
}

class VisitJail() extends State {
  override def nextPlayer(context: Context): Unit = {
    context.setState(new NextPlayerState)
  }
}

class FreeParking() extends State {
  override def nextPlayer(context: Context): Unit = {
    context.setState(new NextPlayerState)
  }
}

class PayForJail() extends State {
  override def nextPlayer(context: Context): Unit =
    context.setState(new NextPlayerState)
}

class GameOverState() extends State {
}
