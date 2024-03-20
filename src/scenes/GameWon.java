package scenes;

import UI.MyButton;
import main.Game;

import java.awt.*;

import static main.GameStates.*;

public class GameWon extends GameScene implements SceneMethods {

    private MyButton bMenu;
    public GameWon(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons() {
        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 300;

        bMenu = new MyButton("Menu", x, y, w, h);
    }

    @Override
    public void render(Graphics g) {
        // Game over text
        g.setFont(new Font("LucidaSans", Font.BOLD, 50));
        g.setColor(Color.green);
        g.drawString("Congrats bro, u WON!", 90, 80);

        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        int final_score = getScore();
        g.drawString("Final score: " + final_score, 160, 120);
        bMenu.draw(g);
    }
    private void resetAll() {
        game.getPlaying().resetEverything();
    }

    private int getScore() { return game.getPlaying().getScore(); }

    @Override
    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
            resetAll();
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);

        if(bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }
    }


    @Override
    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
