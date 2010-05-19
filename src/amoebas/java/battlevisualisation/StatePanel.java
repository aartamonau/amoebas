/**
 * 
 */
package amoebas.java.battlevisualisation;


import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 * @author m
 * 
 */
public class StatePanel extends JPanel {

    public static final String BEST_AMOEBA_TEXT = "Best amoeba: ";
    public static final String FITNESS_DELTA_TEXT = "Population Fitness Delta: ";
    public static final String GENERATION_NUM_TEXT = "Generation: ";
    public static final String POPULATION_SIZE_TEXT = "Population Size : ";


    public StatePanel() {

        setLayout(new GridLayout(20, 1));

        setBackground(Color.WHITE);

        this.generationNumLabel = new JLabel(GENERATION_NUM_TEXT);
        this.populationSizeLabel = new JLabel(POPULATION_SIZE_TEXT);
        this.fitnessDeltaLabel = new JLabel(FITNESS_DELTA_TEXT);
        this.bestAmoebaLabel = new JLabel(BEST_AMOEBA_TEXT);

        add(this.generationNumLabel);
        add(this.populationSizeLabel);
        add(this.fitnessDeltaLabel);
        add(this.bestAmoebaLabel);
    }


    public JLabel getBestAmoebaLabel() {
        return this.bestAmoebaLabel;
    }


    public JLabel getFitnessDeltaLabel() {
        return this.fitnessDeltaLabel;
    }


    public JLabel getGenerationNumLabel() {
        return this.generationNumLabel;
    }


    public JLabel getPopulationSizeLabel() {
        return this.populationSizeLabel;
    }

    private JLabel bestAmoebaLabel;
    private JLabel fitnessDeltaLabel;
    private JLabel generationNumLabel;
    private JLabel populationSizeLabel;
}
