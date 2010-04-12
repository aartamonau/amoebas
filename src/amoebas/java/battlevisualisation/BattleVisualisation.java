package amoebas.java.battlevisualisation;

import java.awt.EventQueue;

import javax.swing.JFrame;


/**
 * @author m 
 */
public class BattleVisualisation {

	/**
	 * Application's entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.print("Battle Visualisation");
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame mainFrame = new MainFrame();
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setVisible(true);	
			}
		});
	}

}
