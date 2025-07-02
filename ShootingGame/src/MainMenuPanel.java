import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private GameFrame frame;
    
    public MainMenuPanel(GameFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> frame.startGame());
        
        JLabel title = new JLabel("SPACE SHOOTER");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);
        
        gbc.gridy = 1;
        add(startButton, gbc);
    }
}