import java.awt.*;

public class Bullet extends GameObject {
    public Bullet(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, speed);
    }
    
    @Override
    public void update() {
        y -= speed;
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }
    
    public boolean isOffScreen() {
        return y < 0;
    }
}