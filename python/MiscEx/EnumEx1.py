# EnumEx1.py

from gpanel import *

State = enum("IDLE", "PLAYING", "PAUSED")
state = State.IDLE

makeGPanel()
enableRepaint(False)

while True:
   if kbhit():
     ch = getKey()
     if ch == "p" or ch == "P":
        if state == State.IDLE:
           state = State.PLAYING
        elif state == State.PLAYING:
           state = State.PAUSED
        elif state == State.PAUSED:
           state = State.PLAYING
       
     if ch == "s" or ch == "S":
        if state == State.IDLE:
           state = State.IDLE
        elif state == State.PLAYING:
           state = State.IDLE
        elif state == State.PAUSED:
           state = State.IDLE

   delay(1)
   clear()
   pos(0.5, 0.5)
   circle(0.1)
   text(0.47, 0.49, str(state))
   repaint()

        
 
