package ca.unknown.bot.gui;

import ca.unknown.bot.app.Main;

import javax.swing.*;

public class GUI extends JFrame {
    private final JPanel main = new JPanel();
    private final JPanel discordAPI = new JPanel();
    private final JPanel mealPrepID = new JPanel();
    private final JPanel mealPrepKey = new JPanel();
    private final JPanel start = new JPanel();
    private final JLabel discordAPIToken = new JLabel("Discord API Token:");
    private final JTextField token = new JTextField(20);
    private final JLabel mealPrepIDText = new JLabel("Meal Prep ID:");
    private final JTextField id =  new JTextField(20);
    private final JLabel mealPrepKeyText = new JLabel("Meal Prep Key:");
    private final JTextField key = new JTextField(20);

    GUI() {
        buildGUI();
        setContentPane(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void buildGUI() {
        // Initialize main and add subsections.
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(discordAPI);
        main.add(mealPrepID);
        main.add(mealPrepKey);
        main.add(start);

        // Set labels for text fields.
        discordAPIToken.setLabelFor(token);
        mealPrepIDText.setLabelFor(id);
        mealPrepKeyText.setLabelFor(key);

        // Add components to each subsection.
        discordAPI.add(discordAPIToken);
        discordAPI.add(token);
        mealPrepID.add(mealPrepIDText);
        mealPrepID.add(id);
        mealPrepKey.add(mealPrepKeyText);
        mealPrepKey.add(key);

        // Add start functionality.
        JButton startButton = new JButton("Start UHelper");
        startButton.addActionListener(e -> {
            // Retrieve text from text fields.
            String tokenText = token.getText();
            String idText = id.getText();
            String keyText = key.getText();

            // Set as system variables.
            System.setProperty("TOKEN", tokenText);
            System.setProperty("EDAMAM_ID", idText);
            System.setProperty("EDAMAM_KEY", keyText);

            // Start the app.
            Main.main(new String[]{tokenText, idText, keyText});
        });
        start.add(startButton);
    }

    public static void main(String[] args) {
        new GUI();
    }
}
