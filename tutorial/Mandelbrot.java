// Mandelbrot.java

import ch.aplu.jgamegrid.*;
import ch.aplu.util.*;
import java.awt.Color;
import java.awt.event.*;

public class Mandelbrot extends GameGrid
  implements MouseListener, MouseMotionListener
{
  private final int maxIterations = 50;
  private final double res = 800;
  private GGPanel p;
  private double xStart;
  private double yStart;
  private double xEnd;
  private double yEnd;
  private boolean isDrawing = false;

  public Mandelbrot()
  {
    super(500, 500);
    show();
    p = getPanel();
    p.setRefreshEnabled(false);
    addMouseListener(this);
    addMouseMotionListener(this);

    draw(-2, 2, -2, 2);
    while (true)
    {
      Monitor.putSleep();
      p.clear();
      refresh();
      double xmin = Math.min(xStart, xEnd);
      double xmax = Math.max(xStart, xEnd);
      double ymin = Math.min(yStart, yEnd);
      double ymax = Math.max(yStart, yEnd);
      draw(xmin, xmax, ymin, ymax);
    }
  }

  private void draw(double xmin, double xmax, double ymin, double ymax)
  {
    isDrawing = true;
    setTitle("Working. Please wait...");
    p.window(xmin, xmax, ymin, ymax);
    double span = xmax - xmin;
    Complex vector = new Complex(xmin, ymin);
    for (double i = 0; i < res; i++)
    {
      for (double j = 0; j < res; j++)
      {
        Complex c = new Complex(span / res * i, span / res * j);
        c.add(vector);
        Complex z = new Complex(0, 0);
        int it = 0;
        while (z.modulus() < 2 && it < maxIterations)
        {
          // z = z * z + c
          z.multiply(z);
          z.add(c);
          it++;
        }
        putPixel(c, new Color((30 * it) % 256, (4 * it) % 256,
          255 - (30 * it) % 256));
      }
      refresh();
    }
    isDrawing = false;
    setTitle("Done. Select zoom area!");
  }

  public void mousePressed(MouseEvent evt)
  {
    if (isDrawing)
      return;
    xStart = xEnd = p.toUserX(evt.getX());
    yStart = yEnd = p.toUserY(evt.getY());
    p.setXORMode(Color.white);
  }

  public void mouseDragged(MouseEvent evt)
  {
    if (isDrawing)
      return;
    p.color(Color.red);
    drawRectangle(true);  // Erase old rectangle
    xEnd = p.toUserX(evt.getX());
    yEnd = p.toUserY(evt.getY());
    drawRectangle(false);  // Draw new rectangle
    refresh();
  }

  public void mouseReleased(MouseEvent evt)
  {
    if (isDrawing)
      return;
    drawRectangle(true);
    p.setPaintMode();
    refresh();
    if (!(xStart == xEnd && yStart == yEnd))
      Monitor.wakeUp();
  }

  private void drawRectangle(boolean erase)
  {
    if (!erase)
    {
      // Restrict to square
      double dx = xEnd - xStart;
      double dy = yEnd - yStart;
      double s = Math.min(Math.abs(dx), Math.abs(dy)); // Side of square
      double dxx = Math.signum(dx) * s;
      double dyy = Math.signum(dy) * s;
      xEnd = xStart + dxx;
      yEnd = yStart + dyy;
    }
    if (!(xStart == xEnd && yStart == yEnd))
      p.rectangle(xStart, yStart, xEnd, yEnd, false);
  }

  private void putPixel(Complex z, Color c)
  {
    p.color(c);
    p.point(z.getReal(), z.getImg());
  }

  public void mouseEntered(MouseEvent e)
  {
  }

  public void mouseExited(MouseEvent e)
  {
  }

  public void mouseMoved(MouseEvent e)
  {
  }

  public void mouseClicked(MouseEvent e)
  {
  }

  public static void main(String[] args)
  {
    new Mandelbrot();
  }
}
