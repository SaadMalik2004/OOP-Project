import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject {
    private Random random = new Random();
    
    public Enemy(int x, int y) {
        super(x, y, 40, 40, 2);
    }
    
    @Override
    public void update() {
        y += speed;
        if (random.nextInt(100) < 5) {
            x += random.nextInt(5) - 2;
        }
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}