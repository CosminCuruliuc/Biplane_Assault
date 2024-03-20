package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.SMALL_ORC;

public class SmallOrc extends Enemy {

    public SmallOrc(float x, float y, int id, EnemyManager em) {
        super(x, y, id, SMALL_ORC, em);
    }
}
