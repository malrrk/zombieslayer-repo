# GameGridEx4.py
from gamegrid import *                         

class MyMouseListener(GGMouseListener):
    def mouseEvent(self, mouse):
        global xStart, xEnd, yStart, yEnd
        location = gg.toLocationInGrid(mouse.getX(), mouse.getY())
        event = mouse.getEvent()
        if event == GGMouse.lPress:
            xStart = xEnd = p.toUserX(mouse.getX())
            yStart = yEnd = p.toUserY(mouse.getY())
            p.setXORMode(Color.white)

        elif event == GGMouse.lDrag:
            p.color(Color.blue)
            # Erase old
            p.rectangle(xStart, yStart, xEnd, yEnd, False)
            xEnd = p.toUserX(mouse.getX())
            yEnd = p.toUserY(mouse.getY())
            # Draw new
            p.rectangle(xStart, yStart, xEnd, yEnd, False)

        elif event == GGMouse.lRelease:
            p.setPaintMode()
            p.color(Color.white)
            p.rectangle(xStart, yStart, xEnd, yEnd, False)
        return False # Don't consume the event

gg = makeGameGrid(500, 500)
p = gg.getPanel()
gg.addMouseListener(MyMouseListener(), GGMouse.lPress | GGMouse.lDrag | GGMouse.lRelease)
image = GGBitmap.getImage("sprites/reef.gif")
p.drawImage(image)
show()


