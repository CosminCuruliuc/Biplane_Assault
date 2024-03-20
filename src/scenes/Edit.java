package scenes;

import UI.ToolBar;
import helperMethods.LoadSave;
import main.Game;
import objects.PathPoint;
import objects.Tile;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static helperMethods.Constants.Tiles.*;

// Clasa Edit gestioneaza scena de editare a nivelului jocului
public class Edit extends GameScene implements SceneMethods {

    private int[][] lvl;
    private Tile selectedTile;
    private int mouseX, mouseY;
    private boolean drawSelect = false;
    private int lastTileY, lastTileX, lastTileId;
    private final ToolBar toolbar;
    private PathPoint start, end;

    // Constructorul clasei Edit
    public Edit(Game game) {
        super(game);
        loadDefaultLevel();
        toolbar = new ToolBar(0, 640, 640, 160, this);
    }

    // Incarca nivelul default
    private void loadDefaultLevel() {
        lvl = LoadSave.GetLevelData("new level");
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("new level");
        assert points != null;
        start = points.get(0);
        end = points.get(1);
    }

    // Metoda pentru randarea grafica a elementelor
    @Override
    public void render(Graphics g) {
        drawLevel(g);
        toolbar.draw(g);
        drawSelectedTile(g);
        drawPathPoints(g);
    }

    private void drawPathPoints(Graphics g) {
        if(start != null) {
            g.drawImage(toolbar.getPathStart(), start.getX()*32, start.getY() * 32, 32, 32, null);
        }
        if(end != null) {
            g.drawImage(toolbar.getPathEnd(),  end.getX()*32, end.getY() * 32, 32, 32, null);
        }

    }

    // Actualizarile
    public void update() {
        updateTick();
    }

    // Deseneaza nivelul
    public void drawLevel(Graphics g) {
        for(int y = 0; y < lvl.length; y++) {
            for(int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                if (isAnimation(id)) {
                    g.drawImage(getSprite(id, animation_Index), x * 32, y * 32, null);
                } else {
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
                }
            }
        }
    }

    // Salveaza nivelul
    public void saveLevel1() {
        LoadSave.SaveLevel("new level", lvl, start, end);
    }
    public void saveLevel2() {
        LoadSave.SaveLevel("new level2", lvl, start, end);
    }
    public void saveLevel3() {
        LoadSave.SaveLevel("new level3", lvl, start, end);
    }

    // Deseneaza Tile-ul selectat
    private void drawSelectedTile(Graphics g) {
        if(selectedTile != null && drawSelect) {
            g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    // Setter:
    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
        drawSelect = true;
    }

    // Schimba Tile-ul
    private void changeTile(int x, int y) {
        if(selectedTile != null) {

            int tileX = x / 32;
            int tileY = y / 32;
            if(selectedTile.getId() >= 0) {
                if(lastTileX == tileX && lastTileY == tileY &&
                    lastTileId == selectedTile.getId()) {
                    return;
                }
                lastTileX = tileX;
                lastTileY = tileY;
                lastTileId = selectedTile.getId();
                lvl[tileY][tileX] = selectedTile.getId();
            } else {
                int id = lvl[tileY][tileX];
                if(game.getTileManager().getTile(id).getTileType() == ROAD_TILE) {
                    if(selectedTile.getId() == -1) {
                        start = new PathPoint(tileX, tileY);
                    } else {
                        end = new PathPoint(tileX, tileY);
                    }
                }
            }
        }
    }

    // Metode mouse
    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640) {
            toolbar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            toolbar.mouseMoved(x, y);
            drawSelect = false;
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
            drawSelect = true;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y >= 640) {
            toolbar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        toolbar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
        if(y <= 640) {
            changeTile(x, y);
        }
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R) {
            toolbar.rotateSprite();
        }
    }
}
