package amoebas.java.battleSimulation;

import java.awt.Point;
import java.awt.Rectangle;

public interface IBrain {
  public void feedSenses(Point selfPosition,
                         Point enemyPosition, Point thornPosition,
                         Rectangle[] walls);

  public Point getMovementVector();
  public Point getAimVector();
  public boolean shallWeShoot();
}
