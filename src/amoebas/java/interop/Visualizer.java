package amoebas.java.interop;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import amoebas.java.battleSimulation.Amoeba;
import amoebas.java.battleSimulation.BattleArea;
import amoebas.java.battleSimulation.BattleSimulation;
import amoebas.java.battleSimulation.IBrain;
import amoebas.java.battleSimulation.Wall;
import amoebas.java.battlevisualisation.AmoebaView;
import amoebas.java.battlevisualisation.BattleAreaPanel;
import amoebas.java.battlevisualisation.InfoDisplay;
import amoebas.java.battlevisualisation.MainFrame;
import amoebas.java.battlevisualisation.SimulationEngine;
import amoebas.java.battlevisualisation.StatePanel;
import amoebas.java.battlevisualisation.WallView;



public class Visualizer {

    private static final int TIMER_DELAY = 30;


    public Visualizer() {

        JFrame mainFrame = new MainFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setLayout(new GridBagLayout());

        // Some magic xD
        GridBagConstraints gbcBattleAreaPanel = new GridBagConstraints();
        gbcBattleAreaPanel.gridx = 0;
        gbcBattleAreaPanel.gridy = 0;
        gbcBattleAreaPanel.weightx = 1.0;
        gbcBattleAreaPanel.weighty = 1.0;
        gbcBattleAreaPanel.fill = GridBagConstraints.BOTH;

        GridBagConstraints gbcStatePanel = new GridBagConstraints();
        gbcStatePanel.gridx = 1;
        gbcStatePanel.gridy = 0;
        gbcStatePanel.weightx = 0.07;
        gbcStatePanel.weighty = 1.0;
        gbcStatePanel.fill = GridBagConstraints.BOTH;

        this.statePanel = new StatePanel();
        InfoDisplay.create(this.statePanel);

        this.battleArea = new BattleArea(BattleAreaDescription.size);
        this.battleAreaPanel = new BattleAreaPanel(this.battleArea);
        this.battleArea.addThornShotListener(this.battleAreaPanel);

        Wall[] walls = BattleAreaDescription.createWalls();

        for (int i = 0; i < walls.length; i++) {
            this.battleArea.addStaticObject(walls[i]);

            this.battleAreaPanel.addGraphicalObject(new WallView(walls[i]));
        }

        this.battleArea.flushNewItems();

        this.battleArea.addThornShotListener(this.battleAreaPanel);

        mainFrame.add(this.battleAreaPanel, gbcBattleAreaPanel);
        mainFrame.add(this.statePanel, gbcStatePanel);

        mainFrame.setVisible(true);
    }


    public void showSynchronously(IBrain a, IBrain b)
            throws InterruptedException {

        this.aBrain = a;
        this.bBrain = b;

        this.aAmoeba = new Amoeba(this.aBrain, this.battleArea,
                BattleAreaDescription.firstAmoebaPosition);

        this.bAmoeba = new Amoeba(this.bBrain, this.battleArea,
                BattleAreaDescription.secondAmoebaPosition);

        this.battleArea.ClearMovableObjects();

        this.battleAreaPanel.addGraphicalObject(new AmoebaView(this.aAmoeba));

        this.battleAreaPanel.addGraphicalObject(new AmoebaView(this.bAmoeba));

        this.battle = new BattleSimulation(this.battleArea);
        this.battle.InitBattle(this.aAmoeba, this.bAmoeba);

        SimulationEngine engine = new SimulationEngine(TIMER_DELAY,
                this.battle, this.battleAreaPanel);

        engine.start();

        synchronized (engine) {
            engine.wait();
        }
    }

    private Amoeba aAmoeba;

    private IBrain aBrain;
    private Amoeba bAmoeba;

    private BattleSimulation battle;
    private BattleArea battleArea;

    private BattleAreaPanel battleAreaPanel;

    private IBrain bBrain;

    private StatePanel statePanel;
}
