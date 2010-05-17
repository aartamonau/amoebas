package amoebas.java.interop;

import amoebas.java.battleSimulation.Amoeba;
import amoebas.java.battleSimulation.BattleArea;
import amoebas.java.battleSimulation.BattleSimulation;
import amoebas.java.battleSimulation.IBrain;
import amoebas.java.battleSimulation.Wall;

public class Simulator {
    private BattleArea battleArea;
    private BattleSimulation battle;

    private IBrain aBrain;
    private IBrain bBrain;

    private Amoeba aAmoeba;
    private Amoeba bAmoeba;

    public enum SimulationResult {
        FIRST, SECOND, DRAW
    }

    public Simulator(IBrain a, IBrain b) {
        this.battleArea = new BattleArea(BattleAreaDescription.size);
        this.aBrain = a;
        this.bBrain = b;

        this.aAmoeba = new Amoeba(this.aBrain, battleArea,
                BattleAreaDescription.firstAmoebaPosition);

        this.bAmoeba = new Amoeba(this.bBrain, battleArea,
                BattleAreaDescription.secondAmoebaPosition);

        Wall[] walls = BattleAreaDescription.createWalls();

        for (int i = 0; i < walls.length; i++) {
            this.battleArea.addStaticObject(walls[i]);
        }

        this.battle = new BattleSimulation(this.battleArea);
    }

    public SimulationResult simulate() {
        this.battle.InitBattle(this.aAmoeba, this.bAmoeba);

        while (!this.battle.isOver()) {
            this.battle.update();
        }

        if (this.battle.isOverTimed()) {
            return SimulationResult.DRAW;
        }

        System.out.println("simulation is over");
        if (this.aAmoeba.equals(this.battle.getWinner())) {
            return SimulationResult.FIRST;
        } else {
            return SimulationResult.SECOND;
        }
    }
}