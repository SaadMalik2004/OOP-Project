import javax.swing.*;
import java.awt.*;

public class login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Page");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(30, 30, 200, 200);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);
        frame.add(panel);

        JLabel userName = new JLabel("User Name:");
        userName.setBounds(10,20,80,25);
        panel.add(userName);

        JTextField userText=new JTextField();
        userText.setBounds(110,20,150,20);
        frame.add(userText);

        JLabel password=new JLabel("Password:");
        password.setBounds(300, 30, 300, 35);
        frame.add(password);

        JTextField passText=new JTextField();
        passText.setBounds(310,30,350,30);
        frame.add(passText);


        frame.setVisible(true);
    }
}