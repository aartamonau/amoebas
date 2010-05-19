package amoebas.java.interop;


import amoebas.java.battleSimulation.Amoeba;
import amoebas.java.battleSimulation.BattleArea;
import amoebas.java.battleSimulation.BattleSimulation;
import amoebas.java.battleSimulation.IBrain;
import amoebas.java.battleSimulation.Wall;



public class Simulator {

    public Simulator(IBrain a, IBrain b) {
        this.battleArea = new BattleArea(BattleAreaDescription.size);
        this.aBrain = a;
        this.bBrain = b;

        this.aAmoeba = new Amoeba(this.aBrain, this.battleArea,
                BattleAreaDescription.firstAmoebaPosition);

        this.bAmoeba = new Amoeba(this.bBrain, this.battleArea,
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

    public enum SimulationResult {
        DRAW, FIRST, SECOND
    }

    private Amoeba aAmoeba;

    private IBrain aBrain;
    private Amoeba bAmoeba;

    private BattleSimulation battle;

    private BattleArea battleArea;

    private IBrain bBrain;
}
