# MauMau2.py
# By setting isDebug = True you get a debug version with few cards, all hands are visible
# Possible test:
# - Take club-jack and click Done -> other players put jacks
# - Play club-jack and select club trump


from ch.aplu.jgamegrid import Location, GGButtonListener, Actor, GGButton, TextActor, ToolBar, ToolBarListener, ToolBarItem, GGButtonListener
from ch.aplu.jcardgame import Deck, Hand, Card, CardGame, RowLayout, StackLayout, CardListener, TargetArea, CardCover
from ch.aplu.jcardgame.Hand import SortType
from enum import *

# ------------------ class MyToolBarListener ----------------------------------
class MyToolBarListener(ToolBarListener):
   def leftPressed(self, item):
      global trumpActor
      global trump
      if item == spadeItem:
         trump = Suit.PIK
      if item == heartItem:
         trump = Suit.HERZ
      if item == diamondItem:
         trump = Suit.KARO
      if item == clubItem:
         trump = Suit.KREUZ

      trumpActor = getTrumpActor(trump)
      cg.addActor(trumpActor, trumpActorLocation)
      showTrumpSelection(False)
      setPartnerMoves()

# ------------------ class MyHandsCardListener ----------------------------------
class MyHandsCardListener(CardListener):
   def leftDoubleClicked(self, card):
      if trumpActor == None:  # No trump
         revealed = pile.getLast()
         if card.getRank() == Rank.BAUER or card.getRank() == revealed.getRank() or card.getSuit() == revealed.getSuit():
            setMouseTouchEnabled(False)
            okBtn.setLocation(hideLocation)
            card.transfer(pile, True)
         else:
            cg.setStatusText("Selected " + str(card) + " forbidden.")
      else:  # Got trump
        if card.getRank() == Rank.BAUER or card.getSuit() == trump:
           setMouseTouchEnabled(True)
           okBtn.setLocation(hideLocation)
           card.transfer(pile, True)
           removeTrumpActor()
        else:
           cg.setStatusText("Selected " + str(card) + " forbidden.")
   
   def atTarget(self, card, targetLocation):
      hands[0].draw()
      if not checkOver(0):
         if pile.getLast().getRank() == Rank.BAUER:
            showTrumpSelection(True)
         else:
            setPartnerMoves()


# ------------------ class MyTalonCardListener ----------------------------------
class MyTalonCardListener(CardListener):
   def leftDoubleClicked(self, card):
      setMouseTouchEnabled(False)
      card.setVerso(False)
      talon.setTargetArea(TargetArea(handLocations[0]))
      card.transfer(hands[0], False)
      talon.draw()

   def atTarget(self, card, targetLocation):
      if targetLocation.equals(handLocations[0]):
         card.setVerso(False)
         hands[0].sort(SortType.SUITPRIORITY, True)
         if checkTalon():
            # Check if you can play it
            top = pile.getLast()
            if card.getRank() == Rank.BAUER or card.getRank() == top.getRank() or card.getSuit() == top.getSuit():
               waitOk()
            else:
               setPartnerMoves()
      for i in range(1, nbPlayers):
         if targetLocation.equals(handLocations[i]):
            card.setVerso(True)
            hands[i].sort(SortType.SUITPRIORITY, True)

# ------------------ class MyButtonListener -------------------------------------
class MyButtonListener(GGButtonListener):
   def buttonClicked(self, button):
      okBtn.setLocation(hideLocation)
      setPartnerMoves()

# ------------------ global functions --------------------------------------
# ---------- initHands() ------------
def initHands():
   global talon
   global hands
   hands = deck.dealingOut(nbPlayers, nbStartCards, not isDebug)
   talon = hands[nbPlayers]

   top = talon.getLast()
   talon.remove(top, True)
   pile.insert(top, True)

   hands[0].sort(SortType.SUITPRIORITY, True)

   layouts = [0] * nbPlayers
   for i in range(nbPlayers):
      layouts[i] = RowLayout(handLocations[i], handWidth)
      layouts[i].setRotationAngle(90 * i)
      hands[i].setView(cg, layouts[i])
      hands[i].setTargetArea(TargetArea(pileLocation))
      if i == 0:
         layouts[i].setStepDelay(10)
      hands[i].draw()
   layouts[0].setStepDelay(0)

   for i in range(1, nbPlayers + 1):
      hands[i].setVerso(True)

   talon.setView(cg, StackLayout(talonLocation))
   talon.draw();
   pile.setView(cg, StackLayout(pileLocation))
   pile.draw()

   hands[0].addCardListener(MyHandsCardListener())
   talon.addCardListener(MyTalonCardListener())  # Player 0 reclaims card from talon
   setMyMove()

# ---------- showTrumpSelection() ---
def showTrumpSelection(show):
   if show:
      cg.setStatusText("Select a trump Suit mandatory for the next player")
      toolBar.setLocation(toolBarLocation)
      toolBarTextActor.show()
   else:
      toolBar.setLocation(hideLocation)
      toolBarTextActor.hide()

# ---------- setMouseTouchEnabled() -
def setMouseTouchEnabled(enable):
   talon.setTouchEnabled(enable)
   hands[0].setTouchEnabled(enable)

# ---------- setMyMove() ------------
def setMyMove():
   setMouseTouchEnabled(True)
   cg.setStatusText(info)

# ---------- waitOk()----------------
def waitOk():
   setMouseTouchEnabled(True)
   cg.setStatusText("Press 'Done' to continue or double click one of your cards to move it to the pile")
   okBtn.setLocation(btnLocation)

# ---------- setPartnerMoves() ------
def setPartnerMoves():
   global isPartnerMoves
   isPartnerMoves = True
   cg.setStatusText("Wait other player's draw.")

# ---------- selectTrumpActor() -----
def selectTrumpActor(nbPlayer):
   global trump
   global trumpActor
   max = 0
   maxSuit = None
   for suit in Suit.values():
      nb = hands[nbPlayer].getNumberOfCardsWithSuit(suit)
      if nb > max:
          max = nb
          maxSuit = suit
   trump = maxSuit
   trumpActor = getTrumpActor(trump)
   cg.addActor(trumpActor, trumpActorLocation)
   
# ---------- simulateMove() ---------
def simulateMove(nbPlayer):
   for card in hands[nbPlayer].getCardList():
      if card.getRank() == Rank.BAUER:  # Yes, play it
         removeTrumpActor()
         card.setVerso(False)
         card.transfer(pile, True)
         hands[nbPlayer].sort(SortType.SUITPRIORITY, True)
         if checkOver(nbPlayer):
            return True
         selectTrumpActor(nbPlayer)   
         cg.delay(500)
         return True

   # Get list of cards that are allowed
   allowed = []
   if trumpActor == None:  # No trump imposed
      revealed = pile.getLast()
      for card in hands[nbPlayer].getCardList():
         if card.getRank() == revealed.getRank() or card.getSuit() == revealed.getSuit():
            allowed.append(card)
   else:  # Trump imposed
      for card in hands[nbPlayer].getCardList():
         if card.getSuit() == trump:
            allowed.append(card)
   talon.setTargetArea(TargetArea(handLocations[nbPlayer]))
   if len(allowed) == 0: # Can't play a card, retrieve one from talon
      card = talon.getLast()
      card.transfer(hands[nbPlayer], True)
      removeTrumpActor()
      talon.draw()
      card.setVerso(True)
      cg.delay(500)
      # Check if we can play it
      top = pile.getLast()
      if card.getRank() == Rank.BAUER or card.getRank() == top.getRank() or card.getSuit() == top.getSuit():
         card.transfer(pile, True)
         card.setVerso(False)
      if card.getRank() == Rank.BAUER:   
         selectTrumpActor(nbPlayer)   
      return checkTalon()

   selectedCard = allowed[0]   # Other strategy here
   selectedCard.setVerso(False)
   selectedCard.transfer(pile, True)
   removeTrumpActor()
   hands[nbPlayer].sort(SortType.SUITPRIORITY, True)
   return True

# ---------- checkTalon -------------
def checkTalon():
# Returns false, if check fails because there are no cards available
   if talon.isEmpty():  # Talon empty, need to shuffle
      if pile.getNumberOfCards() < 2:
         cg.setStatusText("Fatal error: No cards available for talon")
         doPause();
         cg.setMouseEnabled(False)
         return False

      # Show info text
      actor = Actor("sprites/reshuffle.gif")
      cg.addActor(actor, textLocation);

      # Move animated card cover from playing pile to talon
      cardCover = CardCover(cg, pileLocation, deck, 1, 0, False)
      cardCover.slideToTarget(talonLocation, 2, False, True) # On bottom

      # Save card on pile top and remove it
      topCard = pile.getLast()
      pile.remove(topCard, False)
      # Shuffle cards
      pile.shuffle(False)
      # Hide all pictures
      pile.setVerso(True)
      # Insert into talon
      for card in pile.getCardList():
         talon.insert(card, False)
      #  Cleanup playing pile
      pile.removeAll(False)
      # Insert saved card
      pile.insert(topCard, False)
      # Redraw piles
      pile.draw()
      talon.draw()

      # Remove info text
      cg.delay(1000)
      actor.removeSelf()
   return True

# ---------- checkOver() ------------
def checkOver(nbPlayer):
   if hands[nbPlayer].isEmpty():
      cg.addActor(Actor("sprites/gameover.gif"), textLocation)
      cg.setStatusText("Game over. Winner is player: " + str(nbPlayer))
      for i in range(nbPlayers):
         hands[i].setVerso(False)   # Show player hands
      cg.refresh()
      cg.setMouseEnabled(False)
      cg.doPause()
      return True
   return False

# ---------- getTrumpActor() --------
def getTrumpActor(suit):
   if suit == Suit.KREUZ:
      return Actor("sprites/bigclub.gif")
   if suit == Suit.HERZ:
      return Actor("sprites/bigheart.gif")
   if suit == Suit.KARO:
      return Actor("sprites/bigdiamond.gif")
   if suit == Suit.PIK:
      return Actor("sprites/bigspade.gif")
   return None

# ---------- removeTrumpActor() -----
def removeTrumpActor():
   global trumpActor
   global trump
   if trumpActor != None:
      trumpActor.removeSelf()
      trumpActor = None
      trump = None

# ------------------------ main --------------------------------------------
isDebug = False

Suit = enum("KREUZ", "HERZ", "KARO", "PIK")
if isDebug:
   Rank = enum("ASS", "KOENIG", "DAME", "BAUER", "ZEHN")
else:
   Rank = enum("ASS", "KOENIG", "DAME", "BAUER", "ZEHN", "NEUN", "ACHT", "SIEBEN", "SECHS")
deck = Deck(Suit.values(), Rank.values(), "cover")

# ------------ Locations ------------------
handLocations = [Location(300, 525), Location(75, 300), Location(300, 75), Location(525, 300)]
talonLocation = Location(250, 300)
pileLocation = Location(350, 300)
textLocation = Location(300, 400)
trumpActorLocation = Location(350, 200)
toolBarLocation = Location(470, 567)
btnLocation = Location(100, 580)
hideLocation = Location(-500, - 500)
toolBarTextLocation = Location(502, 550)
# ------------------------------------------

version = "3.1"

Card.noVerso = isDebug    # For debugging, library must be reloaded to take effect
info = "It's your move. Hint: Double click on one of your cards or on the talon."
nbPlayers = 4
   
if isDebug:
   nbStartCards = 3
else:
   nbStartCards = 5
handWidth = 300
trumpActor = None
trump = None
hands = None
talon = None
thinkingTime = 1200  # ms
pile = Hand(deck)
isPartnerMoves = False
okBtn = GGButton("sprites/done30.gif", True)
toolBarTextActor = Actor("sprites/text_item.png")
spadeItem = ToolBarItem("sprites/spades_item.png", 2)
heartItem = ToolBarItem("sprites/hearts_item.png", 2)
diamondItem = ToolBarItem("sprites/diamonds_item.png", 2)
clubItem = ToolBarItem("sprites/clubs_item.png", 2)
Item = ToolBarItem("sprites/clubs_item.png", 2)

cg = CardGame(600, 600, 30)
cg.setTitle("Mau-Mau (V" + version + ") constructed with JGameGrid (www.aplu.ch)")
cg.setStatusText("Initializing...")
initHands()
cg.addActor(okBtn, hideLocation)
okBtn.addButtonListener(MyButtonListener())
toolBarTextActor.hide()
cg.addActor(toolBarTextActor, toolBarTextLocation)
toolBar = ToolBar(cg)
toolBar.addItem(spadeItem, heartItem, diamondItem, clubItem)
toolBar.show(hideLocation)
toolBar.addToolBarListener(MyToolBarListener())

while not cg.isDisposed():
   isGameOver = False
   if isPartnerMoves:
      isPartnerMoves = False
      for i in range(1, nbPlayers):
         cg.setStatusText("Player " + str(i) + " thinking...")
         cg.delay(thinkingTime)
         if not simulateMove(i) or checkOver(i):  #  talon empty or game over
            isGameOver = True
            break
      if not isGameOver:
         setMyMove()
   cg.delay(100)
 
