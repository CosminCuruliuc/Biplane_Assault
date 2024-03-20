package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.SMALL_GHOST;

public class SmallGhost extends Enemy {
    public SmallGhost(float x, float y, int id, EnemyManager em) {
        super(x, y, id, SMALL_GHOST, em);
    }
}
