package amoebas.java.battlevisualisation;

import amoebas.java.battleSimulation.Brain;
import amoebas.java.interop.Visualizer;

/**
 * @author m
 */
public class BattleVisualisation {

    /**
     * Application's entry point.
     * 
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        // System.out.println("Battle Visualisation");

        final Visualizer visualizer = new Visualizer();

        Brain brain1 = new Brain();
        Brain brain2 = new Brain();
        visualizer.showSynchronously(brain1, brain2);

        // Timer timer = new Timer(10000, new ActionListener() {
        //
        // @Override
        // public void actionPerformed(ActionEvent arg0) {
        //
        // Brain brain1 = new Brain();
        // Brain brain2 = new Brain();
        // visualizer.show(brain1, brain2);
        //
        // }
        // });
        //
        // timer.start();

        // ---------------------------
        // ---------------------------

        // EventQueue.invokeLater(new Runnable() {
        //
        // @Override
        // public void run() {
        // JFrame mainFrame = new MainFrame();
        // mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // mainFrame.setVisible(true);
        // }
        // });
    }

}
