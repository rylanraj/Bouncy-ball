import java.awt.*;
public class Ball extends Rectangle {

    // The color of the ball
    private Color color;

    // Gravity and gForce (the rate at which gravity increases)
    double gravity = 1.5;
    double gForce = 0.2;

    // Create the ball at the position and dimensions and color needed
    public Ball(double x, double y, double width, double height, Color color) {
        this.x = (int) x;
        this.y = (int) y;
        this.width = (int) width;
        this.height = (int) height;
        this.color = color;
    }

    // Apply gravity to the ball by increases it's y value from gravity
    public void appleGravity(){
        y += (int)gravity;
        gravity += gForce;     
    }

    // Make the ball jump by making gravity negative
    public void jump(){
        gravity = -8;
    }

    // Draw the ball as an fillOval using the graphics library
    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x,y,width,height);
    }

}
