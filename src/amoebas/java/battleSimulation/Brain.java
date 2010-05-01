package amoebas.java.battleSimulation;

import java.awt.Point;
import java.util.Random;


/**
 * 
 * A stub to simulate amoeba's brain.
 * 
 * @author m
 *
 */
public class Brain {
	
	
	public Brain() {
		randGenerator = new Random();
		retreivalNum = 0;
		movementVector = new Point(randGenerator.nextInt(5), randGenerator.nextInt(5));
	}
	
	
	public Point getMovementVector() {
		
		++retreivalNum;
		
		if ( retreivalNum == 20 )  {
			
			retreivalNum = 0;
			
			int signX = randGenerator.nextBoolean() ? -1 : 1;
			int signY = randGenerator.nextBoolean() ? -1 : 1;
			
			movementVector = new Point(randGenerator.nextInt(5) * signX,
					randGenerator.nextInt(5) * signY);
		}
		
		return movementVector;
	}
	
	
	public Point getAimVector(Point velocityVector) {
			
		if ( velocityVector.y != 0 || velocityVector.x != 0 ) {
			return velocityVector;
		} 
		
		return new Point(randGenerator.nextInt(10), randGenerator.nextInt(10)+1);
	}
	
	
	public boolean shallWeShoot() {
		return randGenerator.nextDouble() < 0.02;
	}
	
	
	private Random randGenerator;
	private Point movementVector;
	private int retreivalNum;
}
