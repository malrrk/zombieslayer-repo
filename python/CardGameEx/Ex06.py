# Ex06.py

from ch.aplu.jgamegrid import Location
from ch.aplu.jcardgame import Deck, Hand, CardGame, StackLayout, TargetArea, CardListener
from ch.aplu.jcardgame.Hand import SortType
from enum import enum

# ------------------ class BitsCardListener --------------------------------
class BitsCardListener(CardListener):
   def atTarget(self, card, loc):
      card.getHand().draw()

# ------------------ class HandsCardListener -------------------------------
class HandsCardListener(CardListener):
   def __init__(self, i):
      self.i = i

   def leftDoubleClicked(self, card):
      card.setVerso(False)
      card.transfer(bids[self.i], True)
   
   def rightClicked(self, card):
      transferToStock(self.i)

# ------------------ global functions --------------------------------------
def dealingOut():
   global hands
   global talon
   cg.setStatusText("Dealing out...")
   hands = deck.dealingOut(nbPlayers, 0)  # All cards in talon
   talon = hands[nbPlayers]
   talon.setVerso(True)

   for i in range(nbPlayers):
      hands[i].setView(cg, StackLayout(handLocations[i], 90 * i))
      hands[i].draw()
      hands[i].setTouchEnabled(True)

   talon.setView(cg, StackLayout(handLocations[nbPlayers]))
   talon.draw()

   while not talon.isEmpty():
      for i in range(nbPlayers):
         top = talon.getLast()
         if i == 0:
            top.setVerso(False)
         else:
            top.setVerso(True)
         talon.transfer(top, hands[i], True)

def initBids():
   for i in range(nbPlayers):
      bids[i] = Hand(deck)
      bids[i].setView(cg, StackLayout(bidLocations[i]))
      bids[i].addCardListener(BitsCardListener())
      hands[i].setTargetArea(TargetArea(bidLocations[i]))
      hands[i].setTouchEnabled(True)
      hands[i].addCardListener(HandsCardListener(i))

def initStocks():
   for i in range(nbPlayers):
      stocks[i] = Hand(deck)
      if i == 0 or i == 2:
        rotationAngle = 0
      else:
        rotationAngle = 90
      stocks[i].setView(cg, StackLayout(stockLocations[i], rotationAngle))

def transferToStock(player):
   for i in range(nbPlayers):
      bids[i].setTargetArea(TargetArea(stockLocations[player]))
      card = bids[i].getLast()
      if card == None:
         continue
      card.setVerso(True)
      bids[i].transferNonBlocking(card, stocks[player], True)

# ------------------------ main --------------------------------------------
Suit = enum("SPADES", "HEARTS", "DIAMONDS", "CLUBS")
Rank = enum("ACE", "KING", "QUEEN", "JACK", "TEN")
deck = Deck(Suit.values(), Rank.values(), "cover");

nbPlayers = 4
handLocations = [Location(300, 525), Location(75, 300), Location(300, 75), Location(525, 300), Location(300, 300)]
bidLocations = [Location(300, 350), Location(250, 300), Location(300, 250), Location(350, 300)]
stockLocations = [Location(400, 500), Location(100, 400), Location(200, 100), Location(500, 200)]

hands = None
bids = [0] * nbPlayers
stocks = [0] * nbPlayers
talon = None
  
cg = CardGame(600, 600, 30)
dealingOut()
cg.setStatusText("Dealing out...done. Double click to play.")
initBids()
initStocks()
