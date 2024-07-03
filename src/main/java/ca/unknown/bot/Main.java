package ca.unknown.bot;

import ca.unknown.bot.EventListener;
import ca.unknown.entities.TimerInteractor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
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

        // Adds an event listener for timer features
        jda.addEventListener(new TimerInteractor());

        // Experimenting with onSlashCommands
        jda.updateCommands().addCommands(Commands.slash("timer",
                "Initiates a Timer"), Commands.slash("preset",
                "Creates a new timer preset")
                .addOption(OptionType.INTEGER, "work", "how long a work session should be")
                .addOption(OptionType.INTEGER, "break", "how long a break should be")
                .addOption(OptionType.INTEGER, "iteration", "how many times you want a cycle to repeat")
                .addOption(OptionType.STRING, "name", "the name of the timer")).queue();

    }
}
