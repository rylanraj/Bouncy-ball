import javax.swing.*;

// The JFrame of the game
public class Frame extends JFrame {
    public Frame(JPanel content){
        // Set the window title to Bouncy Ball
        super("Bouncy Ball");
        // Make the game exit when the x in the top right is pressed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the size of the window
        this.setSize(1000, 750);
        // Lock the window size as the game isn't designed to be played maximized
        this.setResizable(false);
        // Centre the window
        this.setLocationRelativeTo(null);
        // Show the GamePanel/JPanel by setting it as the content pane
        this.setContentPane(content);
        // Make this JFrame visible as components by default aren't visible
        this.setVisible(true);
    }
}
