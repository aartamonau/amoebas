package amoebas.java.battleSimulation;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BattleArea {

    public BattleArea(Dimension size) {

        newMovableObjects = new LinkedList<MovableObject>();
        newStaticObjects = new LinkedList<MapObject>();

        movableObjects = new ArrayList<MovableObject>();
        staticObjects = new ArrayList<MapObject>();

        thornShotListeners = new LinkedList<ThornShotListener>();

        this.size = size;
    }

    public void processCollisions() {

        int movableObjectsNum = movableObjects.size();
        int staticObjectsNum = staticObjects.size();

        for (int i = 0; i < movableObjectsNum; ++i) {

            for (int j = i + 1; j < movableObjectsNum; ++j) {

                if (movableObjects.get(i).intersectsWith(movableObjects.get(j))) {

                    movableObjects.get(i).processCollision(
                            movableObjects.get(j));
                    movableObjects.get(j).processCollision(
                            movableObjects.get(i));
                }
            }

            for (int j = 0; j < staticObjectsNum; ++j) {

                if (movableObjects.get(i).intersectsWith(staticObjects.get(j))) {

                    movableObjects.get(i)
                            .processCollision(staticObjects.get(j));
                    staticObjects.get(j)
                            .processCollision(movableObjects.get(i));
                }
            }

        }

    }

    public void update() {

        Iterator<MovableObject> movableObjectsIter = movableObjects.iterator();

        while (movableObjectsIter.hasNext()) {

            MovableObject obj = movableObjectsIter.next();

            obj.update();

            if (!obj.isAlive()) {
                movableObjectsIter.remove();
            }
        }

        Iterator<MapObject> staticObjectsIter = staticObjects.iterator();

        while (staticObjectsIter.hasNext()) {

            MapObject obj = staticObjectsIter.next();

            obj.update();

            if (!obj.isAlive()) {
                staticObjectsIter.remove();
            }
        }

        flushNewItems();

    }

    public void flushNewItems() {

        for (MovableObject obj : newMovableObjects) {
            movableObjects.add(obj);
        }

        for (MapObject obj : newStaticObjects) {
            staticObjects.add(obj);
        }

        newMovableObjects.clear();
        newStaticObjects.clear();
    }

    public void ClearMovableObjects() {

        for (MovableObject obj : movableObjects) {
            obj.setHitPoints(0);
        }

        movableObjects.clear();
    }

    public void thornShot(Thorn thorn) {

        newMovableObjects.add(thorn);

        for (ThornShotListener listener : thornShotListeners) {
            listener.thornShot(thorn);
        }
    }

    public void addThornShotListener(ThornShotListener listener) {
        thornShotListeners.add(listener);
    }

    public void removeThornShotListener(ThornShotListener listener) {
        thornShotListeners.remove(listener);
    }

    public void addMovableObject(MovableObject obj) {
        newMovableObjects.add(obj);
    }

    public void addStaticObject(MapObject obj) {
        newStaticObjects.add(obj);
    }

    public void setMovableObjects(List<MovableObject> movableObjects) {
        this.movableObjects = movableObjects;
    }

    public void setStaticObjects(List<MapObject> staticObjects) {
        this.staticObjects = staticObjects;
    }

    public List<MapObject> getStaticObjects() {
        return staticObjects;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public Point2D.Double normalizeVector(Point vector) {
        Point2D.Double result = new Point2D.Double(vector.x * 1.0
                / this.size.width, vector.y * 1.0 / this.size.height);

        return result;
    }

    public Point denormalizeVector(Point2D.Double vector) {
        Point result = new Point((int) (vector.x * this.size.width),
                (int) (vector.y * this.size.height));

        return result;
    }

    public Rectangle2D.Double normalizeRectangle(Rectangle2D rect) {
        Rectangle2D.Double result = new Rectangle2D.Double(rect.getX() * 1.0
                / this.size.width, rect.getY() * 1.0 / this.size.height, rect
                .getWidth()
                * 1.0 / this.size.width, rect.getHeight() * 1.0
                / this.size.height);

        return result;
    }

    public Rectangle2D.Double[] wallPositions() {
        Rectangle2D.Double[] result = new Rectangle2D.Double[this.staticObjects
                .size()];

        for (int i = 0; i < this.staticObjects.size(); i++) {
            result[i] = this.normalizeRectangle(this.staticObjects.get(i)
                    .getBoundaryRect());
        }

        return result;
    }

    private List<ThornShotListener> thornShotListeners;

    private Queue<MovableObject> newMovableObjects;
    private Queue<MapObject> newStaticObjects;

    private List<MovableObject> movableObjects;
    private List<MapObject> staticObjects;

    private Dimension size;
}
