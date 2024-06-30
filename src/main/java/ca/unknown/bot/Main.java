package ca.unknown.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
// Min: TOKEN=MTI0OTAyNDYzMDIwODA2OTY5Mg.G_elJO.T9CQRFpB59Z8W3SgK6dt_wiDibF1rD4Xn_VOxQ
public class Main {
    /**
     * Main entryway for the program
     *
     * @param args Stores Java command-line arguments
     */
    public static void main(String[] args) {
        // Creates an instance of the bot with general permissions.
        // "TOKEN" is an environment variable.
        JDA jda = JDABuilder.createDefault(System.getenv("TOKEN"),
                GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).build();

        // Adds a simple event listener for testing purposes.
        jda.addEventListener(new EventListener());

        // Experimenting with onSlashCommands
        jda.updateCommands().addCommands(Commands.slash("timer",
                "Initiates a Timer")).queue();

        jda.updateCommands().addCommands(Commands.slash("preset",
                        "creates a new Timer preset")).queue();
    }
}
