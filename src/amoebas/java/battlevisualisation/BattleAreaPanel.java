/**
 * 
 */
package amoebas.java.battlevisualisation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


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

	public BattleAreaPanel() {	
		
		super();
		
		setDoubleBuffered(true);
		setBackground(Color.PINK);
		setBorder(new EtchedBorder());
		setFocusable(true);
		
		objects = new LinkedList<GraphicalObject>();
		
		addKeyListener(new KeyboardHandler());
	}
	
	
	public BattleAreaPanel(List<GraphicalObject> objects) {
		
		this();
		this.objects = objects;
	}
	
	
	
	public void addGraphicalObject(GraphicalObject object) {
		objects.add(object);
	}
	
	
	public void removeGraphicalObject(GraphicalObject object) {
		objects.remove(object);
	}
	
	
	
	public void moveObjects(int xStep, int yStep) {
		
		for( GraphicalObject obj : objects ) {
			obj.setX(obj.getX() + xStep);
			obj.setY(obj.getY() + yStep);
		}
		
		repaint();
	}
	
	
	@Override
	protected void paintComponent(Graphics graphicsContext) {
		
		super.paintComponent(graphicsContext);	
		
		Graphics2D g2d = (Graphics2D) graphicsContext;
		
		for( GraphicalObject object : objects ) {		
			object.draw(this, g2d);			
		}
										
	}
	
	
	/**
	 * Keyboard events' handler.
	 * Whenever the user presses an arrow key, all the
	 * graphical objects that are stored in the object of
	 * the enclosing class move in the appropriate direction.
	 * 
	 * @author m
	 *
	 */
	private class KeyboardHandler extends KeyAdapter {	

		public static final int MOVE_STEP = 5;
			
		
		@Override
		public synchronized void keyPressed(KeyEvent e) {			
			
			int pressedKey = e.getKeyCode();
			activeKeys.add(pressedKey);
			
			int xStep = 0;
			int yStep = 0;
			
			if ( activeKeys.contains(KeyEvent.VK_LEFT) ) {
				xStep = -MOVE_STEP;
			}
			
			if ( activeKeys.contains(KeyEvent.VK_RIGHT) ) {
				xStep = MOVE_STEP;
			}
			
			if ( activeKeys.contains(KeyEvent.VK_DOWN) ) {
				yStep = MOVE_STEP;
			}

			if ( activeKeys.contains(KeyEvent.VK_UP) ) {
				yStep = -MOVE_STEP;	
			}

			moveObjects(xStep, yStep);			
			
			super.keyPressed(e);
		}	
		
		
		@Override
		public synchronized void keyReleased(KeyEvent e) {
			
			activeKeys.remove(e.getKeyCode());
			
			super.keyReleased(e);
		}
		
		
		private Set<Integer> activeKeys = new HashSet<Integer>();
	}
	
	
	private List<GraphicalObject> objects;
}
