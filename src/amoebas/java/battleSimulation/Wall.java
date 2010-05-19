package amoebas.java.battleSimulation;


import java.awt.Dimension;
import java.awt.Point;



public class Wall extends MapObject {

    public static final int DEFAULT_WEIGHT = 40;


    public Wall(Point location, Dimension size) {
        super(null, location, size);
        this.weight = DEFAULT_WEIGHT;
    }


    @Override
    public boolean isAlive() {
        return true;
    }


    @Override
    public void processCollision(MapObject other) {
        // System.out.println("collision with wall");
    }


    @Override
    public void update() {
    }

}
