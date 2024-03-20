package objects;

import java.awt.geom.Point2D;

public class Projectile {

    private Point2D.Float position;
    private final int id;
    private final int projectileType;
    private int damage;
    private boolean active = true;
    private float xSpeed;
    private float ySpeed;
    private float rotation;

    public Projectile(float x, float y, float xSpeed, float ySpeed, int damage, float rotation, int id, int projectileType) {
        position = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.id = id;
        this.damage = damage;
        this.rotation = rotation;
        this.projectileType = projectileType;
    }

    public void reuse(int x, int y, float xSpeed, float ySpeed, int damage, float rotate) {
        position = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.damage = damage;
        this.rotation = rotate;
        active = true;
    }

    public void move() {
        position.x += xSpeed;
        position.y += ySpeed;
    }

    public Point2D.Float getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public int getDamage() {
        return damage;
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
