# Ex05.py

from ch.aplu.jgamegrid import Location
from ch.aplu.jcardgame import Deck, CardGame, CardCover, RowLayout
from ch.aplu.jcardgame.Hand import SortType
from enum import enum

# ------------------ global functions --------------------------------------
def dealingOut():
   global hands
   global talon
   cg.setStatusText("Dealing out in batches...")
   hands = deck.dealingOut(nbPlayers, 0)  # All cards in shuffled talon
   talon = hands[nbPlayers]

   for i in range(nbPlayers):
      hands[i].setView(cg, RowLayout(handLocations[i], 300, 90 * i))
      hands[i].draw()

   talonCover = CardCover(cg, talonLocation, deck, 1, 0)
  
   while not talon.isEmpty() and not cg.isDisposed():
      for i in range(nbPlayers):
         for k in range(packetSize):
            hands[i].insert(talon.getLast(), False)
            talon.removeLast(False)
         cardCover = CardCover(cg, talonLocation, deck, 1, 0)
         cardCover.slideToTarget(handLocations[i], 10, True, True)
         cardCover.removeSelf()
         if i == 0:
            hands[i].setVerso(False)
            hands[i].sort(SortType.RANKPRIORITY, False)
         else:
            hands[i].setVerso(True)
         hands[i].draw()
   
   talonCover.removeSelf()
   hands[0].setTouchEnabled(True)

# ------------------------ main --------------------------------------------
Suit = enum("SPADES", "HEARTS", "DIAMONDS", "CLUBS")
Rank = enum("ACE", "KING", "QUEEN", "JACK", "TEN", "NINE", "EIGHT", "SEVEN", "SIX")
deck = Deck(Suit.values(), Rank.values(), "cover");

hands = None
talon = None
nbPlayers = 4
handLocations = [Location(300, 525), Location(75, 300), Location(300, 75), Location(525, 300)]
talonLocation = Location(300, 300)
packetSize = 3
  
cg = CardGame(600, 600, 30)
dealingOut()
cg.setStatusText("Dealing Out...done")
