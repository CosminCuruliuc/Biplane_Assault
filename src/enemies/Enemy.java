package enemies;

import helperMethods.Constants;
import managers.EnemyManager;

import java.awt.*;
import static helperMethods.Constants.Direction.*;

// Clasa abstractă "Enemy" pentru a reprezenta un inamic în joc
public abstract class Enemy {

    private final EnemyManager enemyManager;
    // Variabile de instanță
    private float x, y;
    private final Rectangle bounds;
    protected int health;
    private final int id;
    private final int enemy_type;
    private int lastDir;
    private int maxHealth;
    private boolean alive = true;
    private final int slowTickLimit = 120;
    private int slowTick = slowTickLimit;

    // Constructor pentru clasa "Enemy"
    public Enemy(float x, float y, int id, int enemy_type, EnemyManager enemyManager) {
        this.x = x;
        this.y = y;
        bounds = new Rectangle((int) x, (int) y, 32, 32);
        this.id = id;
        this.enemy_type = enemy_type;
        this.enemyManager = enemyManager;
        lastDir = -1;
        setStartHealth();
    }

    private void setStartHealth() {
        health = Constants.Enemies.GetStartHealth(enemy_type);
        maxHealth = health;
    }

    // Metoda pentru a muta inamicul în funcție de viteza si directie
    public void move(float speed, int dir) {
        lastDir = dir;
        if (slowTick < 60 * 2) {
            slowTick++;
            speed *= 0.5f;
        }

        switch (dir) {
            case LEFT -> this.x -= speed;
            case UP -> this.y -= speed;
            case RIGHT -> this.x += speed;
            case DOWN -> this.y += speed;
            default -> {
            }
        }
        updateHitBox();
    }

    private void updateHitBox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    // Metoda pentru a scade viata inamicului
    public void hurt(int damage) {
        this.health -= damage;
        if (health <= 0) {
            alive = false;
            enemyManager.rewardPlayer(enemy_type);
        }
    }

    public void slow() {
        slowTick = 0;
    }

    public void kill() {
        alive = false;
        health = 0;
    }

    // Procentul din viata actuala a inamicului
    public float getHealthBar() {
        return health / (float) maxHealth;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public Rectangle getBounds() {
        return bounds;
    }

    public int getId() { return id; }

    public int getEnemy_type() {
        return enemy_type;
    }

    public int getLastDir() {
        return lastDir;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setHealth(int healthBar) {
        this.health = healthBar;
    }

    public int getHealth() {
        return health;
    }

    public void setLastDir(int lastDir) {
        this.lastDir = lastDir;
    }
}
