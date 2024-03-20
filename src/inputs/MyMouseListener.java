package inputs;

import main.Game;
import main.GameStates;
import scenes.SceneMethods;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// Clasa MyMouseListener care implementează interfețele MouseListener și MouseMotionListener pentru a primi evenimente de la mouse
public class MyMouseListener implements MouseListener, MouseMotionListener {
    private final Game game;
    private SceneMethods strategy;

    public void setStrategy(Game game) {
        switch (GameStates.gameStates) {
            case PLAYING -> this.strategy = game.getPlaying();
            case MENU -> this.strategy = game.getMenu();
            case EDIT -> this.strategy = game.getEdit();
            case GAME_OVER -> this.strategy = game.getGameOver();
            case LEVEL_COMPLETE -> this.strategy = game.getLevelComplete();
            case GAME_COMPLETE -> this.strategy = game.getGameComplete();
            default -> {
            }
        }
    }
    public MyMouseListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            setStrategy(game);
            strategy.mouseClicked(e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setStrategy(game);
        strategy.mouseClicked(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setStrategy(game);
        strategy.mouseReleased(e.getX(), e.getY());
    }

    // Metoda mouseEntered este apelată atunci când cursorul mouse-ului intră în zona componentei care ascultă evenimentele de la mouse
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    // Metoda mouseExited este apelată atunci când cursorul mouse-ului iese din zona componentei care ascultă evenimentele de la mouse
    @Override
    public void mouseExited(MouseEvent e) {
    }

    // Metoda mouseDragged este apelată atunci când un buton al mouse-ului este apăsat și cursorul mouse-ului este mișcat
    @Override
    public void mouseDragged(MouseEvent e) {
        setStrategy(game);
        strategy.mouseDragged(e.getX(), e.getY());
    }

    // Metoda mouseMoved este apelată atunci când cursorul mouse-ului este mișcat, dar niciun buton al mouse-ului nu este apăsat
    @Override
    public void mouseMoved(MouseEvent e) {
        setStrategy(game);
        strategy.mouseMoved(e.getX(), e.getY());
    }
}