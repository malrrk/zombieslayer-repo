# Ex11.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location, GGActListener
from java.awt import Color
from java.awt.geom import GeneralPath, AffineTransform
import math, random

# --------------------- class Star ---------------------
class Star():
   def __init__(self, radius, color):
      self.color = color
      self.setSize(radius)

   def setSize(self, size):
      # First triangle, size is radius of circumcircle, center at (0,0)
      self.tri1 = GeneralPath()
      self.tri1.moveTo(size, 0)
      self.tri1.lineTo(int(-0.5 * size), int(0.866 * size))
      self.tri1.lineTo(int(-0.5 * size), int(-0.866 * size))
      self.tri1.closePath()

      # Second triangle like first, but rotated 180 degrees
      self.tri2 = self.tri1.clone()
      t = AffineTransform()
      t.rotate(math.pi)
      self.tri2.transform(t)

   def turn(self, angle):
      t = AffineTransform()
      t.rotate(angle)
      self.tri1.transform(t);
      self.tri2.transform(t);

   def showAt(self, mx, my):
      bg.setPaintColor(self.color)
      t = AffineTransform()
      t.translate(mx, -50 + my % 650)  # Restrict to playground
      # Cloning to avoid side effects
      gp1 = self.tri1.clone()
      gp2 = self.tri2.clone()
      gp1.transform(t)
      gp2.transform(t)
      bg.fillGeneralPath(gp1)
      bg.fillGeneralPath(gp2)

# --------------------- class Angel ---------------------
class Angel(Actor):
   def __init__(self):
      Actor.__init__(self, "sprites/angel.gif")
  
   def act(self):
      loc = self.getLocation()
      dir = self.getDirection()
      if loc.x < 50 or loc.x > 550:
         self.setDirection(180 - dir)
         if loc.x < 50:
            self.setX(55)
            self.setHorzMirror(False)
         else:
            self.setX(545)
            self.setHorzMirror(True)

      if loc.y < 50 or loc.y > 550:
         self.setDirection(360 - dir)
         if loc.y < 50:
            self.setY(55)
         else:
            self.setY(545)
      self.move()

# --------------------- class MyActListener ------------
class MyActListener(GGActListener):
   def act(self): 
      drawParadies()

# --------------------- main ---------------------
mx = [10, 40, 50, 80, 100, 130, 150, 220, 250, 300,
      320, 350, 380, 390, 410, 450, 500, 420, 550, 570]

my = [-10, 50, 250, -60, 10, 350, -60, 200, 500, 50,
      -10, 50, 250, -60, 10, 350, -60, 200, 500, 50]

rotInc = [0.01, -0.02, 0.03, -0.02, 0.05, -0.1, 0.03, -0.02, -0.2, -0.1,
          0.01, 0.02, -0.03, 0.02, -0.05, 0.1, -0.03, 0.02, 0.2, 0.1]

colors = [Color.yellow, Color.white, Color.green,  Color.magenta, Color.pink,
          Color.red, Color.orange, Color.cyan, Color.blue, Color.red,
          Color.yellow, Color.white, Color.green,  Color.magenta, Color.pink,
          Color.red, Color.orange, Color.cyan, Color.blue, Color.red]

def initStars():
   for i in range(nbStars):
      size = random.randint(10, 41)
      star = Star(size, colors[i])
      stars.append(star)

def drawParadies():
   global dy
   bg.clear(Color(175, 175, 255))
   for i in range(nbStars):
      stars[i].turn(rotInc[i])
      stars[i].showAt(mx[i], my[i] + dy)
   dy += 2

nbStars = 20;
stars = []
dy = 0
gg = GameGrid(600, 600, 1, False)
gg.setSimulationPeriod(50)
gg.addActListener(MyActListener())
bg = gg.getBg()
initStars()
gg.addActor(Angel(), Location(300, 300), -22)
gg.show()
gg.doRun()



