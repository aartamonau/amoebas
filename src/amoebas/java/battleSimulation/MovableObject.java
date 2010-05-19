package amoebas.java.battleSimulation;


import java.awt.Dimension;
import java.awt.Point;



public abstract class MovableObject extends MapObject {

    public MovableObject(BattleArea battleArea, Point location, Dimension size) {
        super(battleArea, location, size);
        this.velocityVector = new Point();
    }


    public Point getVelocityVector() {
        return this.velocityVector;
    }


    public void move(int dx, int dy) {

        this.boundaryRect.setRect(this.boundaryRect.getX() + dx,
                this.boundaryRect.getY() + dy, this.boundaryRect.getWidth(),
                this.boundaryRect.getHeight());

        this.velocityVector.x = dx;
        this.velocityVector.y = dy;
    }


    public void move(Point vector) {
        this.move(vector.x, vector.y);
    }

    protected Point velocityVector;
}
