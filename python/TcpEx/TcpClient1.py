from ch.aplu.util import Console
import socket
import sys

c = Console.init()

# Create a TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Connect the socket to the port where the server is listening
server_address = ('10.0.1.1', 1299)
c.print("Connecting...")
sock.connect(server_address)
c.println("OK")
isConnected = True

while True:
    message = c.readLine()
    if message == "q":
        break
        
    try:
        # Send data
        c.println("Sending: " + message)
        sock.sendall(message + "\n")

        bufSize = 4096
        # Look for the response
        data = ""
        
        while not "\n" in data:
            reply = sock.recv(bufSize)
            data += reply
        c.println("Received: " + data[:-1])
       
    except:
        c.println("Connection lost. Closing socket")
        sock.close()
        isConnected = False

if isConnected:
    c.println("Closing socket requested")
    sock.close()
        