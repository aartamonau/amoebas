package amoebas.java.battleSimulation;

public class BattleSimulation {

    private final int TICK_LIMIT = 2000;

    public BattleSimulation(BattleArea battleArea) {
        this.battleArea = battleArea;
    }

    public void InitBattle(Amoeba amoeba1, Amoeba amoeba2) {

        battleArea.ClearMovableObjects();
        tick = 0;
        competitor1 = amoeba1;
        competitor2 = amoeba2;
        battleArea.addAmoeba(amoeba1);
        battleArea.addAmoeba(amoeba2);
        battleArea.flushNewItems();
    }

    public void update() {
        battleArea.update();
        battleArea.processCollisions();
        ++tick;
    }

    public boolean isOver() {
        return this.isOverTimed() || !competitor1.isAlive()
                || !competitor2.isAlive();
    }

    public Amoeba getWinner() {
        return competitor1.isAlive() ? competitor1 : competitor2;
    }

    public void reset() {
        battleArea.ClearMovableObjects();
    }

    public boolean isOverTimed() {
        return this.tick > TICK_LIMIT;
    }

    private Amoeba competitor1;
    private Amoeba competitor2;
    private long tick;
    private BattleArea battleArea;
}
