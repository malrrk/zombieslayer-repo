# SineGenerator.py

from soundsystem import *
import math

sampleRate = 22050 # 8000,11025,16000,22050,44100
duration = 3  # in s
amplitude = 30000
frequency = 1000 # in Hz
nbFrames = int(duration * sampleRate)

t = 0
dt = 1.0 / sampleRate
data = []
for i in range(nbFrames):
   soundData = int(amplitude * math.sin(2 * math.pi * frequency * t))
   data.append(soundData)
   t += dt;

openMonoPlayer(data, sampleRate)
play()

openMonoRecorder(sampleRate)
rc = writeWavFile(data, "d:/scratch/sine.wav")
if rc:
   print "Sound file d:/scratch/sine.wav successfully created"

