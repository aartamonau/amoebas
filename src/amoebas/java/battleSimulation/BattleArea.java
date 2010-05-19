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

        this.newMovableObjects = new LinkedList<MovableObject>();
        this.newStaticObjects = new LinkedList<MapObject>();
        this.newAmoebas = new LinkedList<Amoeba>();

        this.movableObjects = new ArrayList<MovableObject>();
        this.staticObjects = new ArrayList<MapObject>();
        this.amoebas = new ArrayList<Amoeba>();

        this.thornShotListeners = new LinkedList<ThornShotListener>();

        this.size = size;
    }


    public void addAmoeba(Amoeba amoeba) {
        this.newAmoebas.add(amoeba);
        addMovableObject(amoeba);
    }


    public void addMovableObject(MovableObject obj) {
        this.newMovableObjects.add(obj);
    }


    public void addStaticObject(MapObject obj) {
        this.newStaticObjects.add(obj);
    }


    public void addThornShotListener(ThornShotListener listener) {
        this.thornShotListeners.add(listener);
    }


    public void ClearMovableObjects() {

        for (MovableObject obj : this.movableObjects) {
            obj.setHitPoints(0);
        }

        this.amoebas.clear();
        this.movableObjects.clear();
    }


    public Point denormalizeVector(Point2D.Double vector) {
        Point result = new Point((int) (vector.x * this.size.width),
                (int) (vector.y * this.size.height));

        return result;
    }


    public void flushNewItems() {

        for (MovableObject obj : this.newMovableObjects) {
            this.movableObjects.add(obj);
        }

        for (MapObject obj : this.newStaticObjects) {
            this.staticObjects.add(obj);
        }

        for (Amoeba amoeba : this.newAmoebas) {
            this.amoebas.add(amoeba);
        }

        this.newMovableObjects.clear();
        this.newStaticObjects.clear();
        this.newAmoebas.clear();
    }


    public List<Amoeba> getAmoebas() {
        return this.amoebas;
    }


    public Dimension getSize() {
        return this.size;
    }


    public List<MapObject> getStaticObjects() {
        return this.staticObjects;
    }


    public Rectangle2D.Double normalizeRectangle(Rectangle2D rect) {
        Rectangle2D.Double result = new Rectangle2D.Double(rect.getX() * 1.0
                / this.size.width, rect.getY() * 1.0 / this.size.height, rect
                .getWidth()
                * 1.0 / this.size.width, rect.getHeight() * 1.0
                / this.size.height);

        return result;
    }


    public Point2D.Double normalizeVector(Point vector) {
        Point2D.Double result = new Point2D.Double(vector.x * 1.0
                / this.size.width, vector.y * 1.0 / this.size.height);

        return result;
    }


    public void processCollisions() {

        int movableObjectsNum = this.movableObjects.size();
        int staticObjectsNum = this.staticObjects.size();

        for (int i = 0; i < movableObjectsNum; ++i) {

            for (int j = i + 1; j < movableObjectsNum; ++j) {

                if (this.movableObjects.get(i).intersectsWith(
                        this.movableObjects.get(j))) {

                    this.movableObjects.get(i).processCollision(
                            this.movableObjects.get(j));
                    this.movableObjects.get(j).processCollision(
                            this.movableObjects.get(i));
                }
            }

            for (int j = 0; j < staticObjectsNum; ++j) {

                if (this.movableObjects.get(i).intersectsWith(
                        this.staticObjects.get(j))) {

                    this.movableObjects.get(i).processCollision(
                            this.staticObjects.get(j));
                    this.staticObjects.get(j).processCollision(
                            this.movableObjects.get(i));
                }
            }

        }

    }


    public void removeThornShotListener(ThornShotListener listener) {
        this.thornShotListeners.remove(listener);
    }


    public void setMovableObjects(List<MovableObject> movableObjects) {
        this.movableObjects = movableObjects;
    }


    public void setSize(Dimension size) {
        this.size = size;
    }


    public void setStaticObjects(List<MapObject> staticObjects) {
        this.staticObjects = staticObjects;
    }


    public void thornShot(Thorn thorn) {

        this.newMovableObjects.add(thorn);

        for (ThornShotListener listener : this.thornShotListeners) {
            listener.thornShot(thorn);
        }
    }


    public void update() {

        Iterator<MovableObject> movableObjectsIter = this.movableObjects
                .iterator();

        while (movableObjectsIter.hasNext()) {

            MovableObject obj = movableObjectsIter.next();

            obj.update();

            if (!obj.isAlive()) {
                movableObjectsIter.remove();
            }
        }

        Iterator<MapObject> staticObjectsIter = this.staticObjects.iterator();

        while (staticObjectsIter.hasNext()) {

            MapObject obj = staticObjectsIter.next();

            obj.update();

            if (!obj.isAlive()) {
                staticObjectsIter.remove();
            }
        }

        flushNewItems();

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

    private List<Amoeba> amoebas;

    private List<MovableObject> movableObjects;
    private Queue<Amoeba> newAmoebas;
    private Queue<MovableObject> newMovableObjects;

    private Queue<MapObject> newStaticObjects;
    private Dimension size;
    private List<MapObject> staticObjects;

    private List<ThornShotListener> thornShotListeners;
}
