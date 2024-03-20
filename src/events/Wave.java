package events;

import java.util.ArrayList;

public class Wave {

    private final ArrayList<Integer> enemies;

    public Wave(ArrayList<Integer> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Integer> getEnemies() {
        return enemies;
    }
}
