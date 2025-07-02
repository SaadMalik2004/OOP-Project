import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("Space Shooter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        showMainMenu();
    }
    
    public void showMainMenu() {
        getContentPane().removeAll();
        add(new MainMenuPanel(this));
        revalidate();
    }
    
    public void startGame() {
        getContentPane().removeAll();
        add(new GamePanel(this));
        revalidate();
    }
    
    public void gameOver(int score) {
        getContentPane().removeAll();
        add(new GameOverPanel(this, score));
        revalidate();
    }
}