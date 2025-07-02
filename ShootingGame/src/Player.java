import java.awt.*;

public class Player extends GameObject {
    private static final int MAX_X = 750;
    private static final int MAX_Y = 550;
    private int health;
    private boolean up, down, left, right;

    public Player(int x, int y) {
        super(x, y, 50, 50, 5);
        this.health = 100;
    }

    public void setUp(boolean up) { this.up = up; }
    public void setDown(boolean down) { this.down = down; }
    public void setLeft(boolean left) { this.left = left; }
    public void setRight(boolean right) { this.right = right; }

    @Override
    public void update() {
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;

        x = Math.max(0, Math.min(x, MAX_X));
        y = Math.max(0, Math.min(y, MAX_Y));
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
        if (damage > 0) {
            health -= damage;
        }
    }

    public int getHealth() { return health; }
}