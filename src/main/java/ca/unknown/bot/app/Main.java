package ca.unknown.bot.app;

import ca.unknown.bot.use_cases.EventListener;
import ca.unknown.bot.use_cases.GameInteractor;
import ca.unknown.bot.use_cases.RecipeInteractor;
import ca.unknown.bot.use_cases.TimerInteractor;
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
        jda.addEventListener(new RecipeInteractor());
        jda.addEventListener(new TimerInteractor());

        // Adds commands to the bot instance.
        jda.updateCommands().addCommands(
                Commands.slash("rock-paper-scissors", "Starts a game of rock paper scissors.")
                        .addOptions(new OptionData(OptionType.STRING, "choice", "Rock, paper, or scissors.")
                                .addChoice("Rock", "rock")
                                .addChoice("Paper", "paper")
                                .addChoice("Scissors", "scissors")),
                Commands.slash("trivia", "Starts a game of trivia."),
                Commands.slash("timer_create", "Creates a new timer preset")
                        .addOption(OptionType.NUMBER, "work", "how long a work session should be")
                        .addOption(OptionType.NUMBER, "break", "how long a break should be")
                        .addOption(OptionType.INTEGER, "iteration", "how many times you want a cycle to repeat")
                        .addOption(OptionType.STRING, "name", "the name of the timer"),
                Commands.slash("timer_start", "Initiates a Timer")
                        .addOption(OptionType.STRING, "name", "the name of the timer instance"),
                Commands.slash("timer_cancel", "Cancels ongoing timer"),
                Commands.slash("find-recipes", "Suggests recipes based on the name of a food.")
                        .addOption(OptionType.STRING, "food", "Enter the name of a food.", true)
                        .addOption(OptionType.INTEGER, "count", "Enter an integer", true)
                ).queue();