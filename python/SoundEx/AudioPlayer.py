# AudioPlayer.py

from gpanel import *
from soundsystem import *

def getPlayTime():
  return "{:4.1f}".format(getCurrentTime() /  1000) + " s"

makeGPanel()
addStatusBar(30)
setStatusText("Status: Use keyboard for commands")

openSoundPlayer("c:/scratch/mysong.wav")
volume = getVolume()
text(0.4, 0.9, "Command List:")
text(0.1, 0.8, "Cursor up: play")
text(0.1, 0.7, "Cursor down: pause")
text(0.1, 0.6, "Cursor left: rewind")
text(0.1, 0.5, "Cursor right: advance")
text(0.1, 0.4, "Key v: Volume increase")
text(0.1, 0.3, "Key c: Volume decrease")
text(0.1, 0.2, "Key s: Stop")

while True:
   code  = getKeyCode()
   if code == 65535: # no key
      delay(100)
      setStatusText("Status: Current play time " + getPlayTime())
      continue
   if code == 38:  # cursor up
      play()
      setStatusText("Status: Play")
   if code == 40:  # cursor down
      pause()
      setStatusText("Status: Pause")
   if code == 37:  # cursor left
      rewindTime(10000)
      setStatusText("Status: Rewind")
   if code == 39:  # cursor right
      advanceTime(10000)
      setStatusText("Status: Advance")
   if code == 83:  # s key
      stop()
      setStatusText("Status: Stop")
   if code == 86:  # v key
      volume += 50;
      if volume > 1000:
         volume = 1000
      setVolume(volume)
      setStatusText("Status: Volume increase to " + str(volume))
   if code == 67:  # c key
      volume -= 50;
      if volume < 0:
         volume = 0
      setVolume(volume)
      setStatusText("Status: Volume decrease to " + str(volume))
   delay(1000)
