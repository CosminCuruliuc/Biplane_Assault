package managers;

import helperMethods.ImageFix;
import helperMethods.LoadSave;
import objects.Tile;

import static helperMethods.Constants.Tiles.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

// Clasa TileManager gestionează dalele (tiles) din joc, inclusiv încărcarea imaginilor și crearea diferitelor tipuri de dale.
public class TileManager {

    public Tile GRASS, SAND, ROCK_GRASS_1,ROCK_GRASS_2,ROCK_GRASS_3,ROCK_GRASS_4,ROAD_GRASS_S_1, ROAD_GRASS_S_2, ROAD_GRASS_S_3, ROAD_GRASS_S_4, ROAD_GRASS_C_1, ROAD_GRASS_C_2, ROAD_GRASS_C_3, ROAD_GRASS_C_4
    ,SAND_GRASS_S_1, SAND_GRASS_S_2, SAND_GRASS_S_3 , SAND_GRASS_S_4, SAND_GRASS_C_1, SAND_GRASS_C_2, SAND_GRASS_C_3, SAND_GRASS_C_4, SAND_GRASS_I_1,
    SAND_GRASS_I_2, SAND_GRASS_I_3, SAND_GRASS_I_4;
    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public ArrayList<Tile> roadsS = new ArrayList<>();
    public ArrayList<Tile> roadsC = new ArrayList<>();
    public ArrayList<Tile> corners = new ArrayList<>();
    public ArrayList<Tile> beaches = new ArrayList<>();
    public ArrayList<Tile> islands = new ArrayList<>();
    public ArrayList<Tile> rocks = new ArrayList<>();

    public TileManager() {
        loadAtlas();
        createTiles();
    }

    // Creează diferitele tipuri de dale și le adaugă în ArrayList-urile corespunzătoare
    private void createTiles() {
        int id = 0;
        tiles.add(GRASS = new Tile(getAnimatedSprite(19, 6, 9, 5), id++, GRASS_TILE));
        tiles.add(SAND = new Tile(getAnimatedSprite(22, 6, 9,8), id++, SAND_TILE));

        roadsS.add(ROAD_GRASS_S_1 = new Tile(getSprite(1, 9), id++, ROAD_TILE));
        roadsS.add(ROAD_GRASS_S_2 = new Tile(getSprite(2, 10), id++, ROAD_TILE));
        roadsS.add(ROAD_GRASS_S_3 = new Tile(getSprite(1, 11), id++, ROAD_TILE));
        roadsS.add(ROAD_GRASS_S_4 = new Tile(getSprite(0, 10), id++, ROAD_TILE));

        roadsC.add(ROAD_GRASS_C_1 = new Tile(getSprite(0, 9), id++, ROAD_TILE));
        roadsC.add(ROAD_GRASS_C_2 = new Tile(getSprite(0, 11), id++, ROAD_TILE));
        roadsC.add(ROAD_GRASS_C_3 = new Tile(getSprite(2, 11), id++, ROAD_TILE));
        roadsC.add(ROAD_GRASS_C_4 = new Tile(getSprite(2, 9), id++, ROAD_TILE));

        beaches.add(SAND_GRASS_S_1 = new Tile(getSprite(1, 6), id++, SAND_TILE));
        beaches.add(SAND_GRASS_S_2 = new Tile(getSprite(2, 7), id++, SAND_TILE));
        beaches.add(SAND_GRASS_S_3 = new Tile(getSprite(1, 8), id++, SAND_TILE));
        beaches.add(SAND_GRASS_S_4 = new Tile(getSprite(0, 7), id++, SAND_TILE));

        corners.add(SAND_GRASS_C_1 = new Tile(getSprite(0, 6), id++, SAND_TILE));
        corners.add(SAND_GRASS_C_2 = new Tile(getSprite(0, 8), id++, SAND_TILE));
        corners.add(SAND_GRASS_C_3 = new Tile(getSprite(2, 8), id++, SAND_TILE));
        corners.add(SAND_GRASS_C_4 = new Tile(getSprite(2, 6), id++, SAND_TILE));

        islands.add(SAND_GRASS_I_1 = new Tile(getSprite(3, 6), id++, SAND_TILE));
        islands.add(SAND_GRASS_I_2 = new Tile(getSprite(4, 6), id++, SAND_TILE));
        islands.add(SAND_GRASS_I_3 = new Tile(getSprite(4, 7), id++, SAND_TILE));
        islands.add(SAND_GRASS_I_4 = new Tile(getSprite(3, 7), id++, SAND_TILE));

        rocks.add(ROCK_GRASS_1 = new Tile(ImageFix.buildImage(getImages(19, 6, 21, 5)), id++, ROCK_TILE));
        rocks.add(ROCK_GRASS_2 = new Tile(ImageFix.getBuildRotatedImage(getImages(19, 6, 21, 5), 90, 2), id++, ROCK_TILE));
        rocks.add(ROCK_GRASS_3 = new Tile(ImageFix.getBuildRotatedImage(getImages(19, 6, 21, 5), 180, 2), id++, ROCK_TILE));
        rocks.add(ROCK_GRASS_4 = new Tile(ImageFix.getBuildRotatedImage(getImages(19, 6, 21, 5), 270, 2), id++, ROCK_TILE));

        tiles.addAll(roadsS);
        tiles.addAll(roadsC);
        tiles.addAll(beaches);
        tiles.addAll(corners);
        tiles.addAll(islands);
        tiles.addAll(rocks);
    }

    // Returnează un array de imagini cu coordonatele specificate
    private BufferedImage[] getImages(int x1, int y1, int x2, int y2) {
        return new BufferedImage[] {getSprite(x1, y1), getSprite(x2, y2)};
    }

    // Încarcă sprite atlas-ul pentru dale
    private void loadAtlas() {
        atlas = LoadSave.getSpriteAtlas();
    }

    // Obține o dală (tile) cu un ID specificat
    public Tile getTile(int id) {
        return tiles.get(id);
    }

    // Obține sprite-ul pentru un ID specificat
    public BufferedImage getSprite(int id) {
        return tiles.get(id).getSprite();
    }

    // Obține sprite-ul animat pentru un ID specificat și un index de animație
    public BufferedImage getAniSprite(int id, int animation_Index) {
        return tiles.get(id).getSprite(animation_Index);
    }

    // Obține sprite-ul pentru coordonatele x și y specificate
    private BufferedImage getSprite(int x, int y) {
        return atlas.getSubimage(32 * x, 32 * y, 32, 32);
    }

    // Obține un sprite animat cu coordonatele specificate
    private BufferedImage[] getAnimatedSprite(int x1, int y1, int x2, int y2) {
        BufferedImage[] array = new BufferedImage[2];
        array[0] = getSprite(x1, y1);
        array[1] = getSprite(x2, y2);
        return array;
    }

    // Verifică dacă sprite-ul pentru un ID specificat este animat
    public boolean isSpriteAnimation(int id) {
        return tiles.get(id).isAnimation();
    }

    // Getters:
    public ArrayList<Tile> getRocks() { return rocks; }

    public ArrayList<Tile> getRoadsS() {
        return roadsS;
    }

    public ArrayList<Tile> getRoadsC() {
        return roadsC;
    }

    public ArrayList<Tile> getCorners() {
        return corners;
    }

    public ArrayList<Tile> getBeaches() {
        return beaches;
    }

    public ArrayList<Tile> getIslands() {
        return islands;
    }
}
