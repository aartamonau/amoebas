package amoebas.java.battleSimulation;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;


public class Thorn extends MovableObject {

  public static final int SPEED = 8;
  public static final int SIZE = 5;
  public static final int WEIGHT = 500;


  public Thorn(Point location, Dimension size) {
    super(null, location, size);
    this.hitPoints = 1;
    this.weight = WEIGHT;
  }


  public Thorn(Point amoebaVelocityVector, Point location) {
    this(location, new Dimension(SIZE, SIZE));
    this.velocityVector = computeVelocityVector(amoebaVelocityVector);
  }


  @Override
  public void update() {
    move(velocityVector.x, velocityVector.y);
  }


  @Override
  public void processCollision(MapObject other) {
    computeCollisionDamage(other);
  }


  @Override
  public void computeCollisionDamage(MapObject other) {
    hitPoints = 0;    
  }


  private Point computeVelocityVector(Point thornDirection) {
        
	int x = Math.abs(thornDirection.x);
    int y = Math.abs(thornDirection.y);

    double angleCtg = 1.0 * x / y;

    int newY = (int)(1.0 * SPEED / Math.sqrt(angleCtg * angleCtg + 1 ));      
    int newX = ( newY != 0 ) ? (int)(newY * angleCtg) : SPEED;
    
    Point result = new Point(newX, newY);

    // Putting the vector into the same quarter
    // as thorn's direction

    if ( thornDirection.x < 0 ) {
      result.x *= -1;
    }

    if ( thornDirection.y < 0 ) {
      result.y *= -1;
    }


    return result;
  }
}
