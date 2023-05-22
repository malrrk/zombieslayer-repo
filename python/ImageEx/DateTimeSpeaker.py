# DateTimeSpeaker.py

from soundsystem import *
import datetime

initTTS()
#language = "german"
language = "english"

if language == "german":
   selectVoice("german-woman")
   month = ["Januar", "Februar", "M�rz", "April", "Mai", 
      "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"]
if language == "english":
   selectVoice("english-man")
   month = ["January", "February", "March", "April", "May", 
      "June", "July", "August", "September", "October", "November", "December"]

now = datetime.datetime.now()

if language == "german":
   text = "Heute ist der " + str(now.day) + ". " + month[now.month - 1] + " " + str(now.year) + ".\n" \
      + "Die genaue Zeit ist " + str(now.hour) + " Uhr " + str(now.minute)
if language == "english":
   text = "Today we have  " + month[now.month - 1] + " "  + str(now.day) + ", "+ str(now.year) + ".\n" \
      + "The time is " + str(now.hour) + " hours " + str(now.minute) + " minutes."

print text
voice = generateVoice(text)
openSoundPlayer(voice)
play()

