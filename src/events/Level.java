package events;

import objects.PathPoint;
import java.util.ArrayList;

public class Level {
    private final String name;
    private final int[][] map;
    private final ArrayList<PathPoint> pathPoints;

    public Level(String name, int[][] map, ArrayList<PathPoint> pathPoints) {
        this.name = name;
        this.map = map;
        this.pathPoints = pathPoints;
    }

    public String getName() {
        return name;
    }

    public int[][] getMap() {
        return map;
    }

    public ArrayList<PathPoint> getPathPoints() {
        return pathPoints;
    }
}
