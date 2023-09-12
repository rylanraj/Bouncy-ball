import java.awt.*;

// Gamebounds (the floor and roof)
public class GameBound extends Rectangle {
    // Color
    Color color;

    // Create a game bound at whatever position size and color wanted
    public GameBound(int x, int y, double width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = (int) width;
        this.height = height;
        this.color = color;
    }

    // Draw it using the graphics library
    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    // If the player hits the roof or floor, it's game over so the game resets
    public void checkGameOver(Ball ball, GamePanel game) {
        if (this.intersects(ball)){
            game.resetGame();
        }
    }
}
