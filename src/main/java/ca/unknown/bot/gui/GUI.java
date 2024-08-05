package ca.unknown.bot.gui;

import ca.unknown.bot.app.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class GUI extends JFrame {
    private final JPanel main = new JPanel();
    private final JPanel discordAPI = new JPanel();
    private final JPanel mealPrepID = new JPanel();
    private final JPanel mealPrepKey = new JPanel();
    private final JPanel start = new JPanel();
    private final JLabel discordAPIToken = new JLabel("Discord API Token:");
    private final JTextField token = new JTextField(20);
    private final JLabel devLink = new JLabel("Discord Dev Portal");
    private final JLabel mealPrepIDText = new JLabel("Meal Prep ID:");
    private final JTextField id =  new JTextField(20);
    private final JLabel edamamLink = new JLabel("Edamam Recipe Search API");
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

        // Set up FlowLayout for each subsection.
        discordAPI.setLayout(new FlowLayout(FlowLayout.LEFT));
        mealPrepID.setLayout(new FlowLayout(FlowLayout.LEFT));
        mealPrepKey.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Set labels for text fields.
        discordAPIToken.setLabelFor(token);
        mealPrepIDText.setLabelFor(id);
        mealPrepKeyText.setLabelFor(key);

        // Create hyperlinks.
        devLink.setForeground(Color.BLUE.darker());
        edamamLink.setForeground(Color.BLUE.darker());

        // Add components to each subsection.
        discordAPI.add(discordAPIToken);
        discordAPI.add(token);
        discordAPI.add(devLink);
        mealPrepID.add(mealPrepIDText);
        mealPrepID.add(id);
        mealPrepID.add(edamamLink);
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

            // Start/stop the app.
            if (Objects.equals(startButton.getText(), "Stop UHelper")) {
                // Stop
                System.exit(0);
            }
            else {
                // Start
                Main.main(new String[]{tokenText, idText, keyText});
                // Update startButton
                startButton.setText("Stop UHelper");
            }
        });

        // Add hyperlink functionality.
        devLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://discord.com/developers/applications"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                devLink.setText("<html><a href='https://discord.com/developers/applications'>" +
                        "Discord Dev Portal</a></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                devLink.setText("Discord Dev Portal");
            }
        });

        edamamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://developer.edamam.com/edamam-recipe-api"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                edamamLink.setText("<html><a href='https://developer.edamam.com/edamam-recipe-api'>" +
                        "Edamam Recipe Search API</a></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                edamamLink.setText("Edamam Recipe Search API");
            }
        });

        start.add(startButton);
    }

    public static void main(String[] args) {
        new GUI();
    }
}
