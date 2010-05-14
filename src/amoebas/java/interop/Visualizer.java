package amoebas.java.interop;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import amoebas.java.battleSimulation.BattleArea;
import amoebas.java.battleSimulation.BattleSimulation;
import amoebas.java.battleSimulation.IBrain;
import amoebas.java.battleSimulation.Amoeba;
import amoebas.java.battleSimulation.Wall;
import amoebas.java.battlevisualisation.AmoebaView;
import amoebas.java.battlevisualisation.BattleAreaPanel;
import amoebas.java.battlevisualisation.InfoDisplay;
import amoebas.java.battlevisualisation.MainFrame;
import amoebas.java.battlevisualisation.SimulationEngine;
import amoebas.java.battlevisualisation.StatePanel;
import amoebas.java.battlevisualisation.WallView;

public class Visualizer {
  private IBrain aBrain;
  private IBrain bBrain;

  private Amoeba aAmoeba;
  private Amoeba bAmoeba;
  
  private BattleArea battleArea;
  private BattleSimulation battle;
  
  private StatePanel statePanel;
  private BattleAreaPanel battleAreaPanel;
  
  private static final int TIMER_DELAY = 20; 



  public Visualizer() {
	      
	JFrame mainFrame = new MainFrame();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	
	mainFrame.setLayout(new GridBagLayout());
	
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
	
	
	statePanel = new StatePanel();
	InfoDisplay.create(statePanel);
	
	battleArea = new BattleArea(BattleAreaDescription.size);
	battleAreaPanel = new BattleAreaPanel(battleArea);
	this.battleArea.addThornShotListener(battleAreaPanel);

	Wall[] walls = BattleAreaDescription.createWalls();

    for (int i = 0; i < walls.length; i++) {
      this.battleArea.addStaticObject(walls[i]);
      
      this.battleAreaPanel.addGraphicalObject(
    		  new WallView(walls[i]));
    }
    
    this.battleArea.flushNewItems();

    
    battleArea.addThornShotListener(battleAreaPanel);
    
	mainFrame.add(battleAreaPanel, gbcBattleAreaPanel);
	mainFrame.add(statePanel, gbcStatePanel);
	
	mainFrame.setVisible(true);	
  }
 
  
  public void show(IBrain a, IBrain b) {
	
	this.aBrain = a;
	this.bBrain = b;
	    
    this.aAmoeba    = new Amoeba(
    		this.aBrain,            
            battleArea,
            BattleAreaDescription.firstAmoebaPosition);
	    
    this.bAmoeba    = new Amoeba(
    		this.bBrain,
    		battleArea,
    		BattleAreaDescription.secondAmoebaPosition);
    
    
    battleArea.ClearMovableObjects();
    
    battleAreaPanel.addGraphicalObject(
    	new AmoebaView(aAmoeba));
    
    battleAreaPanel.addGraphicalObject(
    	new AmoebaView(bAmoeba));
    
    this.battle = new BattleSimulation(this.battleArea);
    this.battle.InitBattle(aAmoeba, bAmoeba);
    
    SimulationEngine engine = 
    	new SimulationEngine(TIMER_DELAY, battle, battleAreaPanel);
    
    engine.start();
  }
  
  
}