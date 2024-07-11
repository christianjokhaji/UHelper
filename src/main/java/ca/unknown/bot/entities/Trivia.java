package ca.unknown.bot.entities;

import ca.unknown.bot.use_cases.TriviaListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * A subclass of Game that represents a game of trivia.
 */
public class Trivia extends Game {
    public String question;
    public String answer;

    /**
     * The Trivia constructor method.
     *
     * @param question represents the trivia question.
     * @param answer represents the answer to the trivia question.
     */
    public Trivia(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Starts a game of trivia.
     *
     * @param event represents a SlashCommandInteraction event.
     */
    @Override
    public void startGame(SlashCommandInteractionEvent event) {
        // Retrieve the current JDA instance.
        JDA jda = event.getJDA();
        // Register a TriviaListener which holds the correct answer.
        jda.addEventListener(new TriviaListener(answer));
        // Bot poses the question.
        event.reply(question).queue();
    }
}