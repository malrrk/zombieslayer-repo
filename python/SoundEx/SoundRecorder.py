# SoundRecorder.py

from soundsystem import *
from ch.aplu.util import MessagePane

sampleRate = 44100  # 8000,11025,16000,22050,44100

# ---------- Capture sound ---------------
openStereoRecorder(sampleRate)
mp = MessagePane(50)
mp.setText("Recording...")
capture()
delay(5000)
stopCapture()
mp.setText("Stopped")

# ---------- Play captured sound ---------
sound = getCapturedSound()
openStereoPlayer(sound, sampleRate)
play()

# ---------- Save captured sound ---------
filename = "d:/scratch/mysound.wav"
if writeWavFile(sound, filename):
   mp.setText(filename + " successfully saved")
else:
   mp.setText("Can't save " + filename)