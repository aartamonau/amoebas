package amoebas.java.battleSimulation;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Amoeba extends MovableObject {

    protected IBrain brain;
    private String name;
    private Random rand = new Random();
    private BattleArea battleArea;
    private boolean isReadyForShooting;

    private Point nearestEnemyVector;
    private Point nearestThornVector;

    private Thorn thorn;


    public static final int AMOEBA_MAX_SPEED = 3;
    public static final int AMOEBA_WIDTH = 100;
    public static final int AMOEBA_HEIGHT = 100;
    public static final int MAX_HP = 200;
    public static final int WEIGHT = 50;

    // For debugging only
    public Point lastAimVector = new Point();


    public Amoeba(IBrain brain, BattleArea battleArea, Point location) {

        super(battleArea, location, new Dimension(AMOEBA_WIDTH, AMOEBA_HEIGHT));

        this.brain = brain;
        this.battleArea = battleArea;
        this.hitPoints = MAX_HP;
        this.weight = WEIGHT;
        this.name = Integer.toString(new Random().nextInt());
        this.isReadyForShooting = true;
        this.nearestEnemyVector = new Point();
        this.thorn = null;
    }

    @Override
    public void update() {

        updateNearestEnemyAndThornVectors();

        Point location = this.getLocation();

        Point2D.Double enemyVector =
          new Point2D.Double(this.nearestEnemyVector.x - location.x,
                             this.nearestEnemyVector.y - location.y);
        Point2D.Double thornVector = null;
        if (this.nearestThornVector != null) {
          thornVector =
            new Point2D.Double(this.nearestThornVector.x - location.x,
                               this.nearestThornVector.y - location.y);
        }


        this.brain.feedSenses(enemyVector, thornVector);

        Point2D.Double velocityVectorNorm = this.brain.getMovementVector();

        this.velocityVector = new Point((int) Math.ceil(velocityVectorNorm.x
                * AMOEBA_MAX_SPEED), (int) Math.ceil(velocityVectorNorm.y
                * AMOEBA_MAX_SPEED));

        this.move(this.velocityVector);

        if (brain.shallWeShoot()) {

            if (thorn == null) {

                Point2D.Double aimVector = brain.getAimVector();

                lastAimVector = new Point((int) (aimVector.x),
                        (int) (aimVector.y));

                thorn = new Thorn(this, new Point((int) Math
                        .ceil(aimVector.x), (int) Math.ceil(aimVector.y)),
                        getThornInitialLocation(aimVector));

                battleArea.thornShot(thorn);

                thorn.update();
            }

        }

    }


    private void updateNearestEnemyAndThornVectors() {

        Point currAmoebaLocation = this.getLocation();

        double nearestEnemyVectorLength = 0;
        double nearestThornVectorLength = 0;


        List<Amoeba> amoebas = battleArea.getAmoebas();
        Iterator<Amoeba> amoebasIter = amoebas.iterator();

        while(amoebasIter.hasNext()) {

            Amoeba amoeba = amoebasIter.next();

            if ( !amoeba.equals(this) ) {

                Point amoebaLocation = amoeba.getLocation();
                amoebaLocation.x -= currAmoebaLocation.x;
                amoebaLocation.y -= currAmoebaLocation.y;

                double length = Utils.getRadiusVectorLength(amoebaLocation);


                if ( length > nearestEnemyVectorLength ) {
                    nearestEnemyVectorLength = length;
                    nearestEnemyVector = amoebaLocation;
                }


                Thorn enemyThorn = amoeba.getActiveThorn();

                if (enemyThorn != null ) {

                    Rectangle2D boundaryRect = enemyThorn.getBoundaryRect();

                    Point thornLocation = enemyThorn.getLocation();

                    thornLocation.x -= currAmoebaLocation.x;
                    thornLocation.x += boundaryRect.getWidth()/2;

                    thornLocation.y -= currAmoebaLocation.y;
                    thornLocation.y += boundaryRect.getHeight()/2;

                    length = Utils.getRadiusVectorLength(thornLocation);

                    if ( length > nearestThornVectorLength ) {

                        nearestThornVectorLength = length;
                        nearestThornVector = thornLocation;
                    }
                } else {
                    nearestThornVector = null;
                }

            }
        }


    }






    private Point getThornInitialLocation(Point2D.Double aimVector) {

        double rectCenterX = boundaryRect.getCenterX();
        double rectCenterY = boundaryRect.getCenterY();

        double intersectionX;
        double intersectionY;

        assert aimVector.y != 0 || aimVector.x != 0 : "aimVector.y != 0 || aimVector.x != 0";

        double aimDirectionLineTangent;

        if (aimVector.x != 0 && aimVector.y != 0) {

            aimDirectionLineTangent = 1.0 * Math.abs(aimVector.y / aimVector.x);

            // Checking intersection with rect's top line

            intersectionY = boundaryRect.getHeight() / 2;
            intersectionX = intersectionY / aimDirectionLineTangent;

            if (!boundaryRect.contains(intersectionX + rectCenterX - 1,
                    intersectionY + rectCenterY - 1)) {

                // Checking intersection with rect's right line
                intersectionX = boundaryRect.getWidth() / 2;
                intersectionY = intersectionX * aimDirectionLineTangent;
            }

        } else if (aimVector.y == 0) {

            intersectionY = 0;
            intersectionX = boundaryRect.getWidth() / 2;

        } else {

            intersectionX = 0;
            intersectionY = boundaryRect.getHeight() / 2;
        }

        assert boundaryRect.contains(intersectionX + rectCenterX - 1,
                intersectionY + rectCenterY - 1) : "omfg ->" + aimVector
                + "\nx = " + (intersectionX + rectCenterX) + "\ny = "
                + (intersectionY + rectCenterY) + "\nrect : " + boundaryRect;

        Point result = new Point();

        result.x = (int) Math.ceil(intersectionX);
        result.y = (int) Math.ceil(intersectionY);

        if (aimVector.x < 0) {
            result.x *= -1;
        }

        if (aimVector.y < 0) {
            result.y *= -1;
        }

        result.x += rectCenterX;
        result.y += rectCenterY;

        return result;
    }

    @Override
    public void processCollision(MapObject other) {

        int x = (int) this.boundaryRect.getX();
        int y = (int) this.boundaryRect.getY();

        this.boundaryRect.setRect(x - this.velocityVector.x * 2, y
                - this.velocityVector.y * 2, this.boundaryRect.getWidth(),
                this.boundaryRect.getHeight());

        computeCollisionDamage(other);
    }

    @Override
    public void computeCollisionDamage(MapObject other) {
        int dmg = (int) ((int) other.weight * rand.nextDouble() * 0.1);
        hitPoints -= dmg;
    }



    public Thorn getActiveThorn() {
        return thorn;
    }



    public Point getNearestThornVector() {
        return nearestThornVector;
    }

    public Point getNearestEnemyVector() {
        return nearestEnemyVector;
    }

    public boolean isReadyForShooting() {
        return isReadyForShooting;
    }

    public void setReadyForShooting(boolean isReadyForShooting) {
        this.isReadyForShooting = isReadyForShooting;
        this.thorn = null;
    }

    public String getName() {
        return name;
    }

}
