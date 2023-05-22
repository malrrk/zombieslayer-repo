# Ex08.py

from ch.aplu.jgamegrid import X11Color, Location, ToolBar, ToolBarListener, ToolBarItem, ToolBarSeparator, ToolBarStack
from ch.aplu.jcardgame import Deck, CardGame, RowLayout
from enum import *

# ------------------ class MyToolBarListener -------------------------------
class MyToolBarListener(ToolBarListener):
   def leftPressed(self, item):
      if item == cutBtn:
         cutBtn.show(1)
         hand.cut(numbers.getItemId(), True)
      else:
         item.showNext()
   def leftReleased(self, item):
      if item == cutBtn:
         item.show(0)

# ------------------ global functions --------------------------------------
def initToolBar():
   global cutBtn
   global numbers
   numbers = ToolBarStack("sprites/number30.gif", 10)
   separator = ToolBarSeparator(2, 30, X11Color("black"))
   cutBtn = ToolBarItem("sprites/cutBtn.gif", 3)
   toolBar = ToolBar(cg)
   toolBar.addItem(numbers, separator, cutBtn)
   toolBar.show(Location(7, 160))
   toolBar.addToolBarListener(MyToolBarListener())

# ------------------------ main --------------------------------------------
Suit = enum("SPADES", "HEARTS", "DIAMONDS", "CLUBS")
Rank = enum("ACE", "KING", "QUEEN", "JACK", "TEN", "NINE", "EIGHT", "SEVEN", "SIX")

deck = Deck(Suit.values(), Rank.values(), "cover")
hand = deck.dealingOut(1, 5, True)[0]

cutBtn = None
numbers = None

cg = CardGame(300, 200)
hand.setView(cg, RowLayout(Location(150, 80), 290))
hand.draw()
initToolBar()

