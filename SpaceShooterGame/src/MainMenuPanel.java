import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private GameFrame frame;
    
    public MainMenuPanel(GameFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(800, 600));
        setLayout(new GridBagLayout());
        
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> frame.startGame());
        
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        
        JLabel title = new JLabel("SPACE SHOOTER");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);
        
        gbc.gridy = 1;
        add(startButton, gbc);
        
        gbc.gridy = 2;
        add(exitButton, gbc);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}