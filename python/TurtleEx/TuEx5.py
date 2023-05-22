# TuEx5.py

from gturtle import *

def onMouseHit(x, y):   
   t = Turtle(frame)
   t.setPos(x, y)
   for i in range(9):
      t.forward(100)
      t.right(160)

frame = TurtleFrame(mouseHit = onMouseHit)
