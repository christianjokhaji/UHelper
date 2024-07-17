package ca.unknown.bot.entities;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizMe {
    final Map<String, String> notes;
    final Map<String, String> hints;
    final List<String> questionsOrder;

    // Constructs a QuizMe object
    public QuizMe() {
        this.notes = new HashMap<>();
        this.hints = new HashMap<>();
        this.questionsOrder = new ArrayList<>();
    }

     // Getters for files

    public Map<String, String> getNotes() {
        return notes;
    }

    public Map<String, String> getHints() {
        return hints;
    }

    public List<String> getQuestionsOrder() {
        return questionsOrder;
    }

     // Resets all notes (questions and answers).

    public void resetNotes(@NotNull SlashCommandInteractionEvent event) {
        notes.clear();
        hints.clear();
        questionsOrder.clear();
        event.reply("Notes Reset!").queue();
    }

    /**
     * Adds a new question, its answer, and an optional hint.
     *
     * @param question The question to be added.
     * @param answer The answer to be added.
     * @param hint The hint to be added (optional).
     */
    public void addQuestionAndAnswer(String question, String answer, String hint) {
        notes.put(question, answer);
        hints.put(question, hint);
        if (!questionsOrder.contains(question)) {
            questionsOrder.add(question);
            System.out.println("Successfully added question " );
        } else {
            System.out.println("Question already exists");
        }

    }

    /**
     * Starts a study session by asking the first question then continuing when user presses button.
     *
     * @param event The SlashCommandInteractionEvent triggering this method.
     */
    public void study(SlashCommandInteractionEvent event) {
        if (notes.isEmpty()) {
            event.reply("There are no questions to study.").queue();
            return;
        }

        String firstQuestion = questionsOrder.get(0);
        Button answerButton = Button.primary("answer_" + firstQuestion + "_0", "Show Answer");
        Button hintButton = Button.secondary("hint_" + firstQuestion + "_0", "Show Hint");
        event.reply("Question: " + firstQuestion)
                .addActionRow(answerButton, hintButton)
                .queue();
    }


     // Shows the answer to a question and provides a button to go to the next question.

    public void showAnswer(ButtonInteractionEvent event, String question, int currentIndex) {
        String answer = notes.get(question);

        if (answer != null) {
            Button nextQuestionButton = Button.success("next_" + currentIndex, "Next Question");
            event.reply("Answer: " + answer)
                    .addActionRow(nextQuestionButton)
                    .queue();
        } else {
            event.reply("No answer found for this question.").queue();
        }
    }


     // Shows the hint for a question.

    public void showHint(ButtonInteractionEvent event, String question, int currentIndex) {

        String hint = hints.get(question);
        if (hint != null && !hint.isEmpty()) event.reply("Hint: " + hint).queue();
        else {
            event.reply("No hint available for this question.").queue();
        }
    }


     // Shows the next question in the order.

    public void showNextQuestion(ButtonInteractionEvent event, int currentIndex) {
        int nextIndex = currentIndex + 1;
        if (nextIndex < questionsOrder.size()) {
            String nextQuestion = questionsOrder.get(nextIndex);
            Button nextAnswerButton = Button.primary("answer_" + nextQuestion + "_" + nextIndex, "Show Answer");
            Button nextHintButton = Button.secondary("hint_" + nextQuestion + "_" + nextIndex, "Show Hint");
            event.getChannel().sendMessage("Question: " + nextQuestion)
                    .setActionRow(nextAnswerButton, nextHintButton)
                    .queue();
        } else {
            event.reply("No more questions available.").queue();
        }
    }
}
