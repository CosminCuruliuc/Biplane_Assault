package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.MASK_ORC;

public class MaskOrc extends Enemy {

    public MaskOrc(float x, float y, int id, EnemyManager em) {
        super(x, y, id, MASK_ORC, em);
    }
}
