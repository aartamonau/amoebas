package amoebas.java.battleSimulation;

import java.awt.Dimension;
import java.awt.Point;


public class Wall extends MapObject {
	
	public static final int DEFAULT_WEIGHT = 70;

	
	public Wall(Point location, Dimension size) {
		super(location, size);
		this.weight = DEFAULT_WEIGHT;
	}

	@Override
	public void update(BattleArea battleArea) {		
	}	
	
	
	@Override
	public boolean isAlive() {
		return true;
	}
	
}
