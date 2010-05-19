package amoebas.java.battleSimulation;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface IBrain {
    public void feedSenses(Point2D.Double enemyVector,
                           Point2D.Double thornVector);

    public Point2D.Double getMovementVector();

    public Point2D.Double getAimVector();

    public boolean shallWeShoot();
}
