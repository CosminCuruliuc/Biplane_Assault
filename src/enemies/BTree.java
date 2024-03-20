package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.B_TREE;

public class BTree extends Enemy {

    public BTree(float x, float y, int id, EnemyManager em) {
        super(x, y, id, B_TREE, em);
    }
}