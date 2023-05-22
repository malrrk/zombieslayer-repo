# Ex09.py

from ch.aplu.jgamegrid import Location, ToolBar, ToolBarListener, ToolBarItem, ToolBarSeparator, ToolBarStack
from ch.aplu.jcardgame import Deck, CardGame, RowLayout, Hand
from ch.aplu.jcardgame.Hand import SortType
from enum import *

# ------------------ class MyToolBarListener -------------------------------
class MyToolBarListener(ToolBarListener):
   def leftPressed(self, item):
      if item == upBtn:
         upBtn.show(1)
         Hand.randomBatchTransfer(numbers.getItemId(), lowerHand, upperHand, False)
         sortAndDrawHands()
  
      if item == downBtn:
         downBtn.show(1)
         Hand.randomBatchTransfer(numbers.getItemId(), upperHand, lowerHand, False)
         sortAndDrawHands()
    
      if item == numbers:
         item.showNext()
       
   def leftReleased(self, item):
      if item == upBtn:
         upBtn.show(0)
      if item == downBtn:
         downBtn.show(0)

# ------------------ global functions --------------------------------------
def initToolBar():
   global upBtn, downBtn, numbers
   upBtn =ToolBarItem("sprites/up30.gif", 2)
   numbers = ToolBarStack("sprites/number30.gif", 10)
   downBtn = ToolBarItem("sprites/down30.gif", 2)
   toolBar = ToolBar(cg)
   toolBar.addItem(upBtn, numbers, downBtn)
   toolBar.show(Location(160, 185))
   numbers.show(3)  # Default start number
   toolBar.addToolBarListener(MyToolBarListener())

def sortAndDrawHands():
   upperHand.sort(SortType.RANKPRIORITY, True)
   lowerHand.sort(SortType.RANKPRIORITY, True)

# ------------------------ main --------------------------------------------
Suit = enum("SPADES", "HEARTS", "DIAMONDS", "CLUBS")
Rank = enum("ACE", "KING", "QUEEN", "JACK", "TEN", "NINE", "EIGHT", "SEVEN", "SIX")
deck = Deck(Suit.values(), Rank.values(), "cover")

upBtn = None
downBtn = None
numbers = None

cg = CardGame(460, 400)
hands = deck.dealingOut(2, 5, True)
upperHand = hands[0]
upperHand.setView(cg, RowLayout(Location(230, 100), 400))
lowerHand = hands[1]
lowerHand.setView(cg, RowLayout(Location(230, 300), 400))
sortAndDrawHands()
initToolBar()

