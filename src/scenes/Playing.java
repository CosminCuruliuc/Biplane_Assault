package scenes;

import Exceptions.*;
import UI.ActionBar;
import enemies.Enemy;
import events.Level;
import helperMethods.Constants;
import helperMethods.DatabaseHandler;
import main.Game;
import managers.*;
import objects.FogOfWar;
import objects.PathPoint;
import objects.Tower;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static helperMethods.Constants.Tiles.*;

// Clasa Playing gestioneaza scena de joc propriu-zis
public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private final ActionBar actionBar;
    private final FogOfWar fogofWar;
    private int mouseX, mouseY;
    private final EnemyManager enemyManager;
    private final TowerManager towerManager;
    private final ProjectileManager projectileManager;
    private final WaveManager waveManager;
    private final LevelManager levelManager;
    private PathPoint start, end;
    private Tower selectedTower;
    private int goldTick;
    private boolean gamePaused;

    // Construcor
    public Playing(Game game) {
        super(game);
        levelManager = new LevelManager();
        loadLevel(levelManager.getCurrentLevel());
        actionBar = new ActionBar(0, 640, 640, 160, this);
        enemyManager = new EnemyManager(this, start, end);
        towerManager = new TowerManager(this);
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
        DatabaseHandler.initializeDB();
        fogofWar = new FogOfWar(lvl[0].length, lvl.length, 0, 0);
    }

    public void loadLevel(Level level) {
        lvl = level.getMap();
        ArrayList<PathPoint> points = level.getPathPoints();
        start = points.get(0);
        end = points.get(1);
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        actionBar.draw(g);
        enemyManager.draw(g);
        towerManager.draw(g);
        projectileManager.draw(g);

        drawSelectedTower(g);
        drawHighlight(g);
    }

    private void drawHighlight(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(mouseX, mouseY, 32, 32);
    }

    private void drawSelectedTower(Graphics g) {
        if (selectedTower != null) {
            g.drawImage(towerManager.getTowerImages()[selectedTower.getTower_type()], mouseX, mouseY, null);
        }
    }

    // Desenare nivel
    public void drawLevel(Graphics g) {
        for(int y = 0; y < lvl.length; y++) {
            for(int x = 0; x < lvl[y].length; x++) {
                if (fogofWar.isVisible(x, y)) {
                    int id = lvl[y][x];
                    if (isAnimation(id)) {
                        g.drawImage(getSprite(id, animation_Index), x * 32, y * 32, null);
                    } else {
                        g.drawImage(getSprite(id), x * 32, y * 32, null);
                    }
                } else {
                    g.setColor(Color.GRAY); //sau o altă culoare sau imagine ce reprezintă ceața
                    g.fillRect(x * 32, y * 32, 32, 32);
                }
            }
        }
    }

    public void update() {
        if(!gamePaused) {
            updateTick();

            // Gold tick
            goldTick++;
            if (goldTick % (60 * 3) == 0)
                actionBar.addGold(1);

            if (isAllEnemiesDead()) {
                if (isThereMoreWaves()) {
                    waveManager.startWaveTimer();
                    if (isWaveTimerOver()) {
                        try {
                            waveManager.increaseWaveIndex();
                        } catch (NoMoreWavesException e) {
                            throw new RuntimeException(e);
                        }
                        enemyManager.getEnemies().clear();
                        waveManager.resetEnemyIndex();
                    }
                } else if (levelManager.hasMoreLevels()) {
                    levelManager.nextLevel();
                    resetEverything();
                } else if(!levelManager.hasMoreLevels()) {
                    DatabaseHandler.insertScore(game.getMenu().getPlayerName(), game.getPlaying().getScore(), actionBar.getLives(), actionBar.getGold(), true);
                    levelManager.gameEnd();
                    levelManager.resetLevel();
                    resetEverything();
                }
            }

            if (isTimeForNewEnemy()) {
                try {
                    spawnEnemy();
                } catch (InvalidEnemyException e) {
                    throw new RuntimeException(e);
                }
            }

            waveManager.update();
            enemyManager.update();
            towerManager.update();
            projectileManager.update();
        }
    }

    private boolean isWaveTimerOver() {

        return waveManager.isWaveTimerOver();
    }

    private boolean isThereMoreWaves() {
        return waveManager.isThereMoreWaves();
    }

    private boolean isAllEnemiesDead() {

        if (waveManager.isThereMoreEnemiesInWave())
            return false;

        for (Enemy e : enemyManager.getEnemies())
            if (e.isAlive())
                return false;

        return true;
    }

    private void spawnEnemy() throws InvalidEnemyException {
        enemyManager.spawnEnemy(waveManager.getNextEnemy());
    }

    private boolean isTimeForNewEnemy() {
        if (waveManager.isTimeForNewEnemy()) {
            return waveManager.isThereMoreEnemiesInWave();
        }

        return false;
    }

    // Metode mouse
    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640) {
            actionBar.mouseClicked(x, y);
        } else {
            if(selectedTower != null) {
                // Daca nu exista o tureta acolo, verificam daca o putem pune si o facem in caz afirmativ
                if(isTileGrass(mouseX, mouseY)) {
                    if (getTowerAt(mouseX, mouseY) == null) {
                        try {
                            towerManager.addTower(selectedTower, mouseX, mouseY);
                        } catch (TowerPlacementException e) {
                            throw new RuntimeException(e);
                        }
                        removeGold(selectedTower.getTower_type());
                        selectedTower = null;
                    }
                }
            } else {
                // Daca exista deja o tureta pe acea pozitie, afisam informatii despre aceasta
                Tower t = getTowerAt(mouseX, mouseY);
                actionBar.displayTower(t);
            }
        }
    }

    private void removeGold(int towerType) {
        actionBar.payForTower(towerType);
    }

    public void upgradeTower(Tower displayedTower) throws InvalidTowerTypeException, InvalidTowerException {
        towerManager.upgradeTower(displayedTower);
    }
    public void removeTower(Tower displayedTower) {
        towerManager.removeTower(displayedTower);
    }

    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x, y);
    }

    private boolean isTileGrass(int x, int y) {
        int id = lvl[y / 32][x / 32];
        int tileType = game.getTileManager().getTile(id).getTileType();
        return tileType == GRASS_TILE;
    }

    public boolean isTileRock(int x, int y) {
        if(y / 32 < 20) {
            int id = lvl[y / 32][x / 32];
            int tileType = game.getTileManager().getTile(id).getTileType();
            return tileType == ROCK_TILE;
        }
        return false;
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640) {
            actionBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
            fogofWar.setPlayerPosition(mouseX / 32, mouseY / 32);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y >= 640) {
            actionBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        actionBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
    }

    public void rewardPlayer(int enemyType) {
        actionBar.addGold(Constants.Enemies.GetReward(enemyType));
        actionBar.addScore(Constants.Enemies.GetScore(enemyType));
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public LevelManager getLevelManager() { return levelManager; }

    // Returneaza tipul dalei functie de pozitie pe mapa
    public int getTileType(int newX, int newY) {

        int xCord = newX / 32;
        int yCord = newY / 32;

        if(xCord < 0 || xCord > 19) {
            return 0;
        }
        if(yCord < 0 || yCord > 19) {
            return 0;
        }

        int id = lvl[newY / 32][newX / 32];

        return game.getTileManager().getTile(id).getTileType();
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            selectedTower = null;
        }
    }

    public void shootEnemy(Tower t, Enemy e) {
        projectileManager.newProjectile(t, e);
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public void removeOneLife() {
        actionBar.removeOneLife();
    }

    public void resetEverything() {
        actionBar.resetEverything();

        enemyManager.reset();
        towerManager.reset();
        projectileManager.reset();
        waveManager.reset();

        mouseX = 0;
        mouseY = 0;

        selectedTower = null;
        goldTick = 0;
        gamePaused = false;
    }

    public int getScore() {
        return actionBar.getScore();
    }

    public FogOfWar getFogOfWar() {
        return fogofWar;
    }

    public ActionBar getActionBar() {
        return actionBar;
    }
}