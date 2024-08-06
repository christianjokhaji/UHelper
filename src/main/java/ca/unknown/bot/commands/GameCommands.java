package ca.unknown.bot.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class GameCommands {
    public static SlashCommandData getRockPaperScissorsCommand() {
        return Commands.slash("rock-paper-scissors", "Starts a game of rock paper scissors.")
                .addOptions(new OptionData(OptionType.STRING, "choice", "Rock, paper, or scissors.")
                        .addChoice("Rock", "rock")
                        .addChoice("Paper", "paper")
                        .addChoice("Scissors", "scissors"));
    }

    public static SlashCommandData getTriviaCommand() {
        return Commands.slash("trivia", "Starts a game of trivia.");
    }

    public static SlashCommandData get8BallCommand() {
        return Commands.slash("8ball", "Receive a Magic 8Ball reading.")
                .addOption(OptionType.STRING, "question", "What is the question?");
    }
}
