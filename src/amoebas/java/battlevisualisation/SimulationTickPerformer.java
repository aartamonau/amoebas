package amoebas.java.battlevisualisation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import amoebas.java.battleSimulation.BattleSimulation;



public class SimulationTickPerformer implements ActionListener {

	public SimulationTickPerformer(BattleSimulation battle, 
			BattleAreaPanel battleArea, 
			StatePanel statsPanel, 
			SimulationEngine engine) {
		
		this.battle = battle;
		this.battleArea = battleArea;	
		this.statsPanel = statsPanel;
		this.engine = engine;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		
		battle.update();
		battleArea.repaint();
		
		if ( battle.isOver() ) {
			engine.BattleFinished();
		}
		
	}
	
	
	protected BattleSimulation battle;
	protected JPanel battleArea;	
	protected JPanel statsPanel;
	protected SimulationEngine engine;
}
