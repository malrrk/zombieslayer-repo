# Ex06.py

from ch.aplu.jgamegrid import GameGrid, Actor, Location, GGMouse, GGMouseListener
from ch.aplu.util import X11Color

# --------------------- class Bulb ---------------------------
class Bulb(Actor):
   def __init__(self, type):
      Actor.__init__(self, "sprites/bulb" + str(type) + ".gif", 2)

# --------------------- class Switch --------------------------
class Switch(Actor, GGMouseListener):
   def __init__(self, bulb):
      Actor.__init__(self, "sprites/switch.gif", 2)
      self.bulb = bulb
   
   def mouseEvent(self, mouse):
      location = gg.toLocationInGrid(mouse.getX(), mouse.getY())
      if location.equals(self.getLocation()):
         self.showNextSprite()
      self.bulb.show(self.getIdVisible())
      gg.refresh();
      return False
 
# --------------------- main ---------------------------------
gg = GameGrid(7, 4, 40, False)
gg.setBgColor(X11Color("lightgray"))
for i in range(3):
   bulb = Bulb(i)
   gg.addActor(bulb, Location(2*i + 1, 1))
   switch = Switch(bulb)
   gg.addActor(switch, Location(2*i + 1, 3))
   gg.addMouseListener(switch, GGMouse.lPress)
gg.show();


