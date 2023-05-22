# Ex09.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location, GGActListener
from ch.aplu.jgamegrid import GGMouse, GGMouseListener, GGSound, GGTileCollisionListener
from java.awt import Point
from java.lang import String
from ch.aplu.util import X11Color

# --------------------- class Typesetter ------------------
class Typesetter():
   a = String(
    "xxxx" +
    "x  x" +
    "xxxx" +
    "x  x" +
    "x  x")
   d = String(
    "xxx " +
    "x  x" +
    "x  x" +
    "x  x" +
    "xxx ");
   e = String(
    "xxxx" +
    "x   " +
    "xxx " +
    "x   " +
    "xxxx");
   g = String(
    "xxxx" +
    "x   " +
    "x xx" +
    "x  x" +
    "xxxx");
   i = String(
    "x" +
    "x" +
    "x" +
    "x" +
    "x");
   j = String(
    "xxx" +
    "  x" +
    "  x" +
    "  x" +
    "xxx");
   m = String(
    "xxxxx" +
    "x x x" +
    "x x x" +
    "x x x" +
    "x x x");
   r = String(
    "xxxx" +
    "x  x" +
    "xxxx" +
    "x x " +
    "x xx");

# --------------------- class CapturedActor ------------------
class CapturedActor(Actor):
   def __init__(self, isRotatable, imagePath, nbSprites):
      Actor.__init__(self, isRotatable, imagePath, nbSprites)
   
   def move(self, dist):
      pt = gg.toPoint(self.getNextMoveLocation())
      dir = self.getDirection()
      if pt.x < 0 or pt.x > gg.getPgWidth():
         self.setDirection(180 - dir)
         self.setHorzMirror(not self.isHorzMirror())
      if pt.y < 0 or pt.y > gg.getPgHeight():
         self.setDirection(360 - dir)
      Actor.move(self, dist)

# --------------------- class Ball ---------------------------
class Ball(CapturedActor):
   def __init__(self):
      CapturedActor.__init__(self, False, "sprites/ball.gif", 2)

   def act(self):
      self.move(10)

# --------------------- class MyTileCollisionActor -----------
class MyTileCollisionListener(GGTileCollisionListener):
   def collide(self, actor, location):
      d = 10
      ballCenter = gg.toPoint(actor.getLocation())
      brickCenter = gg.toPoint(location)
      if ballCenter.y < brickCenter.y - d or ballCenter.y > brickCenter.y + d:
         actor.setDirection(360 - actor.getDirection())
      return 5

# --------------------- class MyActListener -----------
class MyActListener(GGActListener):
   def act(self):
      global xMap
      global dx
      xMap += dx;
      if xMap == xMapStart or xMap == xMapEnd:
         dx = -dx
      tm.setPosition(Point(xMap, yMapStart))
 
# --------------------- main ---------------------------------
nbHorzTiles = 49
nbVertTiles = 5
tileSize = 20
xMapStart = 20
yMapStart = 40
xMapEnd = -380
xMap = xMapStart
dx = -1
balls = []

def createText():
   ts = Typesetter()
   letter(0, ts.j)
   letter(5, ts.g)
   letter(11, ts.a)
   letter(17, ts.m)
   letter(24, ts.e)
   letter(30, ts.g)
   letter(36, ts.r)
   letter(42, ts.i)
   letter(45, ts.d)

def letter(x, s):
   w = s.length() // 5
   for k in range(5):
       for i in range(w):
          if s.charAt(k * w + i) == 'x':
             showBrick(x + i, k)

def showBrick(i, k):
   tm.setImage("sprites/brick.gif", i, k)
   for n in range(4):
      balls[n].addCollisionTile(Location(i, k))
   

gg = GameGrid(620, 180, 1, False)
gg.setBgColor(X11Color("darkblue"))
gg.setSimulationPeriod(50)
gg.addActListener(MyActListener())
tm = gg.createTileMap(nbHorzTiles, nbVertTiles, tileSize, tileSize)
tm.setPosition(Point(xMapStart, yMapStart))

for n in range(4):
   ball = Ball()
   gg.addActor(ball, gg.getRandomLocation(), gg.getRandomDirection())
   ball.setCollisionCircle(Point(0, 0), 8)
   ball.addTileCollisionListener(MyTileCollisionListener())
   if n == 2 or n == 3:
      ball.show(1)
   balls.append(ball)

createText()
gg.show()
gg.doRun()


