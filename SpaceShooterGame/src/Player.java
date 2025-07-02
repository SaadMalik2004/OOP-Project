import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
    private boolean left, right, up, down;
    private int health;

    public Player(int x, int y) {
        super(x, y, 50, 50, 5);
        this.health = 100;
    }

    // Add these setter methods for key bindings
    public void setUp(boolean up) { this.up = up; }
    public void setDown(boolean down) { this.down = down; }
    public void setLeft(boolean left) { this.left = left; }
    public void setRight(boolean right) { this.right = right; }

    // Remove the old keyPressed/keyReleased methods since we're using key bindings now

    @Override
    public void update() {
        if (left) x -= speed;
        if (right) x += speed;
        if (up) y -= speed;
        if (down) y += speed;

        // Boundary checking
        x = Math.max(0, Math.min(x, 750 - width));
        y = Math.max(0, Math.min(y, 550 - height));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public Bullet shoot() {
        return new Bullet(x + width/2 - 2, y, 4, 10, 7);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public int getHealth() { return health; }
}