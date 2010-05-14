package amoebas.java.interop;

import java.awt.Dimension;
import java.awt.Point;

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
}