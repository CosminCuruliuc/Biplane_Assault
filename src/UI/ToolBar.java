package UI;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import helperMethods.LoadSave;
import objects.Tile;
import scenes.Edit;

// Clasa ToolBar este o clasă care extinde clasa Bar și este utilizată pentru a crea o bară de instrumente într-un editor de nivel al unui joc.
// Această bară de instrumente conține diferite butoane pentru selecția și plasarea obiectelor, precum și butoane pentru salvarea nivelului sau
// întoarcerea la meniul principal.
public class ToolBar extends Bar {
    private final Edit editing; // Obiectul de editare asociat cu această bară de instrumente
    private MyButton bMenu, bSave1, bSave2, bSave3; // Butonul pentru meniu și salvare
    private MyButton bPathStart, bPathEnd; // Butoane pentru a marca inceputl si sfarsitul drumului
    private BufferedImage pathStart, pathEnd; // Salvam imaginile pentru inceput si sfarsit de drum
    private Tile selectedTile;// Dala selectată pentru a fi plasată în editor

    private final Map<MyButton, ArrayList<Tile>> map = new HashMap<>();

    // Butoanele pentru diferitele tipuri de dale
    private MyButton bGrass, bSand, bRoadS, bRoadC, bSandC, bSandB, bSandI, bRock;
    private MyButton currentButton; // Butonul curent selectat pentru dale multiple
    private int currentIndex = 0; // Indexul dalei selectate în lista de dale asociată cu butonul curent

    // Constructorul clasei ToolBar
    public ToolBar(int x, int y, int width, int height, Edit editing) {
        super(x, y, width, height);
        this.editing = editing;
        initButtons();
        initPathImgs();
    }

    // Inițializează butoanele din bara de instrumente
    private void initButtons() {

        bMenu = new MyButton("Menu", 2, 642, 100, 30);
        bSave1 = new MyButton("Save_1", 2, 674, 100, 30);
        bSave2 = new MyButton("Save_2", 2, 706, 100, 30);
        bSave3 = new MyButton("Save_3", 2, 738, 100, 30);

        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);
        int i = 0;

        bGrass = new MyButton("Grass", xStart, yStart, w, h, i++);
        bSand = new MyButton("Sand", xStart + xOffset, yStart, w, h, i++);

        initMapButton(editing.getGame().getTileManager().getRoadsS(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(editing.getGame().getTileManager().getRoadsC(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(editing.getGame().getTileManager().getCorners(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(editing.getGame().getTileManager().getBeaches(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(editing.getGame().getTileManager().getIslands(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(editing.getGame().getTileManager().getRocks(), xStart, yStart, xOffset, w, h, i++);

        bPathStart = new MyButton("StartPath", xStart, yStart + xOffset, w, h, i++);
        bPathEnd = new MyButton("EndPath", xStart + xOffset, yStart + xOffset, w, h, i);
    }

    // Metoda pentru initializarea imaginilor drumului
    private void initPathImgs() {
        pathStart = LoadSave.getSpriteAtlas().getSubimage(18 * 32, 0,32,32);
        pathEnd = LoadSave.getSpriteAtlas().getSubimage(17 * 32, 0,32,32);
    }

    // Inițializează butonul asociat cu o listă de dale și adaugă acesta în harta de butoane
    private void initMapButton(ArrayList<Tile> list, int x, int y, int xOff, int w, int h, int id) {
        MyButton b = new MyButton("", x + xOff * id, y, w, h, id);
        map.put(b, list);
    }

    // Salvează nivelul curent
    private void saveLevel1() {
        editing.saveLevel1();
    }

    private void saveLevel2() {
        editing.saveLevel2();
    }

    private void saveLevel3() {
        editing.saveLevel3();
    }

    // Rotește sprite-ul dalei selectate
    public void rotateSprite() {

        currentIndex++;
        if (currentIndex >= map.get(currentButton).size())
            currentIndex = 0;
        selectedTile = map.get(currentButton).get(currentIndex);
        editing.setSelectedTile(selectedTile);
    }

    // Desenează bara de instrumente și componentele sale pe ecran
    public void draw(Graphics g) {

        // Background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        // Buttons
        drawButtons(g);
    }

    // Desenează butoanele din bara de instrumente
    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bSave1.draw(g);
        bSave2.draw(g);
        bSave3.draw(g);

        drawPathButton(g, bPathStart, pathStart);
        drawPathButton(g, bPathEnd, pathEnd);

        drawNormalButton(g, bGrass);
        drawNormalButton(g, bSand);
        drawSelectedTile(g);
        drawMapButtons(g);
    }

    // Metoda pentru desenarea celor doua imagini pentru inceput si sfarsit de drum
    private void drawPathButton(Graphics g, MyButton b, BufferedImage path) {
        g.drawImage(path, b.x, b.y, b.width, b.height, null);
        drawButtonFeedback(g, b);
    }

    // Desenează un buton normal (de exemplu, Grass, Sand)
    private void drawNormalButton(Graphics g, MyButton b) {
        g.drawImage(getButtImg(b.getId()), b.x, b.y, b.width, b.height, null);
        drawButtonFeedback(g, b);

    }

    // Desenează butoanele asociate cu dale multiple (de exemplu, RoadS, RoadC, SandC, SandB, SandI)
    private void drawMapButtons(Graphics g) {
        for (Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()) {
            MyButton b = entry.getKey();
            BufferedImage img = entry.getValue().get(0).getSprite();

            g.drawImage(img, b.x, b.y, b.width, b.height, null);
            drawButtonFeedback(g, b);
        }

    }

    // Desenează dala selectată în prezent
    private void drawSelectedTile(Graphics g) {

        if (selectedTile != null) {
            g.drawImage(selectedTile.getSprite(), 550, 650, 50, 50, null);
            g.setColor(Color.black);
            g.drawRect(550, 650, 50, 50);
        }

    }
    // Returnează sprite-ul asociat cu ID-ul specificat
    public BufferedImage getButtImg(int id) {
        return editing.getGame().getTileManager().getSprite(id);
    }

    // Verifică dacă un click de mouse a avut loc pe unul dintre butoane și efectuează acțiunea corespunzătoare
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            SetGameState(MENU);
        else if (bSave1.getBounds().contains(x, y))
            saveLevel1();
        else if (bSave2.getBounds().contains(x, y))
            saveLevel2();
        else if (bSave3.getBounds().contains(x, y))
            saveLevel3();
        else if (bSand.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileManager().getTile(bSand.getId());
            editing.setSelectedTile(selectedTile);
        } else if (bGrass.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileManager().getTile(bGrass.getId());
            editing.setSelectedTile(selectedTile);
        } else if(bPathStart.getBounds().contains(x, y)) {
            selectedTile = new Tile(pathStart, -1, -1);
            editing.setSelectedTile(selectedTile);
        } else if(bPathEnd.getBounds().contains(x, y)) {
            selectedTile = new Tile(pathEnd, -2, -2);
            editing.setSelectedTile(selectedTile);
        }
        else {
            for (MyButton b : map.keySet())
                if (b.getBounds().contains(x, y)) {
                    selectedTile = map.get(b).get(0);
                    editing.setSelectedTile(selectedTile);
                    currentButton = b;
                    currentIndex = 0;
                    return;
                }
        }

    }

    // Verifică dacă mouse-ul a fost mutat peste unul dintre butoane și setează valoarea mouseOver pentru butonul respectiv
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bSave1.setMouseOver(false);
        bSave2.setMouseOver(false);
        bSave3.setMouseOver(false);
        bSand.setMouseOver(false);
        bGrass.setMouseOver(false);
        bPathStart.setMouseOver(false);
        bPathEnd.setMouseOver(false);
        for (MyButton b : map.keySet())
            b.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if (bSave1.getBounds().contains(x, y))
            bSave1.setMouseOver(true);
        else if (bSave2.getBounds().contains(x, y))
            bSave2.setMouseOver(true);
        else if (bSave3.getBounds().contains(x, y))
            bSave3.setMouseOver(true);
        else if (bSand.getBounds().contains(x, y))
            bSand.setMouseOver(true);
        else if (bGrass.getBounds().contains(x, y))
            bGrass.setMouseOver(true);
        else if (bPathStart.getBounds().contains(x, y))
            bPathStart.setMouseOver(true);
        else if (bPathEnd.getBounds().contains(x, y))
            bPathEnd.setMouseOver(true);
        else {
            for (MyButton b : map.keySet())
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    return;
                }
        }
    }

    // Verifică dacă mouse-ul a fost apăsat pe unul dintre butoane și setează valoarea mousePressed pentru butonul respectiv
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else if (bSave1.getBounds().contains(x, y))
            bSave1.setMousePressed(true);
        else if (bSave2.getBounds().contains(x, y))
            bSave2.setMousePressed(true);
        else if (bSave3.getBounds().contains(x, y))
            bSave3.setMousePressed(true);
        else if (bSand.getBounds().contains(x, y))
            bSand.setMousePressed(true);
        else if (bGrass.getBounds().contains(x, y))
            bGrass.setMousePressed(true);
        else if (bPathStart.getBounds().contains(x, y))
            bPathStart.setMousePressed(true);
        else if (bPathEnd.getBounds().contains(x, y))
            bPathEnd.setMousePressed(true);
        else {
            for (MyButton b : map.keySet())
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
        }

    }

    // Resetează valoarea mousePressed și mouseOver pentru toate butoanele atunci când mouse-ul este eliberat
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bSave1.resetBooleans();
        bSave2.resetBooleans();
        bSave3.resetBooleans();
        bGrass.resetBooleans();
        bSand.resetBooleans();
        bPathStart.resetBooleans();
        bPathEnd.resetBooleans();
        for (MyButton b : map.keySet())
            b.resetBooleans();
    }

    //Getters:
    public BufferedImage getPathStart() {
        return pathStart;
    }

    public BufferedImage getPathEnd() {
        return pathEnd;
    }
}