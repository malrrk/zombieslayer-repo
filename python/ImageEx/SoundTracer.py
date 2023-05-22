# SoundRecorder.py

from soundsystem import *

sampleRate = 22050  # 8000,11025,16000,22050,44100

# ---------- Capture sound ---------------
openStereoRecorder(sampleRate)
print("Recording...");
capture()
delay(5000)
stopCapture()
print("Stopped");

# ---------- Play captured sound ---------
sound = getCapturedSound()
openStereoPlayer(sound, sampleRate)
play()

# ---------- Save captured sound ---------
filename = "d:/scratch/mysound.wav"
if writeWavFile(sound, filename):
   print filename + " successfully saved"
else:
   print "Can't save " + filename

# ---------- Show captured sound ---------
from gpanel import *
makeGPanel(0, len(sound) / 2, -33000, 33000)
for t in range(len(sound) / 2):
   draw(t, sound[2 * t])  # only left channel
