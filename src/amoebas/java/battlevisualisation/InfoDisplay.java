package amoebas.java.battlevisualisation;

public class InfoDisplay {

    public static InfoDisplay create(StatePanel panel) {
        instance = new InfoDisplay(panel);
        return instance;
    }

    public static InfoDisplay instance() {
        return instance;
    }

    protected InfoDisplay(StatePanel panel) {
        this.panel = panel;
    }

    public void showGenerationNum(int generationNum) {
        panel.getGenerationNumLabel().setText(
                StatePanel.GENERATION_NUM_TEXT + generationNum);
    }

    public void showPopulationFitness(double fitness) {
        panel.getFitnessDeltaLabel().setText(
                StatePanel.FITNESS_DELTA_TEXT + fitness);

    }

    public void showBestAmoeba(String amoeba) {
        panel.getBestAmoebaLabel()
                .setText(StatePanel.BEST_AMOEBA_TEXT + amoeba);
    }

    public void showPopulationSize(int populationSize) {
        panel.getPopulationSizeLabel().setText(
                StatePanel.POPULATION_SIZE_TEXT + populationSize);
    }

    private StatePanel panel;

    protected static InfoDisplay instance;
}
