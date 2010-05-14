package amoebas.java.interop;

import amoebas.java.battleSimulation.BattleSimulation;
import amoebas.java.battleSimulation.BattleArea;
import amoebas.java.battleSimulation.Wall;
import amoebas.java.battleSimulation.IBrain;
import amoebas.java.battleSimulation.Amoeba;

public class Simulator {
  private BattleArea battleArea;
  private BattleSimulation battle;

  private IBrain aBrain;
  private IBrain bBrain;

  private Amoeba aAmoeba;
  private Amoeba bAmoeba;

  public enum SimulationResult {
    FIRST,
    SECOND,
    DRAW
  }

  public Simulator(IBrain a, IBrain b) {
    this.battleArea = new BattleArea(BattleAreaDescription.size);
    this.aBrain     = a;
    this.bBrain     = b;

    for (int i = 0; i < BattleAreaDescription.wallsCount; i++) {
      Wall wall = new Wall(BattleAreaDescription.wallPositions[i],
                           BattleAreaDescription.wallDimensions[i]);
      this.battleArea.addStaticObject(wall);
    }

    this.battle = new BattleSimulation(this.battleArea);
  }

  public SimulationResult simulate() {
    this.battle.InitBattle(this.aAmoeba, this.bAmoeba);

    while (!this.battle.isOver()) {
      if (this.battle.isOverTimed()) {
        return SimulationResult.DRAW;
      }

      this.battle.update();
    }

    if (this.aAmoeba.equals(this.battle.getWinner() == this.aAmoeba)) {
      return SimulationResult.FIRST;
    } else {
      return SimulationResult.SECOND;
    }
  }
}