package scenes;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

// Clasa GameScene serveste ca baza pentru scenele de joc (Menu, Edit, Playing)
public class GameScene {

    // Referinta catre instanta jocului
    protected Game game;
    protected int animation_Index;
    protected int Animation_Speed = 50;
    protected int tick;

    // Constructor pentru GameScene
    public GameScene(Game game) {
        this.game = game;
    }

    // Actualizeaza indexul animatiei
    protected void updateTick() {
        tick++;
        if(tick >= Animation_Speed) {
            tick = 0;
            animation_Index++;
            if(animation_Index >= 2)
                animation_Index = 0;
        }
    }

    // Obtine sprite-ul in functie de ID
    protected BufferedImage getSprite(int id) {
        return game.getTileManager().getSprite(id);
    }

    // Obtine sprite-ul in functie de ID si indexul animatiei
    protected BufferedImage getSprite(int id, int animation_Index) {
        return game.getTileManager().getAniSprite(id, animation_Index);
    }

    // Verifica daca Tile-ul are animatie
    protected boolean isAnimation(int id) {
        return game.getTileManager().isSpriteAnimation(id);
    }

    // Getter pentru a obtine referinta la instanta jocului
    public Game getGame() {
        return game;
    }
}