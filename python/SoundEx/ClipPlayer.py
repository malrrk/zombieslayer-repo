# ClipPlayer.py

from soundsystem import *
from ch.aplu.util import MessageDialog

openSoundPlayer("wav/bird.wav")
play()
MessageDialog("Audio Format: " + str(getAudioFormat())).show()

