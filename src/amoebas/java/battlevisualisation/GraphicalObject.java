/**
 * 
 */
package amoebas.java.battlevisualisation;


import java.awt.Graphics2D;
import java.awt.Point;


/**
 * @author m
 *
 */
public abstract class GraphicalObject {
	
	public GraphicalObject(Point location) {
		this.location = location;
	}
	
	
	public abstract void  draw(Graphics2D graphicsContext, double xScale, double yScale);	
	public abstract boolean isValid();
	
	
	public int getX() {
		return location.x;
	}
	
	public void setX(int newX) {
		location.x = newX;
	}
	
	public int getY() {
		return location.y;
	}
	
	public void setY(int newY) {
		location.y = newY;
	}
	

	protected Point location;	
}