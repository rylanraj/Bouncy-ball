import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// The game needs to implement an Action and KeyListener to take continuous inputs
public class GamePanel extends JPanel implements ActionListener, KeyListener,
        MouseListener {

    // Menu Buttons
    StartButton startButton;
    ExitButton exitButton;

    // Timer that allows action events
    Timer timer;

    // Booleans for button logic, restarting game logic, object instantiation
    boolean startGame;
    boolean jumpGiven;
    boolean pipesCreated = false;
    boolean gameBoundsCreated = false;
    boolean ballCreated = false;

    // Score that increases when the player pases through a pair of pipes
    int score = 0;

    // The ball which the player will be controlling
    Ball ball;

    // Gamebounds of the player
    Floor floor;
    Roof roof;

    // All the pipes present in the game
    ArrayList<Pipe> pipes;

    // FileReader
    FileRead fileRead = new FileRead();

    // Initialization
    public GamePanel() {
        // Instantiation of buttons, arraylist
        startButton = new StartButton(200, 200,200,100,
                Color.RED);
        exitButton = new ExitButton(400, 200, 200, 100,
                Color.CYAN);
        pipes = new ArrayList<>();
        // JPanel needs to be focusable to take keyboard inputs
        this.setFocusable(true);
        // Add the key/mouseListener interfaces which this class implements
        this.addKeyListener(this);
        this.addMouseListener(this);
        // Set the background of the JPanel to white
        this.setBackground(Color.WHITE);
        // Instantiate a timer, with a delay of 10ms between action events
        timer = new Timer(10, this);
        timer.start();
        // Make startGame false so the player can choose a menu option
        startGame = false;
    }

    // Restart the game by changing everything that was altered back to their default values in the constructor
    public void resetGame() {
        score = 0;
        startButton = new StartButton(200, 200,200,100,
                Color.RED);
        exitButton = new ExitButton(400, 200, 200, 100,
                Color.CYAN);
        ball = new Ball(20,40,20,20, Color.BLACK);
        pipes = new ArrayList<>();
        startGame = false;
        pipesCreated = false;
    }

    // Paint all the components in the game
    public void paintComponent(Graphics g){
        // Whatever is called in the constructor
        super.paintComponent(g);

        // Draw the ball
        ball.draw(g);

        // Draw all the pipes in the list
        g.setColor(Color.GREEN);
        for (Pipe p : pipes){
            p.draw(g);
        }

        // Draw the score
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Roboto", Font.BOLD, 20));
        g2d.drawString(Integer.toString(score), 450, 100);
        g2d.setColor(Color.ORANGE);
        g2d.drawString("HIGHSCORE: " + fileRead.retrieveDataFromFile("highscore.txt"), 375, 60);

        // Draw the game bounds
        floor.draw(g);
        roof.draw(g);
        if (!startGame){
            startButton.draw(g);
            exitButton.draw(g);
        }
    }

    // Actions performed during the course of the game
    @Override
    public void actionPerformed(ActionEvent e){

        // Create the ball
        if (!ballCreated){
            createBall();
            ballCreated = true;
        }

        // Create the game bounds
        if (!gameBoundsCreated){
            createGameBounds();
            gameBoundsCreated = true;
        }

        // Create the pipes
        if (!pipesCreated){
            createAllPipes();
            pipesCreated = true;
        }

        // If the game starts
        if(startGame){
            // Check the actions for all the pipes
            checkPipeActions();
            // Apply gravity to the ball
            ball.appleGravity();
            // Check if the player is in bounds
            checkBounds();
            // Check if we need  to add a point
            checkAddPoint();
        }

        //Repaint so that the game refreshes
        repaint();
    }

    // Check all the necessary actions for the pipes
    public void checkPipeActions() {
        for (Pipe p : pipes){
            p.move();
            p.checkGameOver(ball, this);
        }
    }

    // Check if the player is in bounds
    public void checkBounds() {
        floor.checkGameOver(ball, this);
        roof.checkGameOver(ball, this);
    }

    // Instantiate the game components
    public void createBall(){
        ball = new Ball(20,40,20,20, Color.BLACK);
    }

    public void createGameBounds(){
        floor = new Floor(0,713,1400,8, Color.BLACK);
        roof = new Roof(0,0,1400,7, Color.BLACK);
    }

    public void createAllPipes(){
        // Define the pair of pipes
        TopPipe topPipe;
        Pipe bottomPipe;

        // Put them at their places
        int height = 120;
        int pipePosX = 200;
        int pipePostY = 0;

        // Vertical Gap between pipes
        int pipeGap = 350;

        // Horizontal distance between pipes
        int pipeDistance = 150;

        // Put them in positions that make the game possible bases on their index in the pipes arraylist
        for (int i = 0; i < 100; i++){
            pipePosX += pipeDistance;
            topPipe = new TopPipe(pipePosX, pipePostY, 60, height, Color.GREEN);
            bottomPipe = new BottomPipe(pipePosX, pipePostY + pipeGap, 60, 1000, Color.GREEN);
            if (i % 2 == 0 && i % 5 != 0){
                topPipe = new TopPipe(pipePosX, pipePostY, 60, height + 50, Color.GREEN);
                bottomPipe = new BottomPipe(pipePosX, pipePostY + 50 + pipeGap, 60, 1000, Color.GREEN);
            }
            else if (i % 3 == 0){
                topPipe = new TopPipe(pipePosX, pipePostY - 100, 60, height, Color.GREEN);
                bottomPipe = new BottomPipe(pipePosX, pipePostY - 100  + pipeGap, 60, 1000, Color.GREEN);
            }
            else if (i % 5 == 0){
                topPipe = new TopPipe(pipePosX, pipePostY, 60, height + 100, Color.GREEN);
                bottomPipe = new BottomPipe(pipePosX, pipePostY + 100 + pipeGap, 60, 1000, Color.GREEN);
            }
            pipes.add(topPipe);
            pipes.add(bottomPipe);
        }
    }

    // Check if a point needs to be added
    public void checkAddPoint(){
        for (Pipe pipe : pipes) {
            if (ball.getMinX() > pipe.getMaxX() && pipe.getClass()
                    == TopPipe.class && !pipe.isPointAdded()) {
                pipe.setPointAdded(true);
                score++;
            }
        }
    }

    // Necessary override keylistener methods
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //If we press space and a jump hasn't been given to the ball
        if (e.getKeyCode() == 32 && !jumpGiven){
            // Give a jump to the ball
            ball.jump();
            // jumpGiven is now true
            jumpGiven = true;
        }
    }

    // When a keys released this is called
    @Override
    public void keyReleased(KeyEvent e) {
        // If space is released
        if (e.getKeyCode() == 32){
            // Make the ball able to jump again
            jumpGiven = false;
        }
    }

    // Called when mouse events occur
    @Override
    public void mouseClicked(MouseEvent e) {
        // Start game if they click the start game button
        if (startButton.contains(e.getPoint())){
            startGame = true;
        }

        // Exit the game if they click the exit game button
        if (exitButton.contains(e.getPoint())){
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
