import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel {
    private GameFrame frame;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Timer gameTimer;
    private int score = 0;
    private int enemySpawnDelay = 0;
    private Random random = new Random();

    public GamePanel(GameFrame frame) {
        this.frame = frame;
        setBackground(Color.BLACK);
        setFocusable(true);

        player = new Player(375, 500);
        setupKeyBindings();

        gameTimer = new Timer(16, e -> {
            updateGame();
            repaint();
        });
        gameTimer.start();
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // Movement keys
        inputMap.put(KeyStroke.getKeyStroke("UP"), "up");
        actionMap.put("up", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { player.setUp(true); }
        });

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down");
        actionMap.put("down", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { player.setDown(true); }
        });

        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left");
        actionMap.put("left", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { player.setLeft(true); }
        });

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        actionMap.put("right", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { player.setRight(true); }
        });

        // Key release actions
        inputMap.put(KeyStroke.getKeyStroke("released UP"), "release_up");
        actionMap.put("release_up", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { player.setUp(false); }
        });

        inputMap.put(KeyStroke.getKeyStroke("released DOWN"), "release_down");
        actionMap.put("release_down", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { player.setDown(false); }
        });

        inputMap.put(KeyStroke.getKeyStroke("released LEFT"), "release_left");
        actionMap.put("release_left", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { player.setLeft(false); }
        });

        inputMap.put(KeyStroke.getKeyStroke("released RIGHT"), "release_right");
        actionMap.put("release_right", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { player.setRight(false); }
        });

        // Shooting
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "shoot");
        actionMap.put("shoot", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { bullets.add(player.shoot()); }
        });
    }

    private void updateGame() {
        player.update();

        // Spawn enemies
        if (enemySpawnDelay <= 0) {
            enemies.add(new Enemy(random.nextInt(750), -40));
            enemySpawnDelay = 60;
        } else {
            enemySpawnDelay--;
        }

        // Update bullets
        for (int i = bullets.size()-1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update();

            if (bullet.isOffScreen()) {
                bullets.remove(i);
                continue;
            }

            // Check collisions
            for (int j = enemies.size()-1; j >= 0; j--) {
                Enemy enemy = enemies.get(j);
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    enemies.remove(j);
                    bullets.remove(i);
                    score += 10;
                    break;
                }
            }
        }

        // Update enemies
        for (int i = enemies.size()-1; i >= 0; i--) {
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
                    gameTimer.stop();
                    frame.gameOver(score);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        enemies.forEach(e -> e.draw(g));
        bullets.forEach(b -> b.draw(g));

        // Draw HUD
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("Health: " + player.getHealth(), 20, 60);
    }
}