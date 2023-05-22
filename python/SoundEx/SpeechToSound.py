from soundsystem import *

initTTS()
#selectVoice("german-man")
selectVoice("german-woman")
#selectVoice("english-man")

text = "Danke dass du mir eine Sprache gibst. Viel Spass beim Programmieren" 
#text = "Thank you to give me a voice. Enjoy programming" 
voice = generateVoice(text)
openSoundPlayer(voice)
play()  

