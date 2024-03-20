package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.BOSS_DEMON;

public class BossDemon extends Enemy {
    public BossDemon(float x, float y, int id, EnemyManager em) {
        super(x, y, id, BOSS_DEMON, em);
    }
}
