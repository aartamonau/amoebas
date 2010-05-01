package amoebas.java.battleSimulation;

import java.awt.Dimension;
import java.awt.Point;


public abstract class MovableObject extends MapObject {
	
	public MovableObject(Point location, Dimension size) {
		super(location, size);
		velocityVector = new Point();
	}
	
	
	public Point getVelocityVector() {
		return velocityVector;
	}
	
	
	public void move(int dx, int dy) {
		
		boundaryRect.setRect(boundaryRect.getX() + dx, boundaryRect.getY() + dy,
				boundaryRect.getWidth(), boundaryRect.getHeight());
		
		velocityVector.x = dx;
		velocityVector.y = dy;
	}
	
		
	protected Point velocityVector;


}
