# ClipTracer.py

from soundsystem import *
from gpanel import *

samples = getWavMono("wav/bird.wav")

makeGPanel(0, len(samples), -33000, 33000)
for i in range(len(samples)):
   draw(i, samples[i])

openMonoPlayer(samples, 22050)
play()
