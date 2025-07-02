import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class GameOverPanel extends JPanel {
    private GameFrame frame;
    private int score;

    public GameOverPanel(GameFrame frame, int score) {
        this.frame = frame;
        this.score = score;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        saveHighScore();

        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(Color.WHITE);

        JLabel scoreLabel = new JLabel("Your Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setForeground(Color.WHITE);

        JButton restartButton = new JButton("Play Again");
        restartButton.addActionListener(e -> frame.startGame());

        JButton menuButton = new JButton("Main Menu");
        menuButton.addActionListener(e -> frame.showMainMenu());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
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

            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();
                    if (line != null && !line.isEmpty()) {
                        highScore = Integer.parseInt(line);
                    }
                }
            }

            if (score > highScore) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(String.valueOf(score));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error saving high score: " + e.getMessage());
        }
    }
}