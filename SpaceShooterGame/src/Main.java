import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
        });
    }
}

class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("Space Shooter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Start with main menu
        switchToMenu();
        
        pack();
        setLocationRelativeTo(null);
    }
    
    public void switchToMenu() {
        getContentPane().removeAll();
        add(new MainMenuPanel(this));
        revalidate();
        repaint();
    }
    
    public void startGame() {
        getContentPane().removeAll();
        add(new GamePanel(this));
        revalidate();
        repaint();
    }
    
    public void gameOver(int score) {
        getContentPane().removeAll();
        add(new GameOverPanel(this, score));
        revalidate();
        repaint();
    }
}