package ca.unknown.bot.use_cases.quiz_me;

import ca.unknown.bot.entities.quiz_me.QuizMe;
import ca.unknown.bot.use_cases.utils.ModalUtils;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.Objects;

public class AddQuestionInteractor {

    private final QuizMe quizMe;

    public AddQuestionInteractor(QuizMe quizMe) {
        this.quizMe = quizMe;
    }

    /**
     * Class to allow user to add multiple questions without having to call the bot repeatedly.
     *
     * @param event represents a SlashCommandInteraction event.
     */
    public void handleContinuousAddQuestion(SlashCommandInteractionEvent event) {
        showAddQuestionModal(event);
    }

    /**
     * Adds question with answer and hint then asks user if they want to input more
     *
     * @param event represents a ModalInteraction event.
     */
    public void handleModalInteraction(ModalInteractionEvent event) {
        String question = Objects.requireNonNull(event.getValue("question")).getAsString();
        String answer = Objects.requireNonNull(event.getValue("answer")).getAsString();
        String hint = event.getValue("hint") != null ?
                Objects.requireNonNull(event.getValue("hint")).getAsString() : "";
        quizMe.addQuestionAndAnswer(question, answer, hint);
        event.reply("Question and answer added:\nQuestion: " + question + "\nAnswer: " + answer + "\nHint: " + hint)
                .addActionRow(Button.primary("add_another", "Add Another Question"), Button.secondary("done", "Done"))
                .queue();
    }

    /**
     * Handles button interactions for adding questions
     *
     * @param event represents a ButtonInteraction event.
     */
    public void handleButtonInteraction(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();
        if (buttonId != null) {
            if (buttonId.equals("add_another")) {
                showAddQuestionModal(event);
            } else if (buttonId.equals("done")) {
                event.reply("Finished adding questions.").queue();
            } else if (buttonId.startsWith("answer_")) {
                String[] parts = buttonId.split("_", 3);
                String question = parts[1];
                int currentIndex = Integer.parseInt(parts[2]);
                quizMe.showAnswer(event, question, currentIndex);
            } else if (buttonId.startsWith("hint_")) {
                String[] parts = buttonId.split("_", 3);
                String question = parts[1];
                int currentIndex = Integer.parseInt(parts[2]);
                quizMe.showHint(event, question, currentIndex);
            } else if (buttonId.startsWith("next_")) {
                int currentIndex = Integer.parseInt(buttonId.split("_")[1]);
                quizMe.showNextQuestion(event, currentIndex);
            }
        }
    }

    /**
     * Displays inputs for user to add question
     *
     * @param event     represents a SlashCommandInteraction event
     */
    private void showAddQuestionModal(SlashCommandInteractionEvent event) {
        event.replyModal(ModalUtils.createAddQuestionModal(true)).queue();
    }

    /**
     * Displays inputs for user to add question, answer, and hint.
     *
     * @param event     represents a ButtonInteraction event.
     */
    private void showAddQuestionModal(ButtonInteractionEvent event) {
        event.replyModal(ModalUtils.createAddQuestionModal(false)).queue();
    }
}
