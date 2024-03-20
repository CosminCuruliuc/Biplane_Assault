package managers;

import enemies.*;
import helperMethods.LoadSave;
import objects.PathPoint;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static helperMethods.Constants.Direction.*;
import static helperMethods.Constants.Enemies.*;
import static helperMethods.Constants.Tiles.*;

// Clasa EnemyManager gestionează inamicii din joc, inclusiv încărcarea imaginilor și actualizarea poziției inamicilor.
public class EnemyManager {

    private final Playing playing;
    private final BufferedImage[] enemyImgs;
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final PathPoint start;
    private final PathPoint end;
    private int enemy_amount = 0;

    // Cosntructorul
    public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        enemyImgs = new BufferedImage[15];
        this.start = start;
        this.end = end;
        loadEnemyImgs();
    }

    // Încarcă imaginile pentru inamici din sprite atlas
    private void loadEnemyImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas_2();
        for(int i = 0; i < 3; i++) {
            enemyImgs[i] = atlas.getSubimage(0,(i + 1) * 32, 32, 32);
        }
        enemyImgs[3] = atlas.getSubimage(0, 5 * 32, 32, 32);
        enemyImgs[4] = atlas.getSubimage(0,7 * 32, 32, 32);
        enemyImgs[5] = atlas.getSubimage(4 * 32, 7 * 32,32,32);
        enemyImgs[6] = atlas.getSubimage(0,9 * 32, 32, 32);
        enemyImgs[7] = atlas.getSubimage(4 * 32, 9 * 32, 32, 32);
        for(int i = 8; i < 15; i++) {
            enemyImgs[i] = atlas.getSubimage(0,(2 * i - 5) * 32,32,32);
        }
    }

    // Actualizează poziția inamicilor pe ecran
    public void update() {
        for(Enemy e : enemies) {
            if(e.isAlive()) {
                updateEnemyMove(e);
            }
        }
    }

    // Metoda pentru a verifica daca urmatorul tile e drum sau nu pentru inamic
    private void updateEnemyMove(Enemy e) {

        if(e.getLastDir() == -1) {
            setNewDirectionAndMove(e);
        }

        int newX = (int)(e.getX() + getSpeedAndWidth(e.getLastDir(), e.getEnemy_type()));
        int newY = (int)(e.getY() + getSpeedAndHeight(e.getLastDir(), e.getEnemy_type()));

        if(getTileType(newX, newY) == ROAD_TILE) {
            e.move(GetSpeed(e.getEnemy_type()), e.getLastDir());
        } else if(isAtEnd(e)) {
            e.kill();
            playing.removeOneLife();
        } else {
            setNewDirectionAndMove(e);
        }
    }

    // Metoda care stabileste o noua directie daca a ajuns la o "intersectie"
    private void setNewDirectionAndMove(Enemy e) {

        int dir = e.getLastDir();
        int xCord = (int)(e.getX() / 32);
        int yCord = (int)(e.getY() / 32);

        fixEnemyOffsetTile(e, dir, xCord, yCord);
        if(isAtEnd(e)) {
            return;
        }

        if(dir == LEFT || dir == RIGHT) {
            int newY = (int)(e.getY() + getSpeedAndHeight(UP, e.getEnemy_type()));
            if(getTileType((int)e.getX(), newY) == ROAD_TILE) {
                e.move(GetSpeed(e.getEnemy_type()), UP);
            } else {
                e.move(GetSpeed(e.getEnemy_type()), DOWN);
            }
        } else {
            int newX = (int)(e.getX() + getSpeedAndWidth(RIGHT, e.getEnemy_type()));
            if(getTileType(newX, (int)e.getY()) == ROAD_TILE) {
                e.move(GetSpeed(e.getEnemy_type()), RIGHT);
            } else {
                e.move(GetSpeed(e.getEnemy_type()), LEFT);
            }
        }
    }

    // Trebuie sa ne asiguram ca monstrul se afla 100% in
    // caseta cand face schimbarea de directie
    // (altfel, o face inainte si se buguieste)
    private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
        switch (dir) {
            case RIGHT -> {
                if (xCord < 19) {
                    xCord++;
                }
            }
            case DOWN -> {
                if (yCord < 19) {
                    yCord++;
                }
            }
            default -> {
            }
        }

        e.setPos(xCord * 32, yCord * 32);
    }

    private boolean isAtEnd(Enemy e) {
        return e.getX() == end.getX() * 32 && e.getY() == end.getY() * 32;
    }

    // Metoda pentru a verifica tipul dalei in functie de x si y
    private int getTileType(int newX, int newY) {
        return playing.getTileType(newX, newY);
    }

    // Determina viteza pe axa Y functie de directia pe care se misca
    private float getSpeedAndHeight(int dir, int enemy_type) {
        if(dir == UP) {
            return -GetSpeed(enemy_type);
        } else if(dir == DOWN) {
            return GetSpeed(enemy_type) + 32;
        }
        return 0;
    }

    // Determina viteza pe axa X functie de directia pe care se misca
    private float getSpeedAndWidth(int dir, int enemy_type) {
        if(dir == LEFT) {
            return -GetSpeed(enemy_type);
        } else if(dir == RIGHT) {
            return GetSpeed(enemy_type) + 32;
        }
        return 0;
    }

    // Adaugă un inamic în ArrayList-ul de inamici
    public void addEnemy(int enemyType) {

        int x = start.getX() * 32;
        int y = start.getY() * 32;
        enemies.add(EnemyFactory.createEnemy(enemyType, x, y, enemy_amount++, this));
    }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }

    // Desenează inamicii pe ecran
    public void draw(Graphics g) {
        for(Enemy e : enemies) {
            if(e.isAlive()) {
                drawEnemy(e, g);
                drawHealthBar(e, g);
            }
        }
    }

    // Deseneaza bara deasupra inamicului care indica viata
    private void drawHealthBar(Enemy e, Graphics g) {
        int x = (int) e.getX() / 32; // Convertim coordonatele pixel la coordonatele tile
        int y = (int) e.getY() / 32;

        if (playing.getFogOfWar().isVisible(x, y)) {
            g.setColor(Color.red);
            int HPBarHeight = 3;
            g.fillRect((int) e.getX() + 16 - (getNewBarWidth(e) / 2), (int) e.getY(), getNewBarWidth(e), HPBarHeight);
        }
    }

    // Procentul din viata ramas
    private int getNewBarWidth(Enemy e) {
        int HPBarWidth = 20;
        return (int) (HPBarWidth * e.getHealthBar());
    }

    // Desenează un inamic specific pe ecran
    private void drawEnemy(Enemy e, Graphics g) {
        int x = (int) e.getX() / 32; // Convertim coordonatele pixel la coordonatele tile
        int y = (int) e.getY() / 32;

        if (playing.getFogOfWar().isVisible(x, y)) {
            g.drawImage(enemyImgs[e.getEnemy_type()], (int) e.getX(), (int) e.getY(), null);
        }
    }

    public int getAmountOfAliveEnemies() {
        int size = 0;
        for(Enemy e : enemies) {
            if(e.isAlive()) {
                size++;
            }
        }
        return size;
    }

    public void rewardPlayer(int enemyType) {
        playing.rewardPlayer(enemyType);
    }

    public void reset() {
        enemies.clear();
        enemy_amount = 0;
    }

    public void setEnemies(List<Enemy> loadedEnemies) {
        this.enemies.clear();
        this.enemies.addAll(loadedEnemies);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}