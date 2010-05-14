package amoebas.java.Visualizer;

import amoebas.java.battleSimulation.IBrain;
import amoebas.java.battleSimulation.Amoeba;

public class Visualizer {
  private IBrain aBrain;
  private IBrain bBrain;

  private Amoeba aAmoeba;
  private Amoeba bAmoeba;

  public Visualizer(IBrain a, IBrain b) {
    this.aBrain = a;
    this.bBrain = b;
  }
}