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
	
	
	public static final String GENERATION_NUM_TEXT = "Generation: ";
	public static final String POPULATION_SIZE_TEXT = "Population Size : ";
	public static final String FITNESS_DELTA_TEXT = "Population Fitness Delta: ";
	public static final String BEST_AMOEBA_TEXT = "Best amoeba: ";
	
	
	public StatePanel() {
		
		setLayout(new GridLayout(20, 1));
		
		setBackground(Color.WHITE);
				
		generationNumLabel = new JLabel(GENERATION_NUM_TEXT);
		populationSizeLabel = new JLabel(POPULATION_SIZE_TEXT);
		fitnessDeltaLabel = new JLabel(FITNESS_DELTA_TEXT);
		bestAmoebaLabel = new JLabel(BEST_AMOEBA_TEXT);
		
		add(generationNumLabel);
		add(populationSizeLabel);
		add(fitnessDeltaLabel);
		add(bestAmoebaLabel);	
	}
	
	

	public JLabel getGenerationNumLabel() {
		return generationNumLabel;
	}
	
	public JLabel getPopulationSizeLabel() {
		return populationSizeLabel;
	}
	
	public JLabel getFitnessDeltaLabel() {
		return fitnessDeltaLabel;
	}
	
	public JLabel getBestAmoebaLabel() {
		return bestAmoebaLabel;
	}



	private JLabel generationNumLabel;
	private JLabel populationSizeLabel;
	private JLabel fitnessDeltaLabel;
	private JLabel bestAmoebaLabel;
}
