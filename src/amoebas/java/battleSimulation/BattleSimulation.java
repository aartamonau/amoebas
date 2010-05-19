package amoebas.java.battleSimulation;


public class BattleSimulation {

    public BattleSimulation(BattleArea battleArea) {
        this.battleArea = battleArea;
    }


    public Amoeba getWinner() {
        return this.competitor1.isAlive() ? this.competitor1 : this.competitor2;
    }


    public void InitBattle(Amoeba amoeba1, Amoeba amoeba2) {

        this.battleArea.ClearMovableObjects();
        this.tick = 0;
        this.competitor1 = amoeba1;
        this.competitor2 = amoeba2;
        this.battleArea.addAmoeba(amoeba1);
        this.battleArea.addAmoeba(amoeba2);
        this.battleArea.flushNewItems();
    }


    public boolean isOver() {
        return this.isOverTimed() || !this.competitor1.isAlive()
                || !this.competitor2.isAlive();
    }


    public boolean isOverTimed() {

        if (this.tick > this.TICK_LIMIT) {
            System.out.println("overtimed");
        }

        return this.tick > this.TICK_LIMIT;
    }


    public void reset() {
        this.battleArea.ClearMovableObjects();
    }


    public void update() {
        this.battleArea.update();
        this.battleArea.processCollisions();
        ++this.tick;
    }

    private BattleArea battleArea;

    private Amoeba competitor1;
    private Amoeba competitor2;
    private long tick;
    private final int TICK_LIMIT = 2000;
}
