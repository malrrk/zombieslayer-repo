# EchoClient.py
# Sends message to echo server EchoServer and waits for reply

import sys
sys.path.append("./Lib/TcpJLib.jar")

from ch.aplu.tcp import TcpNode, TcpNodeState, TcpTools
from ch.aplu.util import ModelessOptionPane

def nodeStateChanged(state):
   if state == TcpNodeState.DISCONNECTED:
      print "State changed: DISCONNECTED"
   if state == TcpNodeState.CONNECTING:
       print "State changed: CONNECTING"
   if state == TcpNodeState.CONNECTED:  
      print "State changed: CONNECTED"
  
def messageReceived(sender, message):
   global replied
   print "Message received: " + message
   replied = True

def statusReceived(status):
   print "Got status: " + status

def actionPerformed(evt):
   notifyExit()
 
def notifyExit():
   global isRunning
   tcpNode.disconnect()
   mop.dispose()
   isRunning = False

sessionID = "echo&1&&asdf87u823";
nickname = "echoclient";
tcpNode = TcpNode(nodeStateChanged = nodeStateChanged, 
  messageReceived = messageReceived, statusReceived = statusReceived)
print "Trying to connect to " + tcpNode.getRelay() + \
  " using port " + str(tcpNode.getPort())
tcpNode.connect(sessionID, nickname, 2, 2, 2)
while tcpNode.getNodeState() != TcpNodeState.CONNECTED:
   TcpTools.delay(1)
print "Echo client ready"
mop = ModelessOptionPane("Client running. Shutdown?", None, "Ok", 
  actionPerformed = actionPerformed, notifyExit = notifyExit)

n = 1
isRunning = True
while isRunning:
   replied = False
   msg = "#" + str(n)
   n += 1
   tcpNode.sendMessage(msg)
   print "Message sent: " + msg
   print "Waiting for reply..."
   while not replied and isRunning:
      TcpTools.delay(1)
print "All done. Disconnecting..."
tcpNode.disconnect()  
