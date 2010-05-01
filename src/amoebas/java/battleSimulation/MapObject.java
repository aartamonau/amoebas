package amoebas.java.battleSimulation;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;


public abstract class MapObject {
	
	public MapObject(Point location, Dimension size) {		
		this.boundaryRect = new Rectangle(location, size);		
	}
	
	public abstract void update(BattleArea battleArea);
	
	
	public void processCollision(MapObject other) {
	}
	
	public void computeCollisionDamage(MapObject other) {
	}
	
	
	
	public boolean intersectsWith(MapObject other) {
		return this.boundaryRect.intersects(other.boundaryRect);		
	}
	
	
	public int getHitPoints() {
		return hitPoints;
	}
	
	public boolean isAlive() {
		return hitPoints > 0;
	}

	public Point getLocation() {
		return new Point((int)boundaryRect.getX(), (int)boundaryRect.getY());
	}
	
	public Rectangle2D getBoundaryRect() {
		return boundaryRect;
	}
		
		
	protected Rectangle2D boundaryRect;
	protected int hitPoints;
	protected int weight;
}
