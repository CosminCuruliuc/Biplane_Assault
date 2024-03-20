package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.ANGEL;

public class Angel extends Enemy {
    public Angel(float x, float y, int id, EnemyManager em) {
        super(x, y, id, ANGEL, em);
    }
}

