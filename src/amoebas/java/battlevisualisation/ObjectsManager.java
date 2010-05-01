package amoebas.java.battlevisualisation;

import amoebas.java.battleSimulation.BattleArea;
import amoebas.java.battleSimulation.Thorn;


public class ObjectsManager {

	public static ObjectsManager getManager() {
		return instance;		
	}
	
	protected ObjectsManager(BattleArea battleArea, BattleAreaPanel view) {
		this.battleArea = battleArea;
		this.battleAreaView = view;
	}
	
	
	public static void Init(BattleArea battleArea, BattleAreaPanel view) {
		instance = new ObjectsManager(battleArea, view);
	}
	
	public void addThorn(Thorn thorn) {
		battleArea.addMovableObject(thorn);
		battleAreaView.addGraphicalObject(new ThornView(thorn));
	}
	
	
	private static ObjectsManager instance;
	
	private BattleArea battleArea;
	private BattleAreaPanel battleAreaView;

}
