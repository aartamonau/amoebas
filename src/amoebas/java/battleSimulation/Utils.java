package amoebas.java.battleSimulation;


import java.awt.Point;



public class Utils {

    public static double getRadiusVectorLength(Point vector) {
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
    }

}
