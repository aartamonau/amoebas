package amoebas.java.battlevisualisation;


public class InfoDisplay {

    protected static InfoDisplay instance;


    protected InfoDisplay(StatePanel panel) {
        this.panel = panel;
    }


    public void showBestAmoeba(String amoeba) {
        this.panel.getBestAmoebaLabel().setText(
                StatePanel.BEST_AMOEBA_TEXT + amoeba);
    }


    public void showGenerationNum(int generationNum) {
        this.panel.getGenerationNumLabel().setText(
                StatePanel.GENERATION_NUM_TEXT + generationNum);
    }


    public void showPopulationFitness(double fitness) {
        this.panel.getFitnessDeltaLabel().setText(
                StatePanel.FITNESS_DELTA_TEXT + fitness);

    }


    public void showPopulationSize(int populationSize) {
        this.panel.getPopulationSizeLabel().setText(
                StatePanel.POPULATION_SIZE_TEXT + populationSize);
    }


    public static InfoDisplay create(StatePanel panel) {
        instance = new InfoDisplay(panel);
        return instance;
    }


    public static InfoDisplay instance() {
        return instance;
    }

    private StatePanel panel;
}
