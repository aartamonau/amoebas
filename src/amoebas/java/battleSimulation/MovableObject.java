package amoebas.java.battleSimulation;


import java.awt.Dimension;
import java.awt.Point;


public abstract class MovableObject extends MapObject {

  public MovableObject(BattleArea battleArea, Point location, Dimension size) {
    super(battleArea, location, size);
    velocityVector = new Point();
  }


  public Point getVelocityVector() {
    return velocityVector;
  }


  public void move(int dx, int dy) {

    boundaryRect.setRect(boundaryRect.getX() + dx, boundaryRect.getY() + dy,
        boundaryRect.getWidth(), boundaryRect.getHeight());

    velocityVector.x = dx;
    velocityVector.y = dy;
  }

  public void move(Point vector) {
    this.move(vector.x, vector.y);
  }

  protected Point velocityVector;
}
