package amoebas.java.battlevisualisation;

import javax.swing.Timer;

import amoebas.java.battleSimulation.Amoeba;
import amoebas.java.battleSimulation.BattleSimulation;

public class SimulationEngine {

    public SimulationEngine(int timerDelay, BattleSimulation battle,
            BattleAreaPanel battleArea) {

        this.battle = battle;
        this.battleArea = battleArea;
        this.timerDelay = timerDelay;
    }

    public void BattleFinished() {
        timer.stop();
        battleInProgress = false;
        // System.out.println("The battle has ended!");

        Amoeba winner = battle.getWinner();
        String winnerName = winner.getName();
        // System.out.println("Amoeba: " + winnerName + " has won!");
        // System.out.println("HP: " + winner.getHitPoints());

        synchronized (this) {
            this.notify();
        }
    }

    public void start() {
        timer = new Timer(timerDelay, new SimulationTickPerformer(battle,
                battleArea, this));
        battleInProgress = true;
        timer.start();
    }

    public void stop() {
        timer.stop();
        battleInProgress = false;
    }

    public void reset() {

        if (timer != null) {
            timer.stop();
        }

        if (battle != null) {
            battle.reset();
        }

    }

    public boolean isBattleInProgress() {
        return battleInProgress;
    }

    private BattleSimulation battle;
    private BattleAreaPanel battleArea;
    private Timer timer;
    private int timerDelay;
    private boolean battleInProgress = false;
}
