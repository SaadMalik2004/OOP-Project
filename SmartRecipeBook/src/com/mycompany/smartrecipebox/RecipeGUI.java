package com.mycompany.smartrecipebox;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RecipeGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea outputArea;

    private ArrayList<Recipe> recipeList = new ArrayList<>();

    public RecipeGUI() {
        setTitle("Smart Recipe Box");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createMenuPanel(), "Menu");
        mainPanel.add(createAddPanel(), "Add");
        mainPanel.add(createViewPanel(), "View");

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(240, 248, 255)); // light background

        JLabel heading = new JLabel("Smart Recipe Box");
        heading.setFont(new Font("Times New Roman", Font.BOLD, 26));
        heading.setForeground(Color.DARK_GRAY);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        usernameField = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if (user.equals("admin") && pass.equals("admin")) {
                cardLayout.show(mainPanel, "Menu");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginPanel.add(heading, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        loginPanel.add(userLabel, gbc); gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        loginPanel.add(passLabel, gbc); gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        loginPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        return loginPanel;
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(250, 250, 240)); // light background

        JLabel label = new JLabel("Smart Recipe Box - Main Menu");
        label.setFont(new Font("Times New Roman", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addButton = new JButton("Add Recipe");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(e -> cardLayout.show(mainPanel, "Add"));

        JButton viewButton = new JButton("View Saved Recipes");
        viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewButton.addActionListener(e -> {
            outputArea.setText(FileManager.loadRecipes());
            cardLayout.show(mainPanel, "View");
        });

        menuPanel.add(Box.createVerticalStrut(40));
        menuPanel.add(label);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(addButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(viewButton);

        menuPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
        return menuPanel;
    }

    private JPanel createAddPanel() {
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
        addPanel.setBackground(new Color(245, 255, 250));

        JTextField nameField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField specialField = new JTextField();
        JTextField ingredientsField = new JTextField();
        JTextArea instructionsArea = new JTextArea(3, 20);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        JButton saveButton = new JButton("Save Recipe");
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String type = typeField.getText().trim();
            String special = specialField.getText().trim();
            String[] ingredients = ingredientsField.getText().split(",");
            String instructions = instructionsArea.getText();

            Recipe r;
            if (type.equalsIgnoreCase("Dessert")) {
                boolean isSweet = special.equalsIgnoreCase("yes");
                r = new Dessert(name, ingredients, instructions, isSweet);
            } else if (type.equalsIgnoreCase("Cuisine")) {
                r = new Cuisine(name, ingredients, instructions, special);
            } else {
                JOptionPane.showMessageDialog(this, "Unknown type. Use 'Dessert' or 'Cuisine'.");
                return;
            }

            recipeList.add(r);
            FileManager.saveRecipe(r);
            JOptionPane.showMessageDialog(this, "Recipe saved!");
            nameField.setText("");
            typeField.setText("");
            specialField.setText("");
            ingredientsField.setText("");
            instructionsArea.setText("");
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        addPanel.add(new JLabel("Recipe Name:"));
        addPanel.add(nameField);
        addPanel.add(new JLabel("Type (Dessert/Cuisine):"));
        addPanel.add(typeField);
        addPanel.add(new JLabel("Is Sweet (yes/no) for Dessert OR Region for Cuisine:"));
        addPanel.add(specialField);
        addPanel.add(new JLabel("Ingredients (comma separated):"));
        addPanel.add(ingredientsField);
        addPanel.add(new JLabel("Instructions:"));
        addPanel.add(new JScrollPane(instructionsArea));
        addPanel.add(saveButton);
        addPanel.add(backButton);

        addPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        return addPanel;
    }

    private JPanel createViewPanel() {
        JPanel viewPanel = new JPanel(new BorderLayout());
        viewPanel.setBackground(new Color(255, 248, 220));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        viewPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        viewPanel.add(backButton, BorderLayout.SOUTH);

        return viewPanel;
    }
}
