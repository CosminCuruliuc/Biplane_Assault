package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.B_MONSTER;

public class BMonster extends Enemy {
    public BMonster(float x, float y, int id, EnemyManager em) {
        super(x, y, id, B_MONSTER, em);
    }
}
