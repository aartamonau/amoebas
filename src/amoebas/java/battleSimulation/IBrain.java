package amoebas.java.battleSimulation;


import java.awt.geom.Point2D;



public interface IBrain {

    public void feedSenses(Point2D.Double enemyVector,
            Point2D.Double thornVector);


    public Point2D.Double getAimVector();


    public Point2D.Double getMovementVector();


    public boolean shallWeShoot();
}
