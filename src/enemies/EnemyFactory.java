package enemies;

import managers.EnemyManager;

import static helperMethods.Constants.Enemies.*;

public class EnemyFactory {

    public static Enemy createEnemy(int enemyType, float x, float y, int id, EnemyManager em) {
        return switch (enemyType) {
            case SMALL_GHOST -> new SmallGhost(x, y, id, em);
            case SMALL_ORC -> new SmallOrc(x, y, id, em);
            case SMALL_DEMON -> new SmallDemon(x, y, id, em);
            case BIG_GHOST -> new BigGhost(x, y, id, em);
            case B_MONSTER -> new BMonster(x, y, id, em);
            case G_MONSTER -> new GMonster(x, y, id, em);
            case W_TREE -> new WTree(x, y, id, em);
            case B_TREE -> new BTree(x, y, id, em);
            case MASK_ORC -> new MaskOrc(x, y, id, em);
            case BIG_ORC -> new BigOrc(x, y, id, em);
            case BOSS_ORC -> new BossOrc(x, y, id, em);
            case WIZARD -> new Wizard(x, y, id, em);
            case FAST_DEMON -> new FastDemon(x, y, id, em);
            case BOSS_DEMON -> new BossDemon(x, y, id, em);
            case ANGEL -> new Angel(x, y, id, em);
            default -> throw new IllegalArgumentException("Invalid enemy type: " + enemyType);
        };
    }
}