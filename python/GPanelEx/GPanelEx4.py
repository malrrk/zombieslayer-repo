# GPanelEx4.py

from gpanel import *

makeGPanel()
addStatusBar(30)
setStatusText("Draw with cursor keys")

x = 0.5
y = 0.5
step = 0.03
move(x, y)
fillCircle(0.01)

while True:
   code  = getKeyCodeWait()
   if code == 37:  # left cursor key
      x = x - step
   if code == 38:  # up cursor key
      y = y + step
   if code == 39:  # right cursor key
      x = x + step
   if code == 40:  # down cursor key
      y = y - step
   draw(x, y)

