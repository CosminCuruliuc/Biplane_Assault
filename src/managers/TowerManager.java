package managers;

import Exceptions.*;
import enemies.Enemy;
import helperMethods.LoadSave;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TowerManager {

    private final Playing playing;
    private BufferedImage[] towerImages;
    private final ArrayList<Tower> towers = new ArrayList<>();
    private int towerAmount = 0;

    public TowerManager (Playing playing) {
        this.playing = playing;
        loadTowerImages();
    }

    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImages = new BufferedImage[3];
        for(int i = 0; i < 2; i++) {
            towerImages[i] = atlas.getSubimage( (19 + i) * 32, 10 * 32, 32, 32);
        }
        towerImages[2] = atlas.getSubimage(20 * 32, 8 * 32, 32, 32);
    }

    public void addTower(Tower selectedTower, int xPos, int yPos) throws TowerPlacementException {
        for(Tower t : towers)
            if(t.getX() == xPos && t.getY() == yPos)
                throw new TowerPlacementException("A tower already exists at this position.");
        towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTower_type()));
    }

    public void removeTower(Tower displayedTower) {
        for(int i = 0; i < towers.size(); i++) {
            if(towers.get(i).getId() == displayedTower.getId()) {
                towers.remove(i);
            }
        }
        towers.removeIf((t) -> {
            return t.getId() == displayedTower.getId();
        });
    }

    public void upgradeTower(Tower displayedTower) throws InvalidTowerTypeException, InvalidTowerException {
        if(displayedTower == null) {
            throw new InvalidTowerException("Tower does not exist!");
        }
        for(Tower t : towers) {
            if(t.getId() == displayedTower.getId()) {
                t.upgradeTower();
            }
        }
    }

    public void draw(Graphics g) {
        for(Tower t : towers) {
            int x = t.getX() / 32;
            int y = t.getY() / 32;

            if (playing.getFogOfWar().isVisible(x, y)) {
                g.drawImage(towerImages[t.getTower_type()], t.getX(), t.getY(), null);
            }
        }
    }

    public void update() {
        for(Tower t : towers) {
            t.update();
            try {
                attackEnemy(t);
            } catch (InvalidEnemyException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void attackEnemy(Tower t) throws InvalidEnemyException{
        for(Enemy e : playing.getEnemyManager().getEnemies()) {
            if(e == null) {
                throw new InvalidEnemyException("Enemy does not exist!");
            }
            if(e.isAlive()) {
                if(isEnemyInRange(t,e)) {
                    if(t.isCoolDownOver()) {
                        playing.shootEnemy(t, e);
                        t.resetCooldown();
                    }
                }
            }
        }
    }

    private boolean isEnemyInRange(Tower t, Enemy e) {
        int range = helperMethods.Utilz.GetDistance(t.getX(), t.getY(), e.getX(), e.getY());
        return range < t.getRange();
    }

    public Tower getTowerAt(int x, int y) {
        for(Tower t : towers) {
            if(t.getX() == x && t.getY() == y) {
                return t;
            }
        }
        return null;
    }

    public void reset() {
        towers.clear();
        towerAmount = 0;
    }

    public void setTowers(List<Tower> loadedTowers) {
        this.towers.clear();
        this.towers.addAll(loadedTowers);
    }

    public BufferedImage[] getTowerImages() {
        return towerImages;
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }
}
