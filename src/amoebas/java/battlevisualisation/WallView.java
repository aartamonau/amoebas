package amoebas.java.battlevisualisation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import amoebas.java.battleSimulation.Wall;

public class WallView extends GraphicalObject {

    public WallView(Wall wall) {
        super(wall.getLocation());
        this.wall = wall;
    }

    @Override
    public void draw(Graphics2D graphicsContext, double xScale, double yScale) {

        graphicsContext.setColor(Color.BLUE);

        Rectangle2D rect = (Rectangle2D) wall.getBoundaryRect().clone();

        rect.setRect(rect.getX() * xScale, rect.getY() * yScale, rect
                .getWidth()
                * xScale, rect.getHeight() * yScale);

        graphicsContext.fill(rect);
    }

    @Override
    public boolean isValid() {
        return wall.isAlive();
    }

    protected Wall wall;
}
