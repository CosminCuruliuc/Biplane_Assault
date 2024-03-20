package objects;

import java.awt.image.BufferedImage;

// Clasa Tile reprezinta un element grafic din joc (de exemplu, o bucata de teren)
public class Tile {

    private final BufferedImage[] sprite;
    private final int id;
    private final int tileType;

    // Constructor pentru Tile cu o singura imagine
    public Tile(BufferedImage sprite, int id, int tileType) {
        this.sprite = new BufferedImage[1];
        this.sprite[0] = sprite;
        this.id = id;
        this.tileType = tileType;
    }

    // Constructor pentru Tile cu mai multe imagini (animatie)
    public Tile(BufferedImage[] sprite, int id, int tileType) {
        this.sprite = sprite;
        this.id = id;
        this.tileType = tileType;
    }

    // Verifica daca Tile contine o animatie (mai multe imagini)
    public boolean isAnimation() {
        return sprite.length > 1;
    }

    // Getters:
    public int getId() {
        return id;
    }

    public int getTileType() {
        return tileType;
    }

    // Getter pentru prima imagine a sprite-ului
    public BufferedImage getSprite() {
        return sprite[0];
    }

    // Getter pentru imaginea sprite-ului in functie de indexul animatiei
    public BufferedImage getSprite(int animation_Index) {
        return sprite[animation_Index];
    }
}
