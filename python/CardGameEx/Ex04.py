# Ex04.py

from ch.aplu.jgamegrid import Location
from ch.aplu.jcardgame import Deck, CardGame, StackLayout, RowLayout
from ch.aplu.jcardgame.Hand import SortType
from enum import enum

def dealingOut():
   cg.setStatusText("Dealing Out...")
   hands = deck.dealingOut(nbPlayers, 0)  # All cards in talon
   talon = hands[nbPlayers]

   for i in range(nbPlayers):
      hands[i].setView(cg, RowLayout(handLocations[i], 300, 90 * i))
      hands[i].draw()
      hands[i].setTouchEnabled(True)

   talon.setView(cg, StackLayout(handLocations[nbPlayers]))
   talon.draw()

   while not talon.isEmpty() and not cg.isDisposed():
      for i in range(nbPlayers):
         top = talon.getLast()
         talon.transfer(top, hands[i], False)
         hands[i].sort(SortType.RANKPRIORITY, True)

# ------------------------ main --------------------------------------------
Suit = enum("SPADES", "HEARTS", "DIAMONDS", "CLUBS")
Rank = enum("ACE", "KING", "QUEEN", "JACK", "TEN")
deck = Deck(Suit.values(), Rank.values(), "cover");

nbPlayers = 4
handLocations = [Location(300, 525), Location(75, 300), Location(300, 75), Location(525, 300), Location(300, 300)]
  
cg = CardGame(600, 600, 30)
dealingOut()
cg.setStatusText("Dealing Out...done");
