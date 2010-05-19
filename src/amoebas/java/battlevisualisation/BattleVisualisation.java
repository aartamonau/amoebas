package amoebas.java.battlevisualisation;


import amoebas.java.battleSimulation.Brain;
import amoebas.java.interop.Visualizer;



/**
 * @author m
 */
public class BattleVisualisation {

    /**
     * Used for testing
     * 
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        final Visualizer visualizer = new Visualizer();

        Brain brain1 = new Brain();
        Brain brain2 = new Brain();
        visualizer.showSynchronously(brain1, brain2);

    }

}
