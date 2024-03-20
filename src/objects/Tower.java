package objects;

import Exceptions.InvalidTowerTypeException;
import helperMethods.Constants;

import static helperMethods.Constants.Towers.*;

// Clasa folosita pentru turete
public class Tower {

    private final int x;
    private final int y;
    private final int id;
    private final int tower_type;
    private int tick;
    private int damage;
    private float range, cooldown;
    private int tier;

    public Tower(int x, int y, int id, int tower_type) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.tower_type = tower_type;
        tier = 1;
        setDefaultDamage();
        setDefaultRange();
        setDefaultCooldown();
    }

    public void update() {
        tick++;
    }

    public void upgradeTower() throws InvalidTowerTypeException {
        this.tier++;
        switch (tower_type) {
            case G_TOWER -> {
                damage += 3;
                range += 10;
                cooldown -= 5;
            }
            case R_TOWER -> {
                damage += 4;
                range += 12;
                cooldown -= 6;
            }
            case LAUNCHER -> {
                damage += 15;
                range += 15;
                cooldown -= 15;
            }
            default -> throw new InvalidTowerTypeException("Invalid tower type: " + tower_type);
        }
    }
    public boolean isCoolDownOver() {
        return tick >= cooldown;
    }

    public void resetCooldown() {
        tick = 0;
    }

    private void setDefaultCooldown() {
        cooldown = Constants.Towers.GetDefaultCooldown(tower_type);
    }

    private void setDefaultRange() {
        range = Constants.Towers.GetDefaultRange(tower_type);
    }

    private void setDefaultDamage() {
        damage = Constants.Towers.GetStartDmg(tower_type);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getTower_type() {
        return tower_type;
    }

    public int getDamage() {
        return damage;
    }

    public float getRange() {
        return range;
    }

    public float getCooldown() {
        return cooldown;
    }

    public int getTier() {
        return tier;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }
}
