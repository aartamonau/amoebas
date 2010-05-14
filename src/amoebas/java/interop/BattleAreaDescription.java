package amoebas.java.interop;

import java.awt.Dimension;
import java.awt.Point;

import amoebas.java.battleSimulation.Wall;
import amoebas.java.battleSimulation.IBrain;

class BattleAreaDescription {
  public final static Dimension size    = new Dimension(1024, 700);

  public final static int wallThickness = 10;

  public final static int wallsCount = 4;
  public final static
  Point[] wallPositions = { new Point(0, 0),
                            new Point(0, size.height - wallThickness),
                            new Point(size.width - wallThickness, 0),
                            new Point(0, 0) };
  public final static
  Dimension[] wallDimensions = { new Dimension(size.width, wallThickness),
                                 new Dimension(size.width, wallThickness),
                                 new Dimension(wallThickness, size.height),
                                 new Dimension(wallThickness, size.height) };

  public final Point firstAmoebaPosition = new Point(20, 20);
  public final Point secondAmoebaPosition = new Point(600, 200);

  public final static
  Wall[] createWalls() {
    Wall[] result = new Wall[wallsCount];

    for (int i = 0; i < BattleAreaDescription.wallsCount; i++) {
      Wall wall = new Wall(BattleAreaDescription.wallPositions[i],
                           BattleAreaDescription.wallDimensions[i]);
      result[i] = wall;
    }

    return result;
  }
}