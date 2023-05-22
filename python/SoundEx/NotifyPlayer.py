# NotifyPlayer.py

from soundsystem import *

openSoundPlayer(URLfromJAR("_wav/bird.wav"))
blockingPlay()
openSoundPlayer(URLfromJAR("_wav/notify.wav"))
play()


