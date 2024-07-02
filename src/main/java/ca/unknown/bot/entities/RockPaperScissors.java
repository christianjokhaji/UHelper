package ca.unknown.bot.entities;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;
import java.util.Random;

/**
 * A subclass of Game that represents a rock paper scissors game.
 */
public class RockPaperScissors extends Game {

    /**
     * Starts a game of rock paper scissors.
     *
     * @param event represents a SlashCommandInteraction event.
     */
    public void startGame(SlashCommandInteractionEvent event) {
        // Generate a random integer between 0 (inclusive) and 3 (exclusive).
        Random rand = new Random();
        int number = rand.nextInt(3);
        String option = Objects.requireNonNull(event.getOption("choice")).getAsString();
        String choice;

        // The bot selects a move.
        if (number == 0) {
            choice = "rock";
        }
        else if (number == 1) {
            choice = "paper";
        }
        else {
            choice = "scissors";
        }

        // Winner is determined.
        switch (option) {
            case "rock":
                if (choice.equals("rock")) {
                    event.reply("The bot chose " + choice + ". The game is a tie.").queue();
                } else if (choice.equals("paper")) {
                    event.reply("The bot chose " + choice + ". You lose.").queue();
                } else {
                    event.reply("The bot chose " + choice + ". You win!").queue();
                }
                break;
            case "paper":
                if (choice.equals("rock")) {
                    event.reply("The bot chose " + choice + ". You win!").queue();
                } else if (choice.equals("paper")) {
                    event.reply("The bot chose " + choice + ". The game is a tie.").queue();
                } else {
                    event.reply("The bot chose " + choice + ". You lose.").queue();
                }
                break;
            case "scissors":
                if (choice.equals("rock")) {
                    event.reply("The bot chose " + choice + ". You lose.").queue();
                } else if (choice.equals("paper")) {
                    event.reply("The bot chose " + choice + ". You win!").queue();
                } else {
                    event.reply("The bot chose " + choice + ". The game is a tie.").queue();
                }
                break;
            default:
                event.reply("Invalid option selected.").queue();
                break;
        }
    }
}