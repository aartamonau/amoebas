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
import amoebas.java.battleSimulation.battle;


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
		gbcStatePanel.weightx = 0.07;
		gbcStatePanel.weighty = 1.0;
		gbcStatePanel.fill = GridBagConstraints.BOTH;
		
		
		
		// Adding boundary walls
		// let it temporarily be here
		
		final int WALL_THICKNESS = 10;
		
		Dimension size = new Dimension(1024, 700);
		
		Wall northWall = new Wall(new Point(0, 0), 
				new Dimension(size.width, WALL_THICKNESS));
		
		Wall southWall = new Wall(new Point(0, size.height - WALL_THICKNESS), 
				new Dimension(size.width, WALL_THICKNESS));
		
		Wall eastWall = new Wall(new Point(size.width - WALL_THICKNESS, 0), 
				new Dimension(WALL_THICKNESS, size.height));
		
		Wall westWall = new Wall(new Point(0, 0), 
				new Dimension(WALL_THICKNESS, size.height));
		
		
		final BattleArea battleArea = new BattleArea(size);
		battleArea.addStaticObject(northWall);
		battleArea.addStaticObject(southWall);
		battleArea.addStaticObject(eastWall);
		battleArea.addStaticObject(westWall);	
		
		StatePanel statePanel = new StatePanel();
		final BattleAreaPanel battleAreaPanel = new BattleAreaPanel(battleArea);
		
		battleAreaPanel.addGraphicalObject(new WallView(westWall));
		battleAreaPanel.addGraphicalObject(new WallView(northWall));
		battleAreaPanel.addGraphicalObject(new WallView(eastWall));
		battleAreaPanel.addGraphicalObject(new WallView(southWall));
			

		battleArea.addThornShotListener(battleAreaPanel);
		
		
		final BattleSimulation battle = new BattleSimulation(battleArea);
		
		Amoeba amoeba = new amoebas.java.battleSimulation.Amoeba(new Point(20, 20));
		
	    Amoeba amoeba1 = new amoebas.java.battleSimulation.Amoeba(new Point(600, 200));
		
		battle.InitBattle(amoeba, amoeba1);
		
		
		battleAreaPanel.addGraphicalObject(new AmoebaView(amoeba));		
		battleAreaPanel.addGraphicalObject(new AmoebaView(amoeba1));
		

		final SimulationEngine engine = new SimulationEngine(50, battle, battleAreaPanel, null);
		
		add(battleAreaPanel, gbcBattleAreaPanel);
		add(statePanel, gbcStatePanel);
		
		engine.start();		
		
		InfoDisplay.create(statePanel);
		InfoDisplay.instance().showGenerationNum(12);
		InfoDisplay.instance().showBestAmoeba(amoeba1);
		InfoDisplay.instance().showPopulationFitness(1.0);
		InfoDisplay.instance().showPopulationSize(100500);
		
		Thread newTask = new Thread(new Runnable() {		
			
			@Override
			public void run() {
								
				try {
					
					System.out.println("suspending");
					Thread.sleep(5000);
					engine.reset();
					System.out.println("resuming");
					Thread.sleep(5000);

					Amoeba amoeba = new amoebas.java.battleSimulation.Amoeba(new Point(20, 20));								
				    Amoeba amoeba1 = new amoebas.java.battleSimulation.Amoeba(new Point(600, 300));
				    
				    battleAreaPanel.addGraphicalObject(new AmoebaView(amoeba));		
					battleAreaPanel.addGraphicalObject(new AmoebaView(amoeba1));
					
					battle.InitBattle(amoeba1, amoeba);				
					engine.start();
					
				} catch (InterruptedException e) {			
					e.printStackTrace();
				}
				
			}
		});
		
		newTask.start();
				
	}
		
}
