import java.awt.*;

public class GameButtons extends Rectangle {

    // GameButtons to select either to play the game normally or to train an AI

    // Color of the button
    private Color color;

    // The font of the text of the button
    private Font font;

    // The text of the button
    private String text;

    // A GameButton can be created at/with whatever position, size, color, and text desired
    public GameButtons(int x, int y, int width, int height, Color color,
                       String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        font = new Font("Roboto", Font.PLAIN, 20);
        this.text = text;
    }

    // Draw the gameButton using the graphics library
    public void draw(Graphics g){
        g.setFont(font);
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        // Draw the text at a centered position
        g.drawString(text, (int) getCenterX() - 10, (int) getCenterY() + 5);
    }
}
