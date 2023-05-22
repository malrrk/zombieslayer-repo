# MusicPlayer.py

from ch.aplu.util import SoundPlayer
from javax.swing import JFrame, JButton, JTextField, JFileChooser
from java.awt import FlowLayout

def getClipTime():
   return "at %4.1f s" %(player.getCurrentTime() / 1000)

def doOpen(event):
   global player
   global volume

   if not player == None:
      player.stop()

   chooser = JFileChooser()
   rc = chooser.showOpenDialog(None)
   if rc == JFileChooser.APPROVE_OPTION:
      soundFile = chooser.getSelectedFile()
      player = SoundPlayer(soundFile)
      volume = player.getVolume();
   
def doRewind(event):
   if player == None:
      return
   player.rewindTime(10000)

def doStop(event):
   if player == None:
      return
   player.stop()

def doPlay(event):
   if player == None:
      return
   player.play()

def doPause(event):
   if player == None:
      return
   player.pause()

def doAdvance(event):
   if player == None:
      return
   player.advanceTime(10000)

def doVolumeIncrease(event):
   if player == None:
      return
   global volume
   global doDisplayTime
   volume += 50;
   if volume > 1000:
      volume = 1000
   player.setVolume(volume)
   doDisplayTime = False

def doVolumeDecrease(event):
   if player == None:
      return
   global volume
   global doDisplayTime
   volume -= 50;
   if volume < 0:
      volume = 0
   player.setVolume(volume)
   doDisplayTime = False
   
player = None

openButton = JButton("Open", actionPerformed = doOpen)
rewindButton = JButton("Rewind", actionPerformed = doRewind)
stopButton = JButton("Stop", actionPerformed = doStop)
playButton = JButton("Play", actionPerformed = doPlay)
pauseButton = JButton("Pause", actionPerformed = doPause)
advanceButton = JButton("Advance", actionPerformed = doAdvance)
volumeIncreaseButton = JButton("Volume+", actionPerformed = doVolumeIncrease)
volumeDecreaseButton = JButton("Volume-", actionPerformed = doVolumeDecrease)
status = JTextField(preferredSize = (400, 20))

buttons = [openButton, rewindButton, stopButton, playButton, pauseButton, 
advanceButton, volumeIncreaseButton, volumeDecreaseButton]
nbButtons = len(buttons)

win = JFrame("Music Player")
win.getContentPane().setLayout(FlowLayout())
for i in range(nbButtons):
   win.getContentPane().add(buttons[i])
win.getContentPane().add(status)
win.setTitle("Music Player")
win.setSize(700, 100)
win.setLocation(200, 100)
win.setVisible(True)
status.setText("Please open a WAV music file")
doDisplayTime = True

# Display loop
while (win.isVisible()):
  if player != None:
     if doDisplayTime:
        status.setText("Current time: " + getClipTime())
     else:   
        status.setText("Current volume: " + str(volume))
        SoundPlayer.delay(300)
        doDisplayTime = True
  SoundPlayer.delay(100)

# Cleanup
if player != None:
   player.stop()  
win.dispose()  # free resources
