/**
 *
 */
package amoebas.java.battlevisualisation;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.net.URL;

import amoebas.java.battleSimulation.Amoeba;



/**
 * @author m
 *
 */
public class AmoebaView extends GraphicalObject {

  public AmoebaView(Amoeba amoeba) {
    super(amoeba.getLocation());
    this.amoebaModel = amoeba;

    URL imgURL = getClass().getResource("amoeba.jpg");
    img = Toolkit.getDefaultToolkit().getImage(imgURL);
  }


  @Override
  public boolean isValid() {
    return amoebaModel.isAlive();
  }
  
 
  @Override
  public void draw(Graphics2D graphicsContext, double xScale, double yScale) {

    Point loc = amoebaModel.getLocation();

    graphicsContext.drawImage(img,
        (int)(loc.x * xScale),
        (int)(loc.y * yScale),
        (int)(Amoeba.AMOEBA_WIDTH * xScale),
        (int)(Amoeba.AMOEBA_HEIGHT * yScale), null);

    // Displaying the direction
    Point velocityVector = amoebaModel.getVelocityVector();

    int squareCenterX = (int)amoebaModel.getBoundaryRect().getCenterX();
    int squareCenterY = (int)amoebaModel.getBoundaryRect().getCenterY();

    int endX = squareCenterX + velocityVector.x * 50;
    int endY = squareCenterY + velocityVector.y * 50;

    graphicsContext.setColor(Color.blue);

    graphicsContext.drawLine(
        (int)(squareCenterX  * xScale),
        (int)(squareCenterY * yScale),
        (int)(endX * xScale),
        (int)(endY * yScale));
    
    
    // Displaying the aim vector
    Point aimVector = amoebaModel.lastAimVector;
    
    graphicsContext.setColor(Color.green);

    graphicsContext.drawLine(
        (int)(squareCenterX  * xScale),
        (int)(squareCenterY * yScale),
        (int)((aimVector.x * 3 + squareCenterX) * xScale),
        (int)((aimVector.y * 3 + squareCenterY)* yScale));
    
    
    // Displaying a HP Bar
    graphicsContext.setColor(Color.GRAY);
    
    Rectangle2D rect = amoebaModel.getBoundaryRect();
    
    int squareX = amoebaModel.getLocation().x;
    int squareY = amoebaModel.getLocation().y;
    
    int height = (int)Math.round( 0.1 * rect.getHeight() );
    int width = (int)rect.getWidth();
    
    
    graphicsContext.fillRect(
    		(int)(squareX * xScale), 
    		(int)(squareY * yScale), 
    		(int)(width * xScale), 
    		(int)(height * yScale));
    
    graphicsContext.setColor(Color.RED);
        
    width *= 1.0 * amoebaModel.getHitPoints() / Amoeba.MAX_HP;
    
    graphicsContext.fillRect(
    		(int)(squareX * xScale), 
    		(int)(squareY * yScale), 
    		(int)(width * xScale), 
    		(int)(height * yScale));
   
  }


  private Image img;
  private Amoeba amoebaModel;
}
