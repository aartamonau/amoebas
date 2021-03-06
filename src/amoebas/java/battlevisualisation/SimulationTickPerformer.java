package amoebas.java.battlevisualisation;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import amoebas.java.battleSimulation.BattleSimulation;



public class SimulationTickPerformer implements ActionListener {

    public SimulationTickPerformer(BattleSimulation battle,
            BattleAreaPanel battleArea, SimulationEngine engine) {

        this.battle = battle;
        this.battleArea = battleArea;
        this.engine = engine;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        this.battle.update();
        this.battleArea.repaint();

        if (this.battle.isOver()) {
            this.engine.BattleFinished();
        }

    }

    protected BattleSimulation battle;
    protected JPanel battleArea;
    protected SimulationEngine engine;
}
