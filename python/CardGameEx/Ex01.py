# Ex01.py

from ch.aplu.jgamegrid import Location
from ch.aplu.jcardgame import Deck, CardGame, RowLayout, ColumnLayout, FanLayout
from enum import enum

# ------------------------ main --------------------------------------------
Suit = enum("SPADES", "HEARTS", "DIAMONDS", "CLUBS")
Rank = enum("ACE", "KING", "QUEEN", "JACK", "TEN", "NINE", "EIGHT", "SEVEN", "SIX")
deck = Deck(Suit.values(), Rank.values(), "cover");

cg = CardGame(600, 600)
hands = deck.dealingOut(4, 5)

rowLayout0 = RowLayout(Location(150, 520), 300)
hands[0].setView(cg, rowLayout0)
hands[0].draw()

rowLayout1 = RowLayout(Location(150, 370), 300)
rowLayout1.setStepDelay(10)
hands[1].setView(cg, rowLayout1)
hands[1].draw()

columnLayout0 = ColumnLayout(Location(370, 390), 400)
hands[2].setView(cg, columnLayout0)
hands[2].draw()

columnLayout1 = ColumnLayout(Location(470, 390), 400)
columnLayout1.setScaleFactor(0.7)
columnLayout1.setStepDelay(10)
hands[3].setView(cg, columnLayout1)
hands[3].draw()

fanLayout = FanLayout(Location(300, 700), 600, 250, 290)
hands[4].setView(cg, fanLayout)
hands[4].draw()


