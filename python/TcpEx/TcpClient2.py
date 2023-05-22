# telnet program example
import socket, select, string, sys
 
def prompt() :
    sys.stdout.write('<You> ')
    sys.stdout.flush()
 


host = "10.0.1.1"
port = 1299
 
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.settimeout(2)
error = False
 
# connect to remote host
print 'Trying to connect'
try:
    s.connect((host, port))
except :
    print 'Cannot connect'
    error = True

if error:
     raise SystemExit
