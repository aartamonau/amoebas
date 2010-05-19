/**
 *
 */
package amoebas.java.battlevisualisation;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.net.URL;

import amoebas.java.battleSimulation.Amoeba;



/**
 * @author m
 * 
 */
public class AmoebaView extends GraphicalObject {

    public AmoebaView(Amoeba amoeba) {
        super(amoeba.getLocation());
        this.amoebaModel = amoeba;

        URL imgURL = getClass().getResource("amoeba.jpg");
        this.img = Toolkit.getDefaultToolkit().getImage(imgURL);
    }


    @Override
    public void draw(Graphics2D graphicsContext, double xScale, double yScale) {

        Point loc = this.amoebaModel.getLocation();

        graphicsContext.drawImage(this.img, (int) (loc.x * xScale),
                (int) (loc.y * yScale), (int) (Amoeba.AMOEBA_WIDTH * xScale),
                (int) (Amoeba.AMOEBA_HEIGHT * yScale), null);

        // Displaying the direction
        Point velocityVector = this.amoebaModel.getVelocityVector();

        int squareCenterX = (int) this.amoebaModel.getBoundaryRect()
                .getCenterX();
        int squareCenterY = (int) this.amoebaModel.getBoundaryRect()
                .getCenterY();

        int endX = squareCenterX + velocityVector.x * 50;
        int endY = squareCenterY + velocityVector.y * 50;

        graphicsContext.setColor(Color.blue);

        graphicsContext.drawLine((int) (squareCenterX * xScale),
                (int) (squareCenterY * yScale), (int) (endX * xScale),
                (int) (endY * yScale));

        // Displaying the aim vector
        Point aimVector = this.amoebaModel.lastAimVector;

        graphicsContext.setColor(Color.green);

        graphicsContext.drawLine((int) (squareCenterX * xScale),
                (int) (squareCenterY * yScale),
                (int) ((aimVector.x * 3 + squareCenterX) * xScale),
                (int) ((aimVector.y * 3 + squareCenterY) * yScale));

        // Displaying the nearest enemy vector
        Point nearestEnemyVector = this.amoebaModel.getNearestEnemyVector();

        graphicsContext.setColor(Color.CYAN);

        graphicsContext.drawLine((int) (squareCenterX * xScale),
                (int) (squareCenterY * yScale),
                (int) ((nearestEnemyVector.x + squareCenterX) * xScale),
                (int) ((nearestEnemyVector.y + squareCenterY) * yScale));

        // Displaying the nearest enemy's thorn vector
        Point nearsetThornVector = this.amoebaModel.getNearestThornVector();

        if (nearsetThornVector != null) {

            graphicsContext.setColor(Color.black);

            graphicsContext.drawLine((int) (loc.x * xScale),
                    (int) (loc.y * yScale),
                    (int) ((nearsetThornVector.x + loc.x) * xScale),
                    (int) ((nearsetThornVector.y + loc.y) * yScale));

        }

        // Displaying a HP Bar
        graphicsContext.setColor(Color.GRAY);

        Rectangle2D rect = this.amoebaModel.getBoundaryRect();

        int squareX = this.amoebaModel.getLocation().x;
        int squareY = this.amoebaModel.getLocation().y;

        int height = (int) Math.round(0.1 * rect.getHeight());
        int width = (int) rect.getWidth();

        graphicsContext.fillRect((int) (squareX * xScale),
                (int) (squareY * yScale), (int) (width * xScale),
                (int) (height * yScale));

        graphicsContext.setColor(Color.RED);

        width *= 1.0 * this.amoebaModel.getHitPoints() / Amoeba.MAX_HP;

        graphicsContext.fillRect((int) (squareX * xScale),
                (int) (squareY * yScale), (int) (width * xScale),
                (int) (height * yScale));

    }


    @Override
    public boolean isValid() {
        return this.amoebaModel.isAlive();
    }

    private Amoeba amoebaModel;
    private Image img;
}
