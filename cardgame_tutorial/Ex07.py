# Ex07.py

from ch.aplu.jgamegrid import X11Color, Location, ToolBar, ToolBarListener, ToolBarItem, ToolBarText, ToolBarSeparator, ToolBarStack
from ch.aplu.jcardgame import Deck, Hand, Card, CardGame, RowLayout
from ch.aplu.jcardgame.Hand import SortType
from enum import *

# ------------------ class MyToolBarListener -------------------------------
class MyToolBarListener(ToolBarListener):
   def leftPressed(self, item):
      if item == okBtn:
         okBtn.show(1)
         
         card = getSelectedCard(deck)
         if card != None:
            if hand.insert(card, True):
               cg.setStatusText("Card " + str(card.toString()) + " successfully inserted.");
               if hand.getNumberOfCards() == 2:
                  initSortBtn()
            else:
              cg.setStatusText("Failed to insert card " + str(card.toString()) + " (no duplication allowed)");
      else:
         if item == spades:
            self.selected(spades)
         if item == hearts:
            self.selected(hearts)
         if item == diamonds:
            self.selected(diamonds)
         if item == clubs:
            self.selected(clubs)
            
   def leftReleased(self, item):
      if item == okBtn:
         item.show(0)

   def selected(self, toolBarStack):
      if toolBarStack.isSelected():
         toolBarStack.showNext()
      else:
         deselectAll()
         toolBarStack.setSelected(True)

# ------------------ class MySortButtonListener ----------------------------
class MySortBtnListener(ToolBarListener):
   def leftPressed(self, item):
      sortBtn.show(1) 
      hand.sort(SortType.RANKPRIORITY, True)
   def leftReleased(self, item):
      sortBtn.show(0)

# ------------------ global functions --------------------------------------
def initToolBar():
   toolBar = ToolBar(cg)
   toolBar.addItem(textItem, separator0, spades, hearts, diamonds, clubs, separator1, okBtn)
   toolBar.show(Location(10, 10))
   toolBar.addToolBarListener(MyToolBarListener())

def initSortBtn():
   global sortBtn
   toolBar = ToolBar(cg)
   sortBtn = ToolBarItem("sprites/sortBtn.gif", 2)
   toolBar.addItem(sortBtn)
   toolBar.show(Location(233, 212))
   toolBar.addToolBarListener(MySortBtnListener())

def deselectAll():
   spades.setSelected(False)
   hearts.setSelected(False)
   diamonds.setSelected(False)
   clubs.setSelected(False)
 
def getSelectedCard(deck):
# Assumes that only one card stack is selected
# Returns None, if no card is selected
   card = None
   ids = ToolBarStack.getSelectedItemIds()
   for i in range(len(ids)):
      id = ids[i]
      if (id != -1):  # Selected item found
         deck.getSuit(i)
         deck.getRank(id)
         card = Card(deck, deck.getSuit(i), deck.getRank(id))
         break
   return card
 

# ------------------------ main --------------------------------------------
Suit = enum("SPADES", "HEARTS", "DIAMONDS", "CLUBS")
Rank = enum("ACE", "KING", "QUEEN", "JACK", "TEN", "NINE", "EIGHT", "SEVEN", "SIX", "FIVE", "FOUR", "THREE", "TWO")

nbRanks = len(Rank.values())
deck = Deck(Suit.values(), Rank.values(), "cover")
hand = Hand(deck)
sortBtn = None

textItem = ToolBarText("Select Card:", 30)
separator0 = ToolBarSeparator(2, 30, X11Color("black"))
spades = ToolBarStack("sprites/spades.gif", nbRanks)
hearts = ToolBarStack("sprites/hearts.gif", nbRanks)
diamonds = ToolBarStack("sprites/diamonds.gif", nbRanks)
clubs = ToolBarStack("sprites/clubs.gif", nbRanks)
separator1 = ToolBarSeparator(2, 30, X11Color("black"))
okBtn = ToolBarItem("sprites/ok30.gif", 2)

cg = CardGame(300, 250, 30)
cg.setStatusText("Select a card by clicking on the card stacks and press OK.")
hand.setView(cg, RowLayout(Location(150, 125), 290))
hand.draw()
initToolBar()

