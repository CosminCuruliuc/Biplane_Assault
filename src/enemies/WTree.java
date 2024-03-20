package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.*;

public class WTree extends Enemy {

    public WTree(float x, float y, int id, EnemyManager em) {
        super(x, y, id, W_TREE, em);
    }
}

