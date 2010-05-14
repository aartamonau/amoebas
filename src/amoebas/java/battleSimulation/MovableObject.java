package amoebas.java.battleSimulation;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;


public abstract class MovableObject extends MapObject {

  public MovableObject(BattleArea battleArea, Point location, Dimension size) {
    super(battleArea, location, size);
    velocityVector = new Point2D.Double();
  }


  public Point2D.Double getVelocityVector() {
    return velocityVector;
  }


  public void move(double dx, double dy) {

    boundaryRect.setRect(boundaryRect.getX() + dx, boundaryRect.getY() + dy,
        boundaryRect.getWidth(), boundaryRect.getHeight());

    velocityVector.x = dx;
    velocityVector.y = dy;
  }

  protected Point2D.Double velocityVector;


}
