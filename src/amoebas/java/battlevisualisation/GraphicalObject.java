/**
 * 
 */
package amoebas.java.battlevisualisation;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;


/**
 * @author m
 *
 */
public abstract class GraphicalObject {
	
	public GraphicalObject(Point location) {
		this.location = location;
	}
	
	public abstract void  draw(JComponent component, Graphics2D graphicsContext);
	
	
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

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	
	protected Point location;
	protected Dimension size; //???
}