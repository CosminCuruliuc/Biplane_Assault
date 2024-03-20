package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.*;

public class GMonster extends Enemy {
    public GMonster(float x, float y, int id, EnemyManager em) {
        super(x, y, id, G_MONSTER, em);
    }
}
