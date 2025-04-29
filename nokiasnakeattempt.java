import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class nokiasnakeattempt extends JPanel implements KeyListener, ActionListener {
    private final int GRID_SIZE = 20;
    private final int WIDTH = 20, HEIGHT = 20;
    private final int[] x = new int[400], y = new int[400];
    private int snakeLength = 3;
    private int foodX, foodY;
    private boolean running = true, left = false, right = true, up = false, down = false;
    private Timer timer;
public nokiasnakeattempt(int delay) {
    setPreferredSize(new Dimension(WIDTH * GRID_SIZE, HEIGHT * GRID_SIZE));
    setBackground(Color.BLACK);
    setFocusable(true);
    addKeyListener(this);
    spawnFood();
    timer = new Timer(delay, this); // speed depends on delay passed
    timer.start();
}
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            // Food
            g.setColor(Color.GREEN);
            g.fillRect(foodX * GRID_SIZE, foodY * GRID_SIZE, GRID_SIZE, GRID_SIZE);

            // Snake
            g.setColor(Color.BLUE);
            for (int i = 0; i < snakeLength; i++) {
                g.fillRect(x[i] * GRID_SIZE, y[i] * GRID_SIZE, GRID_SIZE, GRID_SIZE);
            }
        } else {
            // Game Over
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", 25, getHeight() / 2);
        }
    }

    private void move() {
        for (int i = snakeLength; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (left) x[0]--;
        if (right) x[0]++;
        if (up) y[0]--;
        if (down) y[0]++;

        if (x[0] >= WIDTH) x[0] = 0;
        if (x[0] < 0) x[0] = WIDTH - 1;
        if (y[0] >= HEIGHT) y[0] = 0;
        if (y[0] < 0) y[0] = HEIGHT - 1;
    }

    private void checkCollision() {
        for (int i = 1; i < snakeLength; i++) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                timer.stop();
            }
        }
    }

    private void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            snakeLength++;
            spawnFood();
        }
    }

    private void spawnFood() {
        Random rand = new Random();
        foodX = rand.nextInt(WIDTH);
        foodY = rand.nextInt(HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollision();
            checkFood();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && !right) {
            left = true; right = up = down = false;
        }
        if (key == KeyEvent.VK_RIGHT && !left) {
            right = true; left = up = down = false;
        }
        if (key == KeyEvent.VK_UP && !down) {
            up = true; left = right = down = false;
        }
        if (key == KeyEvent.VK_DOWN && !up) {
            down = true; left = right = up = false;
        }
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0); // Exit game on ESC
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
    String[] options = { "Slow", "Normal", "Fast" };
    int choice = JOptionPane.showOptionDialog(
        null,
        "Select Snake Speed:",
        "Speed Selector",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[1]
    );

    int delay;
    switch (choice) {
        case 0: delay = 180; break; // Slow
        case 2: delay = 60; break;  // Fast
        case 1: 
        default: delay = 120; break; // Normal or closed dialog
    }

    JFrame frame = new JFrame("Snake Game - Speed: " + options[choice]);
    nokiasnakeattempt gamePanel = new nokiasnakeattempt(delay);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.add(gamePanel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}

    }
