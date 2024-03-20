package managers;

import enemies.Enemy;
import helperMethods.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Constants.Towers.*;
import static helperMethods.Constants.Projectiles.*;

public class ProjectileManager {

    private final Playing playing;
    private final ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] proj_images;
    private int projectile_id = 0;

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImages();
    }

    private void importImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        proj_images = new BufferedImage[3];
        for(int i = 0; i < 2; i++) {
            proj_images[i] = atlas.getSubimage((21 + i) * 32,11 * 32, 32, 32);
        }
        proj_images[2] = atlas.getSubimage(21 * 32, 10 * 32, 32, 32);
    }

    public void newProjectile(Tower t, Enemy e) {
        int type = getProjType(t);

        int xDist = (int) (t.getX() - e.getX());
        int yDist = (int) (t.getY() - e.getY());
        int total_distance = Math.abs(xDist) + Math.abs(yDist);

        float xPer = (float) Math.abs(xDist) / total_distance;
        float yPer = 1.0f - xPer;
        float xSpeed = xPer * GetSpeed(type);
        float ySpeed = yPer * GetSpeed(type);

        if(t.getX() > e.getX()) {
            xSpeed *= -1;
        }
        if(t.getY() > e.getY()) {
            ySpeed *= -1;
        }

        float arc = (float) Math.atan(yDist / (float) xDist);
        float rotate = (float) Math.toDegrees(arc);

        if(xDist < 0) {
            rotate += 180;
        }

        for(Projectile p : projectiles) {
            if(!p.isActive()) {
                if(p.getProjectileType() == type) {
                    p.reuse(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDamage(), rotate);
                    return;
                }
            }
        }

        projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDamage(), rotate, projectile_id++, type));
    }

    public void update() {
        for(Projectile p : projectiles) {
            if(p.isActive()) {
                p.move();
                if(isProjectileHittingEnemy(p)) {
                    p.setActive(false);
                } else if(isProjectileOutsideBounds(p) || isProjectileHittingRock(p)) {
                    p.setActive(false);
                }
            }
        }
    }

    private boolean isProjectileHittingRock(Projectile p) {
        return playing.isTileRock((int) p.getPosition().x , (int) p.getPosition().y);
    }


    private boolean isProjectileOutsideBounds(Projectile p) {
        if(p.getPosition().x >= 0 && p.getPosition().x <= 640) {
            return !(p.getPosition().y >= 0) || !(p.getPosition().y <= 800);
        }
        return true;
    }

    private boolean isProjectileHittingEnemy(Projectile p) {
        for(Enemy e : playing.getEnemyManager().getEnemies()) {
            if(e.getBounds().contains(p.getPosition()) && e.isAlive()) {
                e.hurt(p.getDamage());
                if(p.getProjectileType() == SPECIAL_BULLET) {
                    e.slow();
                }
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        Graphics2D graphics2D  = (Graphics2D) g;
        for(Projectile p : projectiles) {
            if(p.isActive()) {
                graphics2D.translate(p.getPosition().x, p.getPosition().y);
                graphics2D.rotate(Math.toRadians(p.getRotation() - 90));
                graphics2D.drawImage(proj_images[p.getProjectileType()], -16, -16, null);
                graphics2D.rotate(-Math.toRadians(p.getRotation() - 90));
                graphics2D.translate(-p.getPosition().x, -p.getPosition().y);
            }
        }
    }

    private int getProjType(Tower t) {
        return switch (t.getTower_type()) {
            case G_TOWER -> BULLET;
            case R_TOWER -> SPECIAL_BULLET;
            case LAUNCHER -> ROCKET;
            default -> 0;
        };
    }

    public void reset() {
        projectiles.clear();
        projectile_id = 0;
    }
}
