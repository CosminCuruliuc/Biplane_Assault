package scenes;

import UI.MyButton;
import main.Game;

import java.awt.*;

import static main.GameStates.*;

public class LevelComplete extends GameScene implements SceneMethods {

    private MyButton bMenu, bContinue;

    public LevelComplete(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons() {
        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 300;
        int yOffset = 100;

        bContinue = new MyButton("Continue", x, y, w, h);
        bMenu = new MyButton("Menu", x, y + yOffset, w, h);
    }

    @Override
    public void render(Graphics g) {
        // Level complete Text
        g.setFont(new Font("LucidaSans", Font.BOLD, 50));
        g.setColor(Color.red);
        g.drawString("Level complete!", 160, 80);

        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        bContinue.draw(g);
        bMenu.draw(g);
    }

    private void resetAll() {
        game.getPlaying().resetEverything();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bContinue.getBounds().contains(x, y)) {
            game.getPlaying().loadLevel(game.getPlaying().getLevelManager().getCurrentLevel());
            SetGameState(PLAYING);
        } else if(bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
            resetAll();
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bContinue.setMouseOver(false);
        bMenu.setMouseOver(false);

        if(bContinue.getBounds().contains(x, y)) {
            bContinue.setMouseOver(true);
        } else if(bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(bContinue.getBounds().contains(x, y)) {
            bContinue.setMousePressed(true);
        } else if(bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        bContinue.resetBooleans();
        bMenu.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
