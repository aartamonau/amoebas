package amoebas.java.battlevisualisation;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;



/**
 * 
 * @author m
 * 
 */
public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Battle Visualisation");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 4 * 3, screenSize.height / 4 * 3);

        setLocationRelativeTo(null); // location in the screen center

        setBackground(Color.green);
    }

}
