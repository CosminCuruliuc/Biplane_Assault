package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.BIG_ORC;

public class BigOrc extends Enemy {

    public BigOrc(float x, float y, int id, EnemyManager em) {
        super(x, y, id, BIG_ORC, em);
    }
}
