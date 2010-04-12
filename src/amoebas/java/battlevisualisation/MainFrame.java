package amoebas.java.battlevisualisation;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;


/**
 *  
 * @author m
 *
 */
public class MainFrame extends JFrame {
	
	public MainFrame(){
		
		setTitle("Battle Visualisation");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 4 * 3, screenSize.height / 4 * 3 );
		
		setLocationRelativeTo(null);	// location in the screen center
		
		setBackground(Color.green);		
		
		addChildPanels();
	}
	
	
	private void addChildPanels() {
		
		setLayout(new GridBagLayout());
		
		// Some magic xD
		GridBagConstraints gbcBattleAreaPanel = new GridBagConstraints();
		gbcBattleAreaPanel.gridx = 0;
		gbcBattleAreaPanel.gridy = 0;
		gbcBattleAreaPanel.weightx = 1.0;
		gbcBattleAreaPanel.weighty = 1.0;
		gbcBattleAreaPanel.fill = GridBagConstraints.BOTH;
		
		GridBagConstraints gbcStatePanel = new GridBagConstraints();
		gbcStatePanel.gridx = 1;
		gbcStatePanel.gridy = 0;
		gbcStatePanel.weightx = 0.15;
		gbcStatePanel.weighty = 1.0;
		gbcStatePanel.fill = GridBagConstraints.BOTH;
		
		
		StatePanel statePanel = new StatePanel();
		BattleAreaPanel battleAreaPanel = new BattleAreaPanel();
		
		add(battleAreaPanel, gbcBattleAreaPanel);
		add(statePanel, gbcStatePanel);
		
		// Just for demonstration
		battleAreaPanel.addGraphicalObject(new Amoeba(new Point(0, 0)));					
	}
	
}
