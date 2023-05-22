# GPanelEx2.py

from gpanel import *
from ch.aplu.util import X11Color
import random

NB_POLYGONS = 20
makeGPanel()
for i in range(NB_POLYGONS):
   color = getRandomX11Color()
   setColor(color)
   m = random.randrange(5, 10)
   x = [0.0] * m
   y = [0.0] * m
   for k in range(m):
      x[k] = random.random()
      y[k] = random.random()
   fillPolygon(x, y)
   delay(100)
