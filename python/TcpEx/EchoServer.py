# EchoServer.py
# Sends back the received line to EchoClient 
# Simulates a delay due to long lasting program code

import sys
sys.path.append("./Lib/TcpJLib.jar")

from ch.aplu.tcp import TcpNode, TcpNodeState, TcpTools
from javax.swing import JOptionPane

def nodeStateChanged(state):
   if state == TcpNodeState.DISCONNECTED:
      print "State changed: DISCONNECTED"
   if state == TcpNodeState.CONNECTING:
       print "State changed: CONNECTING"
   if state == TcpNodeState.CONNECTED:  
      print "State changed: CONNECTED"
      print "Listening..."
  
def messageReceived(sender, message):
   print "Message received: " + message
   print "Working now..."
   # Simulate some work
   # Set this delay time to 5000 for a 'lazy' server and
   # the corresponding delay time in EchoClient to 1000 for a 'fast' client
   TcpTools.delay(1000)
   print "Work done. Reply now..."
   tcpNode.sendMessage(message)   # Echo message
   print "Listening..."
 
def statusReceived(status):
   print "Got status: " + status
 
sessionID = "echo&1&&asdf87u823";
nickname = "echoserver";
tcpNode = TcpNode(nodeStateChanged = nodeStateChanged, 
  messageReceived = messageReceived, statusReceived = statusReceived)
print "Trying to connect to " + tcpNode.getRelay() + \
  " using port " + str(tcpNode.getPort()) + "..."
tcpNode.connect(sessionID, nickname, 2, 2, 2)

JOptionPane.showMessageDialog(None, "Echo server running. Shutdown?")
tcpNode.disconnect()
print "Disconnected"
