package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.BIG_GHOST;

public class BigGhost extends Enemy {
    public BigGhost(float x, float y, int id, EnemyManager em) {
        super(x, y, id, BIG_GHOST, em);
    }
}
