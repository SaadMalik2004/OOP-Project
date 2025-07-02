import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GameOverPanel extends JPanel {
    private GameFrame frame;
    private int score;
    
    public GameOverPanel(GameFrame frame, int score) {
        this.frame = frame;
        this.score = score;
        setPreferredSize(new Dimension(800, 600));
        setLayout(new GridBagLayout());
        
        // Save high score
        saveHighScore();
        
        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        
        JLabel scoreLabel = new JLabel("Your Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        
        JButton restartButton = new JButton("Play Again");
        restartButton.addActionListener(e -> frame.startGame());
        
        JButton menuButton = new JButton("Main Menu");
        menuButton.addActionListener(e -> frame.switchToMenu());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(gameOverLabel, gbc);
        
        gbc.gridy = 1;
        add(scoreLabel, gbc);
        
        gbc.gridy = 2;
        add(restartButton, gbc);
        
        gbc.gridy = 3;
        add(menuButton, gbc);
    }
    
    private void saveHighScore() {
        try {
            File file = new File("highscore.txt");
            int highScore = 0;
            
            // Read existing high score
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                highScore = Integer.parseInt(reader.readLine());
                reader.close();
            }
            
            // Update if current score is higher
            if (score > highScore) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(String.valueOf(score));
                writer.close();
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error handling high score file: " + e.getMessage());
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}