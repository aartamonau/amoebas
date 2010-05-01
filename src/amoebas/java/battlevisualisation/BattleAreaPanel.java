/**
 * 
 */
package amoebas.java.battlevisualisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import amoebas.java.battleSimulation.BattleArea;


/**
 * This panel represents the battlefield.
 * All the objects that are to be displayed are stored in
 * "objects" list.
 *   
 * TODO: BattleAreaPanel creation process. (builder?)
 * +Init with BattleAreaModel.
 *  
 * @author m
 *
 */
public class BattleAreaPanel extends JPanel {

	public BattleAreaPanel(BattleArea battleArea) {	
		
		super();
		
		setDoubleBuffered(true);
		setBackground(Color.PINK);
		setBorder(new EtchedBorder());
		setFocusable(true);
		
		this.battleArea = battleArea;
		objects = new LinkedList<GraphicalObject>();	
	}
	
	
	public void addGraphicalObject(GraphicalObject object) {
		objects.add(object);
	}
	
	
	public void removeGraphicalObject(GraphicalObject object) {
		objects.remove(object);
	}
	
	
	
	@Override
	protected void paintComponent(Graphics graphicsContext) {
		
		super.paintComponent(graphicsContext);	
		
		Graphics2D g2d = (Graphics2D) graphicsContext;
		
		Dimension panelSize = getSize();
		Dimension battleAreaSize = battleArea.getSize();
		
		double xScale = 1.0 * panelSize.width / battleAreaSize.width;
		double yScale = 1.0 * panelSize.height / battleAreaSize.height;
		
		Iterator<GraphicalObject> graphicalObjectsIter = objects.iterator();
		
		while( graphicalObjectsIter.hasNext() ) {
			GraphicalObject object = graphicalObjectsIter.next();
			
			if( object.isValid() ){			
				object.draw(g2d, xScale, yScale);	
			} else {
				graphicalObjectsIter.remove();		
			}		
		}
							
	}
	
	
	
	private BattleArea battleArea;
	private List<GraphicalObject> objects;
}
