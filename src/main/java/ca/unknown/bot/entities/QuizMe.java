package ca.unknown.bot.entities;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents the QuizMe entity.
 */
public class QuizMe {

    private final Map<String, String> notes;

    public QuizMe() {
        this.notes = new HashMap<>();
    }

    /**
     * Resets all notes (questions and answers).
     */
    public void resetNotes(SlashCommandInteractionEvent event) {
        notes.clear();
        event.reply("All notes have been reset.").queue();
    }

    /**
     * Adds a new question.
     *
     * @param event represents a SlashCommandInteraction event.
     */
    public void addQuestion(SlashCommandInteractionEvent event) {
        String question = event.getOption("question").getAsString();
        notes.put(question, "");
        event.reply("Question added: " + question).queue();
    }

    /**
     * Adds an answer for a specified question.
     */
    public void addAnswer(SlashCommandInteractionEvent event) {
        String question = event.getOption("question").getAsString();
        String answer = event.getOption("answer").getAsString();

        if (notes.containsKey(question)) {
            notes.put(question, answer);
            event.reply("Answer added for question: " + question).queue();
        } else {
            event.reply("Question not found: " + question).queue();
        }
    }

    /**
     * Conducts a study session by asking the questions back to the user.
     */
    public void study(SlashCommandInteractionEvent event) {
        if (notes.isEmpty()) {
            event.reply("There are no questions to study.").queue();
            return;
        }

        StringBuilder questions = new StringBuilder();
        for (String question : notes.keySet()) {
            questions.append("Question: ").append(question).append("\n");
        }

        event.reply("Let's study! Here are your questions:\n" + questions.toString()).queue();
        // TODO ad checking once im act taking inpts
    }
}
