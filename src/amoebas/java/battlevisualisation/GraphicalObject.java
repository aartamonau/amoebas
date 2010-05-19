/**
 * 
 */
package amoebas.java.battlevisualisation;


import java.awt.Graphics2D;
import java.awt.Point;



/**
 * @author m
 * 
 */
public abstract class GraphicalObject {

    public GraphicalObject(Point location) {
        this.location = location;
    }


    public abstract void draw(Graphics2D graphicsContext, double xScale,
            double yScale);


    public int getX() {
        return this.location.x;
    }


    public int getY() {
        return this.location.y;
    }


    public abstract boolean isValid();


    public void setX(int newX) {
        this.location.x = newX;
    }


    public void setY(int newY) {
        this.location.y = newY;
    }

    protected Point location;
}
