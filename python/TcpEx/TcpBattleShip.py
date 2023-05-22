# TcpBattleShip.py

import sys
sys.path.append("./Lib/TcpJLib.jar")

from ch.aplu.jgamegrid import GameGrid, X11Color, Actor, GGMouse, GGMouseListener, Location
from ch.aplu.tcp import TcpNode, TcpNodeState, TcpTools

# ---------- class Ship --------------------
class Ship(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/boat.gif")

# ---------- class MyMouseListener --------
class MyMouseListener(GGMouseListener):
   def mouseEvent(self, mouse):
      global isMyMove, loc
      if not isMyMove:
         return True
      loc = gg.toLocationInGrid(mouse.getX(), mouse.getY())
      tcpNode.sendMessage(str(loc.x) + str(loc.y))  # send string
      gg.setTitle("Wait enemy bomb!")
      isMyMove = False
      return True
 
# ---------- connect() --------------------
def connect():
   id = requestEntry("Enter unique server ID (more than 3 characters)");
   if id == None:
      gg.dispose()
      return
   sessionID = sessionPrefix + id
   gg.setTitle("Trying to connect to host")
   tcpNode.connect(sessionID, nickname)
 
# ---------- messageReceived() ------------
def messageReceived(sender, message):
   print "Got message: " + message
   global isMyMove, enemyScore, myScore, loc
   
   x = ord(message[0]) - 48 # We get ASCII code of number
   y = ord(message[1]) - 48
    
   if x == 9:  # Got command
      if y == GAME_START:
          isMyMove = False
          gg.setTitle("Wait! Enemy shoots first.")
      if y == SHIP_HIT:
          gg.addActor(Actor("sprites/checkgreen.gif"), loc)
          myScore += 1
          if myScore == nbShips:
            gameOver(True)
      if y == SHIP_MISSED:
          gg.addActor(Actor("sprites/checkred.gif"), loc)
   else: # Got coordinates
      loc = Location(x, y)
      actor = gg.getOneActorAt(loc, Ship)
      if actor != None:
         actor.removeSelf()
         gg.addActor(Actor("sprites/explosion.gif"), loc)
         tcpNode.sendMessage("9" + str(SHIP_HIT))
         enemyScore += 1
         if enemyScore == nbShips:
            gameOver(False)
            return
      else:
         tcpNode.sendMessage("9" + str(SHIP_MISSED))

      gg.setTitle("Shoot now! Score: " + str(myScore) + " (" + str(enemyScore) + ")");
      isMyMove = True
 
# ---------- statusReceived() -------------
def statusReceived(status):
   print "Got status: " + status
   global isMyMove, myScore, enemyScore
   if "In session:--- (0)" in status:  # we are first player
      gg.setTitle("Connected. Wait enemy..")
   if "In session:--- (1)" in status:  # we are second player
      tcpNode.sendMessage("9" + str(GAME_START))
      gg.setTitle("It is you to play")
      isMyMove = True  # Second player starts
   if "Disconnected:--- " in status:  # the partner is disconnected
      gg.setTitle("Enemy disconnected. Wait next..")
      gg.removeAllActors()
      isMyMove = False
      myScore = 0
      enemyScore = 0
      deployShips()
  
# ---------- requestEntry() ---------------
def requestEntry(prompt):
   entry = ""
   while len(entry) < 3:
      entry = inputString(prompt, False)
      if entry == None:
         return None
   return entry.strip()
    
# ---------- gameOver() -------------------
def gameOver(isWinner):
   global isMyMove
   isMyMove = False
   gg.removeAllActors()
   gg.addActor(Actor("sprites/gameover.gif"), Location(3, 3))
   if isWinner:
      gg.setTitle("You win!")
   else:
      gg.setTitle("You lost!")

# ---------- notifyExit() ----------------
def notifyExit():
   tcpNode.disconnect()
   gg.dispose()

def deployShips():
   for i in range(nbShips):
      gg.addActor(Ship(), gg.getRandomEmptyLocation())
   
# ---------- main() ----------------------
sessionPrefix = "ama&19td"
nickname = "captain"
tcpNode = TcpNode(messageReceived = messageReceived, statusReceived = statusReceived)
isMyMove = False
nbShips = 7
loc = None
myScore = 0
enemyScore = 0

GAME_START = 0
SHIP_MISSED = 1
SHIP_HIT = 2

gg = GameGrid(6, 6, 50, X11Color("red"), False, notifyExit = notifyExit)
gg.setTitle("TcpBattleShip")
deployShips()
gg.addMouseListener(MyMouseListener(), GGMouse.lClick);
gg.show()
connect()
