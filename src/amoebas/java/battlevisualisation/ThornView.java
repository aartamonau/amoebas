package amoebas.java.battlevisualisation;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import amoebas.java.battleSimulation.Thorn;



public class ThornView extends GraphicalObject {
	
	
  public ThornView(Thorn thorn) {
    super(thorn.getLocation());
    this.thorn = thorn;
    
    Point velocity = thorn.getVelocityVector();
    dbgSpeed = (int)Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y);
  }


  @Override
  public void draw(Graphics2D graphicsContext, double xScale, double yScale) {

    graphicsContext.setColor(Color.black);

    Rectangle2D rect = (Rectangle2D)thorn.getBoundaryRect().clone();

    rect.setRect(
        rect.getX() * xScale,
        rect.getY() * yScale,
        rect.getWidth() * xScale,
        rect.getHeight() * yScale);

    graphicsContext.fill(rect);

    // debug
    // Showing the direction
    Point velocityVector = thorn.getVelocityVector();

    int squareCenterX = (int)thorn.getBoundaryRect().getCenterX();
    int squareCenterY = (int)thorn.getBoundaryRect().getCenterY();

    int endX = squareCenterX + velocityVector.x * 20;
    int endY = squareCenterY + velocityVector.y * 20;

    graphicsContext.setColor(Color.RED);
    
    graphicsContext.drawLine(
        (int)(squareCenterX  * xScale),
        (int)(squareCenterY * yScale),
        (int)(endX * xScale),
        (int)(endY * yScale));
    
    // Displaying thorn's speed (velocity vector length)
    graphicsContext.drawString(Integer.toString(dbgSpeed), squareCenterX, squareCenterY);

  }


  @Override
  public boolean isValid() {
    return thorn.isAlive();
  }


  protected Thorn thorn;
  private int dbgSpeed;
}
