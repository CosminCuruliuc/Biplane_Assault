package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.BOSS_ORC;

public class BossOrc extends Enemy {
    public BossOrc(float x, float y, int id, EnemyManager em) {
        super(x, y, id, BOSS_ORC, em);
    }
}