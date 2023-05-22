# Ex03.py

from ch.aplu.jgamegrid import Location, GGMouse, GGMouseTouchListener
from ch.aplu.jcardgame import Deck, CardGame, RowLayout, StackLayout
from ch.aplu.jcardgame.Hand import SortType
from java.awt import Point
from enum import enum

Suit = enum("SPADES", "HEARTS", "DIAMONDS", "CLUBS")
Rank = enum("ACE", "KING", "QUEEN", "JACK", "TEN")
deck = Deck(Suit.values(), Rank.values(), "cover");

class MyMouseTouchListener(GGMouseTouchListener):
   def mouseTouched(self, actor, mouse, spot):
      global hotspot
      event = mouse.getEvent()
      if event == GGMouse.lPress:
         hotspot = spot;
      if event == GGMouse.lDrag:
         actor.setLocation(Location(mouse.getX() - hotspot.x, mouse.getY() - hotspot.y))
         if actor.getLocation().getDistanceTo(handLocation) < 50:
            insertInHand(actor)
      if event == GGMouse.lRelease:
         insertInHand(actor);

def takeFromTalon():
   top = talon.getLast()
   if top == None:  # talon empty
      return
   talon.remove(top, False)     # Remove from talon and game grid
   addActor(top.getCardActor()) # Reinsert into game grid

def insertInHand(actor):
   actor.removeSelf()
   card = actor.getCard()
   hand.insert(card, False)
   hand.sort(SortType.RANKPRIORITY, True)
   takeFromTalon()

def addActor(cardActor):
   cg.addActor(cardActor, Location(talonLocation))
   cardActor.addMouseTouchListener(MyMouseTouchListener(), GGMouse.lPress | GGMouse.lDrag | GGMouse.lRelease, True)

# ------------------------ main --------------------------------------------
handLocation = Location(250, 100)
talonLocation = Location(250, 300)
hotSpot = Point(0, 0)
  
cg = CardGame(500, 400, 30)
hands = deck.dealingOut(1, 2)  # only two cards in hand
hand = hands[0]
hand.setView(cg, RowLayout(handLocation, 400))
talon = hands[1]
talon.setView(cg, StackLayout(talonLocation))
hand.draw()
talon.draw()
takeFromTalon()
cg.setStatusText("Drag cards from card talon to the hand");
