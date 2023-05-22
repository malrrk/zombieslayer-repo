# Ex11.py

from ch.aplu.jgamegrid import Location, GGMouseListener, GGMouse
from ch.aplu.jcardgame import Deck, CardGame, RowLayout, Hand
from ch.aplu.jcardgame.Hand import SortType
from ch.aplu.util import Monitor
from enum import *

# ------------------ class MyMouseListener ---------------------------------
class MyMouseListener(GGMouseListener):
   def mouseEvent(self, mouse):
      Monitor.wakeUp()
      return True
   
# ------------------------ main --------------------------------------------
Suit = enum("SPADES", "HEARTS", "DIAMONDS", "CLUBS")
Rank = enum("ACE", "KING", "QUEEN", "JACK", "TEN", "NINE", "EIGHT", "SEVEN", "SIX")
deck = Deck(Suit.values(), Rank.values(), "cover")

cg = CardGame(900, 615, 30)
cg.addMouseListener(MyMouseListener(), GGMouse.lPress)
while True:
   hand = deck.dealingOut(1, 25)[0]

   hand.setView(cg, RowLayout(Location(450, 80), 890))
   hand.sort(SortType.RANKPRIORITY, True)

   sequence3 = hand.extractSequences(Suit.HEARTS, 3)
   for i in range(len(sequence3)):
      sequence3[i].setView(cg, RowLayout(Location(70 + 150 * i, 230), 120))
      sequence3[i].draw()
 
   sequence4 = hand.extractSequences(Suit.HEARTS, 4)
   for i in range(len(sequence4)):
      sequence4[i].setView(cg, RowLayout(Location(70 + 150 * i, 380), 120))
      sequence4[i].draw()

   sequence5 = hand.extractSequences(Suit.HEARTS, 5)
   for i in range(len(sequence5)):
      sequence5[i].setView(cg, RowLayout(Location(100 + 200 * i, 530), 150))
      sequence5[i].draw()
 
   cg.setStatusText("Click to generate the next card set.");
   Monitor.putSleep()
   if cg.isDisposed():
      break
 
   # Cleanup all hands
   hand.removeAll(False)
   for h in sequence3:
      h.removeAll(False)
   for h in sequence4:
      h.removeAll(False)
   for h in sequence5:
      h.removeAll(False)
 
