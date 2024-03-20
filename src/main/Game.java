package main;

import helperMethods.LoadSave;
import managers.TileManager;
import scenes.*;

import javax.swing.JFrame;

// Clasa principala a jocului
public class Game extends JFrame implements Runnable {

    private GameScreen gameScreen_1;

    // Clase
    private Render render;
    private Menu menu;
    private Playing playing;
    private Edit edit;
    private GameOver gameOver;
    private TileManager tileManager;
    private LevelComplete levelComplete;
    private GameWon gameWon;

    // Constructorul clasei Game
    public Game() {

        initClases();
        createDefaultLevel();

        // Setează proprietățile ferestrei principale a jocului
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Biplane Assault");

        // Adaugă ecranul de joc în fereastra principală
        add(gameScreen_1);

        // Ajustează dimensiunea ferestrei pentru a încadra componentele
        pack();
        setVisible(true);
    }

    // Metoda pentru inițializarea claselor folosite în joc
    private void initClases() {
        tileManager = new TileManager();
        render = new Render(this);
        gameScreen_1 = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        edit = new Edit(this);
        gameOver = new GameOver(this);
        levelComplete = new LevelComplete(this);
        gameWon = new GameWon(this);
    }

    // Creaza un nivel care contine doar iarba
    private void createDefaultLevel() {
        int[] arr = new int[400];
        LoadSave.CreateLevel("new level", arr);
    }

    // Metoda pentru a începe execuția thread-ului de joc
    private void start() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    // Metoda pentru actualizarea stării jocului
    private void updateGame() {
        switch (GameStates.gameStates) {
            case PLAYING -> playing.update();
            case EDIT -> edit.update();
            default -> {
            }
        }
    }

    // Metoda main pentru a rula jocul
    public static void main(String[] args) {
        Game game = new Game();
        game.gameScreen_1.initInputs();                                  // Inițializează obiectele de input
        game.start();                                                    // Începe execuția thread-ului de joc
    }

    // Metoda run implementată din interfața Runnable, conține bucla principală a jocului
    @Override
    public void run() {

        // Setează FPS (Frames Per Second) și UPS (Updates Per Second) pentru joc
        double FPS_SET = 120.0;
        double timePerFrame = 1000000000.0 / FPS_SET;
        double UPS_SET = 60.0;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        long now;

        // Bucla principală a jocului
        while(true) {

            now = System.nanoTime();

            // Renderizare
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }

            // Actualizare
            if (now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }

            // Verificare FPS și UPS
            if(System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    // Getters si setters:
    public Render getRender() {
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Edit getEdit() {
        return edit;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public LevelComplete getLevelComplete() {
        return levelComplete;
    }

    public GameWon getGameComplete() {
        return gameWon;
    }
}