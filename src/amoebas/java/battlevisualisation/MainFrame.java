package amoebas.java.battlevisualisation;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

import amoebas.java.battleSimulation.Amoeba;
import amoebas.java.battleSimulation.BattleArea;
import amoebas.java.battleSimulation.BattleSimulation;
import amoebas.java.battleSimulation.Wall;
// import amoebas.java.battleSimulation.battle;


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
				
		InfoDisplay.instance().showGenerationNum(12);
		InfoDisplay.instance().showPopulationFitness(1.0);
		InfoDisplay.instance().showPopulationSize(100500);	
	}
	
}
