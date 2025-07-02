import java.awt.*;

public abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected int speed;
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public GameObject(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }
    
    public abstract void update();
    public abstract void draw(Graphics g);
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}