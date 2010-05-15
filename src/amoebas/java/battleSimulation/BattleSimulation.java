package amoebas.java.battleSimulation;



public class BattleSimulation {
  private final int TICK_LIMIT = 500;

  public BattleSimulation(BattleArea battleArea)  {
    this.battleArea = battleArea;
  }


  public void InitBattle(Amoeba amoeba1, Amoeba amoeba2) {

    battleArea.ClearMovableObjects();
    tick = 0;
    competitor1 = amoeba1;
    competitor2 = amoeba2;
    battleArea.addMovableObject(amoeba1);
    battleArea.addMovableObject(amoeba2);
  }

  public void update() {
    battleArea.update();
    battleArea.processCollisions();
    ++tick;
  }


  public boolean isOver() {
    return !competitor1.isAlive() || !competitor2.isAlive();
  }


  public Amoeba getWinner() {
    return competitor1.isAlive()? competitor1 : competitor2;
  }


  public void reset() {
    battleArea.ClearMovableObjects();
  }

  public boolean isOverTimed() {
    if (this.tick > TICK_LIMIT) {
      System.out.println("over timed");

      return true;
    } else {
      return false;
    }
  }


  private Amoeba competitor1;
  private Amoeba competitor2;
  private long tick;
  private BattleArea battleArea;
}
