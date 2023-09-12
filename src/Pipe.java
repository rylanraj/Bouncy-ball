import java.awt.*;

public class Pipe extends Rectangle {
    Color color;
    private double pipeHorizontalVelocity = 1;
    private boolean pointAdded = false;

    public Pipe(double x, double y, double width, double height, Color color) {
        this.x = (int) x;
        this.y = (int) y;
        this.width = (int) width;
        this.height = (int) height;
        this.color = color;
    }

    public void move(){
        x -= pipeHorizontalVelocity;
    }


    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void checkGameOver(Ball ball, GamePanel panel) {
        if (this.intersects(ball)){
            // If it's a high score, save it
            if (panel.score > Integer.valueOf(new FileRead().retrieveDataFromFile("highscore.txt"))){
                FileWrite fileWrite = new FileWrite();
                fileWrite.writeContentsToFile("highscore.txt", String.valueOf(panel.score),false);
            }
            panel.resetGame();
        }
    }

    public boolean isPointAdded() {
        return pointAdded;
    }

    public void setPointAdded(boolean pointAdded) {
        this.pointAdded = pointAdded;
    }
}
