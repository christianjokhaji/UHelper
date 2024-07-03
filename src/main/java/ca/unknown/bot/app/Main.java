package ca.unknown.bot.app;

import ca.unknown.bot.use_cases.EventListener;
import ca.unknown.bot.use_cases.GameInteractor;
import ca.unknown.bot.use_cases.StudyInteractor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    /**
     * Main entryway for the program. It serves as a factory for building the bot's functionality.
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

        // Adds use-case interactors to the bot instance.
        jda.addEventListener(new GameInteractor());
        jda.addEventListener(new StudyInteractor());
        // Adds commands to the bot instance.
        jda.updateCommands().addCommands(Commands.slash("rock-paper-scissors",
                "Starts a game of rock paper scissors.")
                .addOptions(new OptionData(OptionType.STRING, "choice", "Rock, paper, or scissors.")
                        .addChoice("Rock", "rock")
                        .addChoice("Paper", "paper")
                        .addChoice("Scissors", "scissors"))).queue();

        jda.updateCommands().addCommands(Commands.slash("study",
                        "Starts a study session")
                .addOptions(new OptionData(OptionType.STRING, "choice", "How can we help you study")
                        .addChoice("Reset Notes", "resetnotes")
                        .addChoice("Add Question", "addquestion")
                        .addChoice("Add Answer", "addanswer")
                        .addChoice("Study", "study"))).queue();
    }
}