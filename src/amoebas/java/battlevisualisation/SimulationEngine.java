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
        this.timer.stop();
        this.battleInProgress = false;
        // System.out.println("The battle has ended!");

        Amoeba winner = this.battle.getWinner();
        String winnerName = winner.getName();
        // System.out.println("Amoeba: " + winnerName + " has won!");
        // System.out.println("HP: " + winner.getHitPoints());

        synchronized (this) {
            this.notify();
        }
    }


    public boolean isBattleInProgress() {
        return this.battleInProgress;
    }


    public void reset() {

        if (this.timer != null) {
            this.timer.stop();
        }

        if (this.battle != null) {
            this.battle.reset();
        }

    }


    public void start() {
        this.timer = new Timer(this.timerDelay, new SimulationTickPerformer(
                this.battle, this.battleArea, this));
        this.battleInProgress = true;
        this.timer.start();
    }


    public void stop() {
        this.timer.stop();
        this.battleInProgress = false;
    }

    private BattleSimulation battle;
    private BattleAreaPanel battleArea;
    private boolean battleInProgress = false;
    private Timer timer;
    private int timerDelay;
}
