package amoebas.java.battleSimulation;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;


public class Amoeba extends MovableObject {

  protected IBrain brain;
  private String name;
  private Random rand = new Random();
  private BattleArea battleArea;

  public static final int AMOEBA_MAX_SPEED = 3;
  public static final int AMOEBA_WIDTH = 100;
  public static final int AMOEBA_HEIGHT = 100;
  public static final int MAX_HP = 200;
  public static final int WEIGHT = 50;


  public Amoeba(IBrain brain, BattleArea battleArea, Point location) {

    super(battleArea, location, new Dimension(AMOEBA_WIDTH, AMOEBA_HEIGHT));

    this.brain = brain;
    this.battleArea = battleArea;
    this.hitPoints = MAX_HP;
    this.weight = WEIGHT;
    this.name = Integer.toString(new Random().nextInt());

    System.out.println("amoeba: " + name + " created. hp: " + hitPoints);
  }

  @Override
  public void update() {

    Point2D.Double velocityVectorNorm = this.brain.getMovementVector();
    this.velocityVector =
      new Point((int) Math.round(velocityVectorNorm.x * AMOEBA_MAX_SPEED),
                (int) Math.round(velocityVectorNorm.y * AMOEBA_MAX_SPEED));

    this.move(this.velocityVector);

    System.out.println(this.getLocation());

    if(brain.shallWeShoot()) {

      Point2D.Double aimVector = brain.getAimVector();

      Thorn thorn = new Thorn(velocityVector, getThornInitialLocation(aimVector));

      battleArea.thornShot(thorn);

      // temporary workaround
      thorn.update();
    }
  }


  private Point getThornInitialLocation(Point2D.Double aimVector) {

    double rectCenterX = boundaryRect.getCenterX();
    double rectCenterY = boundaryRect.getCenterY();

    double intersectionX;
    double intersectionY;

    assert aimVector.y != 0 || aimVector.x != 0 :
      "aimVector.y != 0 || aimVector.x != 0";


    double aimDirectionLineTangent;


    if (aimVector.x != 0 && aimVector.y != 0) {

      aimDirectionLineTangent = 1.0 *
        Math.abs(aimVector.y) / Math.abs(aimVector.x);

      // Checking intersection with rect's top line
      intersectionY = boundaryRect.getHeight() / 2;
      intersectionX = intersectionY / aimDirectionLineTangent;

      if (!boundaryRect.contains(intersectionX + rectCenterX - 1,
          intersectionY + rectCenterY - 1)) {

        // Checking intersection with rect's right line
        intersectionX = boundaryRect.getWidth() /2;
        intersectionY = intersectionX * aimDirectionLineTangent;
      }

    } else if ( aimVector.y == 0 ) {

      intersectionY = 0;
      intersectionX = boundaryRect.getWidth() / 2;

    } else    {

      intersectionX = 0;
      intersectionY = boundaryRect.getHeight() / 2;

    }


    assert boundaryRect.contains(intersectionX + rectCenterX - 1,
        intersectionY + rectCenterY - 1) : "omfg ->" + aimVector +
        "\nx = " + (intersectionX + rectCenterX) +
        "\ny = " + (intersectionY + rectCenterY) +
        "\nrect : " + boundaryRect;


    Point result = new Point();

    result.x = (int) Math.ceil(intersectionX);
    result.y = (int) Math.ceil(intersectionY);

    if ( aimVector.x < 0 ) {
      result.x *= -1;
    }

    if ( aimVector.y < 0 ) {
      result.y *= -1;
    }

    result.x += rectCenterX;
    result.y += rectCenterY;

    return result;
  }


  @Override
  public void processCollision(MapObject other) {

    velocityVector.x *= -1;
    velocityVector.y *= -1;

    computeCollisionDamage(other);
  }


  @Override
  public void computeCollisionDamage(MapObject other) {

    int dmg = (int) ((int)other.weight * rand.nextDouble() * 0.1);
    hitPoints -= dmg;

    System.out.println("Amoeba: " + name + " dmg: " + dmg + " rem: " + hitPoints);
  }


  public String getName() {
    return name;
  }
}
