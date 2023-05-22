# GPanelEx5.py

from gpanel import *

def onMousePressed(x, y):
   global x0, y0
   x0 = x
   y0 = y

def onMouseDragged(x, y):
   global x0, y0
   x1 = x
   y1 = y
   line(x0, y0, x1, y1)
   x0 = x1
   y0 = y1

makeGPanel(mousePressed = onMousePressed, 
           mouseDragged = onMouseDragged)
