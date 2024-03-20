package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

import javax.swing.JPanel;
import java.awt.*;

// Clasa GameScreen extinde JPanel și afișează ecranul jocului, gestionând, de asemenea, obiectele de input.
public class GameScreen extends JPanel {

    private final Game game;

    // Constructor pentru GameScreen
    public GameScreen(Game game) {
        this.game = game;
        setPanelSize();
    }

    // Inițializează obiectele de input și adaugă ascultătorii corespunzători
    public void initInputs() {
        MyMouseListener myMouseListener = new MyMouseListener(game);
        KeyboardListener keyboardListener = new KeyboardListener(game);

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        requestFocus();
    }

    // Setează dimensiunile panoului de joc
    private void setPanelSize() {
        // Dimensiunea panoului
        Dimension size = new Dimension(640, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    // Desenează componentele jocului în funcție de starea curentă a jocului
    public void paintComponent(Graphics Graph) {
        super.paintComponent(Graph);
        game.getRender().render(Graph);
    }
}
