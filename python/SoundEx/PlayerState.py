# PlayerState.py

from soundsystem import *

def notify(reason, mixerIndex):
   print "Notification from sound device %d" %(mixerIndex)
   if reason == 0:
      print "Start playing at " + str(getCurrentPos())
   elif reason == 1:
      print "Resume playing at " + str(getCurrentPos())
   elif reason == 2:
      print "Pause playing at " + str(getCurrentPos())
   elif reason == 3:
      print "Stop playing at " + str(getCurrentPos())
   elif reason == 4:
      print "End of resource at " + str(getCurrentPos())
   else:
     pass

audioFile = "c:/scratch/mysong.wav"

openSoundPlayer(audioFile, notify)
play()
delay(5000)
pause()
delay(3000)
play()
delay(5000)
stop()
