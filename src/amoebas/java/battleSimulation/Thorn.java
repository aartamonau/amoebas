package amoebas.java.battleSimulation;


import java.awt.Dimension;
import java.awt.Point;



public class Thorn extends MovableObject {

    public static final int SIZE = 5;
    public static final int SPEED = 8;
    public static final int WEIGHT = 500;


    public Thorn(Amoeba shooter, Point location, Dimension size) {
        super(null, location, size);
        this.hitPoints = 1;
        this.weight = WEIGHT;
        this.shooter = shooter;
        this.shooter.setReadyForShooting(false);
    }


    public Thorn(Amoeba shooter, Point amoebaVelocityVector, Point location) {
        this(shooter, location, new Dimension(SIZE, SIZE));
        this.velocityVector = computeVelocityVector(amoebaVelocityVector);
    }


    @Override
    public void computeCollisionDamage(MapObject other) {
        this.hitPoints = 0;
        this.shooter.setReadyForShooting(true);
    }


    @Override
    public void processCollision(MapObject other) {
        computeCollisionDamage(other);
    }


    @Override
    public void update() {
        move(this.velocityVector.x, this.velocityVector.y);
    }


    private Point computeVelocityVector(Point thornDirection) {

        int x = Math.abs(thornDirection.x);
        int y = Math.abs(thornDirection.y);

        double angleCtg = 1.0 * x / y;

        double newY = (1.0 * SPEED / Math.sqrt(angleCtg * angleCtg + 1));
        double newX = (newY != 0) ? (newY * angleCtg) : SPEED;

        Point result = new Point((int) Math.round(newX), (int) Math.round(newY));

        // Putting the vector into the same quarter
        // as thorn's direction

        if (thornDirection.x < 0) {
            result.x *= -1;
        }

        if (thornDirection.y < 0) {
            result.y *= -1;
        }

        return result;
    }

    private Amoeba shooter;
}
