package amoebas.java.battleSimulation;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;



public abstract class MapObject {

    public MapObject(BattleArea battleArea, Point location, Dimension size) {
        this.boundaryRect = new Rectangle(location, size);
        this.battleArea = battleArea;
    }
    
    
    public abstract void update();


    public void computeCollisionDamage(MapObject other) {
    }


    public Rectangle2D getBoundaryRect() {
        return this.boundaryRect;
    }


    public int getHitPoints() {
        return this.hitPoints;
    }


    public Point getLocation() {
        return new Point((int) this.boundaryRect.getX(),
                (int) this.boundaryRect.getY());
    }


    public boolean intersectsWith(MapObject other) {
        return this.boundaryRect.intersects(other.boundaryRect);
    }


    public boolean isAlive() {
        return this.hitPoints > 0;
    }


    public void processCollision(MapObject other) {
    }


    public void setHitPoints(int hp) {
        this.hitPoints = hp;
    }


    protected BattleArea battleArea;
    protected Rectangle2D boundaryRect;
    protected int hitPoints;
    protected int weight;
}
