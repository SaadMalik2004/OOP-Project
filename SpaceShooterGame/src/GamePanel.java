import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private GameFrame frame;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Random random = new Random();
    private Timer timer;
    private int score = 0;
    private int enemySpawnDelay = 0;

    // Key bindings constants
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String SHOOT = "shoot";

    public GamePanel(GameFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        // Initialize player
        player = new Player(375, 500);

        // Set up key bindings (more reliable than KeyListener)
        setupKeyBindings();

        // Start game loop
        timer = new Timer(16, this);
        timer.start();

        // Ensure panel can receive focus
        setFocusable(true);
        requestFocusInWindow();
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // Movement controls
        inputMap.put(KeyStroke.getKeyStroke("UP"), UP);
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), DOWN);
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), LEFT);
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), RIGHT);

        // Shooting control
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), SHOOT);

        // Movement actions
        actionMap.put(UP, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                player.setUp(true);
            }
        });

        actionMap.put(DOWN, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                player.setDown(true);
            }
        });

        actionMap.put(LEFT, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                player.setLeft(true);
            }
        });

        actionMap.put(RIGHT, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                player.setRight(true);
            }
        });

        // Release actions
        inputMap.put(KeyStroke.getKeyStroke("released UP"), "release_up");
        actionMap.put("release_up", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                player.setUp(false);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("released DOWN"), "release_down");
        actionMap.put("release_down", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                player.setDown(false);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("released LEFT"), "release_left");
        actionMap.put("release_left", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                player.setLeft(false);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("released RIGHT"), "release_right");
        actionMap.put("release_right", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                player.setRight(false);
            }
        });

        // Shooting action
        actionMap.put(SHOOT, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                bullets.add(player.shoot());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw player
        player.draw(g);

        // Draw enemies
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        // Draw bullets
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        // Draw HUD
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("Health: " + player.getHealth(), 20, 60);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update game state
        player.update();

        // Spawn enemies
        if (enemySpawnDelay <= 0) {
            enemies.add(new Enemy(random.nextInt(750), -40));
            enemySpawnDelay = 60;
        } else {
            enemySpawnDelay--;
        }

        // Update bullets
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update();

            if (bullet.isOffScreen()) {
                bullets.remove(i);
                continue;
            }

            // Check bullet-enemy collisions
            for (int j = enemies.size() - 1; j >= 0; j--) {
                Enemy enemy = enemies.get(j);
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    enemies.remove(j);
                    bullets.remove(i);
                    score += 10;
                    break;
                }
            }
        }

        // Update enemies and check player collisions
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.update();

            if (enemy.getY() > 600) {
                enemies.remove(i);
                continue;
            }

            if (enemy.getBounds().intersects(player.getBounds())) {
                player.takeDamage(10);
                enemies.remove(i);

                if (player.getHealth() <= 0) {
                    timer.stop();
                    frame.gameOver(score);
                    return;
                }
            }
        }

        repaint();
    }

    // This ensures focus is maintained when panel is shown
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}