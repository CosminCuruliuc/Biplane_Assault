package main;

import java.awt.*;

// Clasa Render gestionează randarea componentelor jocului în funcție de starea curentă a jocului.
public class Render {

    public Game game;

    public Render(Game game) {
        this.game = game;
    }

    // Randarea componentelor jocului în funcție de starea curentă a jocului
    public void render(Graphics Graph) {
        switch (GameStates.gameStates) {
            case MENU -> game.getMenu().render(Graph);
            case PLAYING -> game.getPlaying().render(Graph);
            case EDIT -> game.getEdit().render(Graph);
            case GAME_OVER -> game.getGameOver().render(Graph);
            case LEVEL_COMPLETE -> game.getLevelComplete().render(Graph);
            case GAME_COMPLETE -> game.getGameComplete().render(Graph);
            default -> {
            }
        }
    }
}