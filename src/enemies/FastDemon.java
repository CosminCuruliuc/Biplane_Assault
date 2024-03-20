package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.FAST_DEMON;

public class FastDemon extends Enemy {

    public FastDemon(float x, float y, int id, EnemyManager em) {
        super(x, y, id, FAST_DEMON, em);
    }
}

