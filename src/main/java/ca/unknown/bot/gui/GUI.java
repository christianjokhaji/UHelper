package ca.unknown.bot.gui;

import javax.swing.*;

public class GUI extends JFrame {
    private final JPanel main = new JPanel();

    GUI() {
        createUIComponents();
        setContentPane(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createUIComponents() {
        // Create JPanels.
        JPanel discordAPI = new JPanel();
        JPanel mealPrepID = new JPanel();
        JPanel mealPrepKey = new JPanel();
        JPanel start = new JPanel();

        // Initialize main and add subsections.
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(discordAPI);
        main.add(mealPrepID);
        main.add(mealPrepKey);
        main.add(start);

        // Add components to each subsection.
        discordAPI.add(new JLabel("Discord API Token:"));
        discordAPI.add(new JTextField(20));
        mealPrepID.add(new JLabel("Meal Prep ID:"));
        mealPrepID.add(new JTextField(20));
        mealPrepKey.add(new JLabel("Meal Prep Key:"));
        mealPrepKey.add(new JTextField(20));
        start.add(new JButton("Start UHelper"));
    }

    public static void main(String[] args) {
        new GUI();
    }
}
