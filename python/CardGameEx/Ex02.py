# Ex02.py

from ch.aplu.jgamegrid import Location
from ch.aplu.jcardgame import Deck, CardGame, RowLayout, StackLayout, CardListener
from ch.aplu.jcardgame.Hand import SortType
from enum import enum


# ------------------ class MyCardListener ----------------------------------
class MyCardListener(CardListener):
   def leftDoubleClicked(self, card):
      card.transfer(stock, True)

# ------------------------ main --------------------------------------------
Suit = enum("SPADES", "HEARTS", "DIAMONDS", "CLUBS")
Rank = enum("ACE", "KING", "QUEEN", "JACK", "TEN", "NINE", "EIGHT", "SEVEN", "SIX")
deck = Deck(Suit.values(), Rank.values(), "cover");

cg = CardGame(600, 600)
hands = deck.dealingOut(1, 9)
stock = hands[1]
stock.setView(cg, StackLayout(Location(300, 150)))
stock.draw()

hands[0].setView(cg, RowLayout(Location(300, 400), 500))
hands[0].sort(SortType.SUITPRIORITY, False)
hands[0].draw()
hands[0].addCardListener(MyCardListener())
hands[0].setTouchEnabled(True)
cg.setDoubleClickDelay(300)
