/**
 * 
 */
package amoebas.java.battlevisualisation;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JComponent;


/**
 * @author m
 *
 */
public class Amoeba extends GraphicalObject {

	public Amoeba(Point initialLocation) {			
		//+ init amoeba model
		super(initialLocation);
		URL imgURL = getClass().getResource("amoeba.jpg");
		img = Toolkit.getDefaultToolkit().getImage(imgURL);		
	}
	
	
	@Override
	public void draw(JComponent component, Graphics2D graphicsContext) {
		// location = Utils.ModelToScreenLocation(model.getLocation());
		graphicsContext.drawImage(img, location.x, location.y, component);
	}

	
	private Image img;
	//private AmoebaModel model;
}
