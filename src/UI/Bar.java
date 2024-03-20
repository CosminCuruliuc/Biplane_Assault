package UI;

import java.awt.*;

public class Bar {

    protected int x, y, width, height;

    public Bar(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Desenează feedback-ul butoanelor atunci când sunt selectate sau peste ele cu mouse-ul
    protected void drawButtonFeedback(Graphics g, MyButton b) {
        // MouseOver
        if (b.getMouseOver())
            g.setColor(Color.white);
        else
            g.setColor(Color.BLACK);

        // Border
        g.drawRect(b.x, b.y, b.width, b.height);

        // MousePressed
        if (b.getMousePressed()) {
            g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
            g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
        }
    }
}
