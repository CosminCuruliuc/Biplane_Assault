package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static main.GameStates.*;

import main.Game;
import main.GameStates;

// Clasa KeyboardListener care implementează interfața KeyListener pentru a primi evenimente de la tastatură
public class KeyboardListener implements KeyListener {

    private final Game game;

    // Constructorul
    public KeyboardListener(Game game) {
        this.game = game;
    }

    // Metoda keyTyped este apelată atunci când o tastă este apăsată și eliberată
    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Metoda keyPressed este apelată atunci când o tastă este apăsată
    @Override
    public void keyPressed(KeyEvent e) {
        if(GameStates.gameStates == EDIT) {
            game.getEdit().keyPressed(e);
        } else if(GameStates.gameStates == PLAYING) {
            game.getPlaying().keyPressed(e);
        }
    }

    // Metoda keyReleased este apelată atunci când o tastă este eliberată
    @Override
    public void keyReleased(KeyEvent e) {
    }
}