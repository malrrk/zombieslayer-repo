# TcpChat.py

import sys
sys.path.append("./Lib/TcpJLib.jar")

from ch.aplu.tcp import TcpNode, TcpNodeState

def messageReceived(sender, text):
   print("Received message from " + sender + ": " + text)

def statusReceived(text):
   print("Received status: " + text)

nickname = inputString("What's your name?")    
node = TcpNode(messageReceived = messageReceived, 
               statusReceived = statusReceived)
print "Connecting as " + nickname
node.connect("$123$", nickname)

msg = ""
while msg != None:
  msg = inputString("I am " + nickname, False)
  if msg != None and msg != "":
     node.sendMessage(msg) 

node.disconnect()
print "Disconnected" 
