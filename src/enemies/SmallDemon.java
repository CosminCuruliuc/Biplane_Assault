package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.SMALL_DEMON;

public class SmallDemon extends Enemy {

    public SmallDemon(float x, float y, int id, EnemyManager em) {
        super(x, y, id, SMALL_DEMON, em);
    }
}
