package amoebas.java.battleSimulation;


import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



public class BattleArea {

	public BattleArea(Dimension size) {
		
		
		newMovableObjects = new LinkedList<MovableObject>();
		newStaticObjects = new LinkedList<MapObject>();
		
		movableObjects = new ArrayList<MovableObject>();
		staticObjects = new ArrayList<MapObject>();
		this.size = size;		
	}
	
	
	public void processCollisions() {
		
		int movableObjectsNum = movableObjects.size();
		int staticObjectsNum = staticObjects.size();
		
		for( int i = 0; i < movableObjectsNum; ++i ) {
					
			for ( int j = i + 1; j < movableObjectsNum; ++j ) {
				
				if ( movableObjects.get(i).intersectsWith(movableObjects.get(j))) {
					
					movableObjects.get(i).processCollision(movableObjects.get(j));
					movableObjects.get(j).processCollision(movableObjects.get(i));
				}
			}
			
			for ( int j = 0; j < staticObjectsNum; ++j ) {			
				
				if ( movableObjects.get(i).intersectsWith(staticObjects.get(j))) {
					
					movableObjects.get(i).processCollision(staticObjects.get(j));
					staticObjects.get(j).processCollision(movableObjects.get(i));
				}
			}
			
		}
		
	}
	
	
	public void update() {
		
	
		Iterator<MovableObject> movableObjectsIter = movableObjects.iterator();
		
		while( movableObjectsIter.hasNext() ) {
			
			MovableObject obj = movableObjectsIter.next();
			
			obj.update(this);
			
			if (!obj.isAlive()) {
				movableObjectsIter.remove();
			}
		}
		
		Iterator<MapObject> staticObjectsIter = staticObjects.iterator();
	
		while( staticObjectsIter.hasNext() ) {
			
			MapObject obj = staticObjectsIter.next();
			
			obj.update(this);
			
			if (!obj.isAlive()) {
				staticObjectsIter.remove();
			}
		}
		
		flushNewItems();		
		
	}
	
	
	public void flushNewItems() {
		
		for( MovableObject obj : newMovableObjects ) {
			movableObjects.add(obj);
		}
		
		for( MapObject obj : newStaticObjects ) {
			staticObjects.add(obj);
		}
		
		newMovableObjects.clear();
		newStaticObjects.clear();
	}
	
	
	public void ClearMovableObjects() {		
		movableObjects.clear();		
	}
	
	
	public void addMovableObject(MovableObject obj) {		
		newMovableObjects.add(obj);			
	}
	
	public void addStaticObject(MapObject obj) {		
		newStaticObjects.add(obj);		
	}
	
	
	public void setMovableObjects(List<MovableObject> movableObjects) {	
		this.movableObjects = movableObjects;
	}

	
	public void setStaticObjects(List<MapObject> staticObjects) {		
		this.staticObjects = staticObjects;	
	}

	
	public Dimension getSize() {
		return size;
	}
	
	public void setSize(Dimension size) {		
		this.size = size;
	}


	private Queue<MovableObject> newMovableObjects;
	private Queue<MapObject> newStaticObjects;
	
	private List<MovableObject> movableObjects;
	private List<MapObject> staticObjects;
	
	private Dimension size;
}
