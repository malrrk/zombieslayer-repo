# InputEx5.py

name = ""
while name != None:
   name = inputString("Your name?", False)
   if name != None:
      msgDlg("Your name is " + name)
msgDlg("All done")       
