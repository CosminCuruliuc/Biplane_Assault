package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.WIZARD;

public class Wizard extends Enemy {

    public Wizard(float x, float y, int id, EnemyManager em) {
        super(x, y, id, WIZARD, em);
    }
}

