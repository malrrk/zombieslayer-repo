# Ex10.py

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
   
# ------------------------ main ------------------------------------------
Suit = enum("SPADES", "HEARTS", "DIAMONDS", "CLUBS")
Rank = enum("ACE", "KING", "QUEEN", "JACK", "TEN", "NINE", "EIGHT", "SEVEN", "SIX")
deck = Deck(Suit.values(), Rank.values(), "cover")

cg = CardGame(900, 615, 30)
cg.addMouseListener(MyMouseListener(), GGMouse.lPress)
while True:
   hand = deck.dealingOut(1, 25)[0]
   rowLayout = RowLayout(Location(450, 80), 890)
   hand.setView(cg, rowLayout)
   hand.sort(SortType.RANKPRIORITY, True)
   hand.draw()
   cg.setStatusText("Click to get pairs, trips and quads")
   Monitor.putSleep()
   if cg.isDisposed():
      break
   pairs = hand.extractPairs()
   for i in range(len(pairs)):
      pairs[i].setView(cg, RowLayout(Location(70 + 150 * i, 230), 120))
      pairs[i].draw()

   trips = hand.extractTrips()
   for i in range(len(trips)):
      trips[i].setView(cg, RowLayout(Location(70 + 150 * i, 380), 120))
      trips[i].draw();
  
   quads = hand.extractQuads()
   for i in range(len(quads)):
      quads[i].setView(cg, RowLayout(Location(100 + 200 * i, 530), 150))
      quads[i].draw();
   
   cg.setStatusText("Click to generate the next card set.")
   Monitor.putSleep()
   # Cleanup all hands
   hand.removeAll(False)
   for h in pairs:
      h.removeAll(False)
   for h in trips:
      h.removeAll(False)
   for h in quads:
      h.removeAll(False)
 
