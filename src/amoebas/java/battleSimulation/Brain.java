package amoebas.java.battleSimulation;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.Random;


/**
 *
 * A stub to simulate amoeba's brain.
 *
 * @author m
 *
 */
public class Brain implements IBrain {


  public Brain() {
    randGenerator = new Random();
    retreivalNum = 0;
    movementVector = new Point2D.Double (randGenerator.nextDouble(),
                                         randGenerator.nextDouble());
  }

  public void feedSenses(Point2D.Double selfPosition,
                         Point2D.Double enemyPosition,
                         Point2D.Double thornPosition,
                         Rectangle2D.Double[] walls) {
    return;
  }

  public Point2D.Double getMovementVector() {

    ++retreivalNum;

    if ( retreivalNum == 20 )  {

      retreivalNum = 0;

      int signX = randGenerator.nextBoolean() ? -1 : 1;
      int signY = randGenerator.nextBoolean() ? -1 : 1;

      movementVector = new Point2D.Double(
    		  randGenerator.nextInt(Amoeba.AMOEBA_MAX_SPEED) * signX * 1.0,
    		  randGenerator.nextInt(Amoeba.AMOEBA_MAX_SPEED) * signY * 1.0);
    }

    return movementVector;
  }


  public Point2D.Double getAimVector() {
    return new Point2D.Double(randGenerator.nextDouble(),
                              randGenerator.nextDouble());
  }


  public boolean shallWeShoot() {
    return randGenerator.nextDouble() < 0.02;
  }


  private Random randGenerator;
  private Point2D.Double movementVector;
  private int retreivalNum;
}
