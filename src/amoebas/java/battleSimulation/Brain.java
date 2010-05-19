package amoebas.java.battleSimulation;


import java.awt.geom.Point2D;
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
        this.randGenerator = new Random();
        this.retreivalNum = 0;
        this.movementVector = new Point2D.Double(this.randGenerator
                .nextDouble(), this.randGenerator.nextDouble());
    }


    public void feedSenses(Point2D.Double enemyVector,
                           Point2D.Double thornVector,
                           Double intersectionDistance) {
        return;
    }


    public Point2D.Double getAimVector() {
        return new Point2D.Double(this.randGenerator.nextDouble() * 100,
                this.randGenerator.nextDouble() * 100);
    }


    public Point2D.Double getMovementVector() {

        ++this.retreivalNum;

        if (this.retreivalNum == 20) {

            this.retreivalNum = 0;

            int signX = this.randGenerator.nextBoolean() ? -1 : 1;
            int signY = this.randGenerator.nextBoolean() ? -1 : 1;

            this.movementVector = new Point2D.Double(this.randGenerator
                    .nextDouble()
                    * signX, this.randGenerator.nextDouble() * signY);
        }

        return this.movementVector;
    }


    public boolean shallWeShoot() {
        return this.randGenerator.nextDouble() < 0.02;
    }

    private Point2D.Double movementVector;
    private Random randGenerator;
    private int retreivalNum;
}
