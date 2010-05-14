package amoebas.java.battlevisualisation;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import amoebas.java.battleSimulation.Thorn;



public class ThornView extends GraphicalObject {

	public ThornView(Thorn thorn) {
		super(thorn.getLocation());
		this.thorn = thorn;
	}
	
	
	@Override
	public void draw(Graphics2D graphicsContext, double xScale, double yScale) {	
		
		graphicsContext.setColor(Color.black);
				
		Rectangle2D rect = (Rectangle2D)thorn.getBoundaryRect().clone();
		
		rect.setRect(
				rect.getX() * xScale, 
				rect.getY() * yScale, 
				rect.getWidth() * xScale, 
				rect.getHeight() * yScale);
		
		graphicsContext.fill(rect);

		// debug
		// Showing the direction
		Point2D.Double velocityVector = thorn.getVelocityVector();
		
		int squareCenterX = (int)thorn.getBoundaryRect().getCenterX();
		int squareCenterY = (int)thorn.getBoundaryRect().getCenterY();

		int endX = (int)(squareCenterX + velocityVector.x * 20);
		int endY = (int)(squareCenterY + velocityVector.y * 20);
		
		graphicsContext.setColor(Color.RED);
		graphicsContext.drawLine(
				(int)(squareCenterX  * xScale), 
				(int)(squareCenterY * yScale), 
				(int)(endX * xScale), 
				(int)(endY * yScale));			
		
	}

	
	@Override
	public boolean isValid() {
		return thorn.isAlive();
	}
	
	
	protected Thorn thorn;	
}
