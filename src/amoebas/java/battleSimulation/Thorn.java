package amoebas.java.battleSimulation;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;


public class Thorn extends MovableObject {

	public static final int SPEED = 10;
	public static final int SIZE = 5;
	public static final int WEIGHT = 500;
		
	
	public Thorn(Point location, Dimension size) {
		super(null, location, size);		
		this.hitPoints = 1;
		this.weight = WEIGHT;
	}
		
	
	public Thorn(Point2D.Double amoebaVelocityVector, Point location) {		
		this(location, new Dimension(SIZE, SIZE));
		this.velocityVector = computeVelocityVector(amoebaVelocityVector);		
	}
	
	
	@Override
	public void update() {
		move(velocityVector.x, velocityVector.y);
	}


	@Override
	public void processCollision(MapObject other) {
		computeCollisionDamage(other);		
	}


	@Override
	public void computeCollisionDamage(MapObject other) {
		hitPoints = 0;
	}
	
	
	private Point2D.Double computeVelocityVector(Point2D.Double thornDirection) {
		
		double x = Math.abs(thornDirection.x);
		double y = Math.abs(thornDirection.y);
			
		// Computing the cos and sin of the angle, that the thorn's
		// velocity vector will be rotated on
		
		double angleCos;
		
		if ( x != 0 || y != 0 ) {
			angleCos = x / (Math.sqrt(x * x + y * y));
		} else {
			angleCos = new Random().nextDouble();
		}
				
		double angleSin = Math.sqrt(1 - angleCos * angleCos);
		
		// Rotating the velocity vector
		Point2D.Double result = new Point2D.Double(SPEED, 0);
		int newX = (int) Math.round(result.x * angleCos - result.y * angleSin);
		int newY = (int) Math.round(result.x * angleSin + result.y * angleCos);
		
		result.x = newX;
		result.y = newY;
		
		// Putting the vector into the same quarter
		// as the amoeba's direction
		
		if ( thornDirection.x < 0 ) {
			result.x *= -1;
		}
		
		if ( thornDirection.y < 0 ) {
			result.y *= -1;
		}
			
		
		return result;
	}

		
}
