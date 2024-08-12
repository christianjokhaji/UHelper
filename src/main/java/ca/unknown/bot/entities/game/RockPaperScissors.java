package ca.unknown.bot.entities.game;

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
     * @param choice represents the user's move.
     */
    @Override
    public String startGame(String choice) {
        // Generate a random integer between 0 (inclusive) and 3 (exclusive).
        Random rand = new Random();
        int number = rand.nextInt(3);
        String move;

        // The bot selects a move.
        if (number == 0) {
            move = "rock";
        }
        else if (number == 1) {
            move = "paper";
        }
        else {
            move = "scissors";
        }

        // Winner is determined.
        return determineWinner(choice, move);
    }

    /**
     * @param choice represents the user's move.
     * @param move represents the bot's move.
     * @return represents a statement determining the winner.
     */
    private String determineWinner(String choice, String move) {
        switch (choice) {
            case "rock":
                if (move.equals("rock")) {
                    return "The bot chose " + move + ". The game is a tie.";
                } else if (move.equals("paper")) {
                    return "The bot chose " + move + ". You lose.";
                } else {
                    return "The bot chose " + move + ". You win!";
                }
            case "paper":
                if (move.equals("rock")) {
                    return "The bot chose " + move + ". You win!";
                } else if (move.equals("paper")) {
                    return "The bot chose " + move + ". The game is a tie.";
                } else {
                    return "The bot chose " + move + ". You lose.";
                }
            case "scissors":
                if (move.equals("rock")) {
                    return "The bot chose " + move + ". You lose.";
                } else if (move.equals("paper")) {
                    return "The bot chose " + move + ". You win!";
                } else {
                    return "The bot chose " + move + ". The game is a tie.";
                }
            default:
                return "Invalid option selected.";
        }
    }
}