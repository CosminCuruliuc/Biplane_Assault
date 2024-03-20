package scenes;

import UI.MyButton;
import helperMethods.DatabaseHandler;
import main.Game;
import java.awt.*;
import javax.swing.JOptionPane;

import static main.GameStates.*;

// Clasa Menu gestioneaza scena de meniu principal a jocului
public class Menu extends GameScene implements SceneMethods {

    private MyButton bPlaying, bEdit, bLoad, bQuit;
    private String playerName;

    // Constructorul clasei Menu
    public Menu(Game game) {
        super(game);
        initButtons();
        initPlayerName();
    }

    private void initPlayerName() {
        playerName = JOptionPane.showInputDialog("Please enter your name:");
    }

    private void initButtons() {
        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;

        bPlaying = new MyButton("Play", x, y, w, h);
        bEdit = new MyButton("Edit", x, y + yOffset, w, h);
        bLoad = new MyButton("Load", x, y + 2 * yOffset, w, h);
        bQuit = new MyButton("Quit", x, y + 3 * yOffset, w, h);
    }

    // Metoda render pentru afișarea sprite-urilor pe ecran
    @Override
    public void render(Graphics g) {
        drawButtons(g);
    }

    // Metode mouse
    @Override
    public void mouseClicked(int x, int y) {
        if(bPlaying.getBounds().contains(x, y)) {
            SetGameState(PLAYING);
        } else if (bEdit.getBounds().contains(x, y)) {
            SetGameState(EDIT);
        } else if (bLoad.getBounds().contains(x, y)) {
            DatabaseHandler.loadGameFromDatabase(game.getPlaying().getEnemyManager(), game.getPlaying().getTowerManager(), game.getPlaying().getLevelManager(), game.getPlaying().getWaveManager(), game.getPlaying().getActionBar());
        } else if (bQuit.getBounds().contains(x, y)) {
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bPlaying.setMouseOver(false);
        bEdit.setMouseOver(false);
        bLoad.setMouseOver(false);
        bQuit.setMouseOver(false);

        if(bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMouseOver(true);
        } else if (bEdit.getBounds().contains(x, y)) {
            bEdit.setMouseOver(true);
        } else if(bLoad.getBounds().contains(x, y)) {
            bLoad.setMouseOver(true);
        } else if(bQuit.getBounds().contains(x, y)) {
            bQuit.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMousePressed(true);
        } else if(bEdit.getBounds().contains(x, y)) {
            bEdit.setMousePressed(true);
        } else if(bLoad.getBounds().contains(x, y)) {
            bLoad.setMousePressed(true);
        } else if(bQuit.getBounds().contains(x, y)) {
            bQuit.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {
    }

    private void resetButtons() {
        bPlaying.resetBooleans();
        bEdit.resetBooleans();
        bLoad.resetBooleans();
        bQuit.resetBooleans();
    }

    private void drawButtons(Graphics g) {
        bPlaying.draw(g);
        bEdit.draw(g);
        bLoad.draw(g);
        bQuit.draw(g);
    }

    public String getPlayerName() {
        return playerName;
    }
}