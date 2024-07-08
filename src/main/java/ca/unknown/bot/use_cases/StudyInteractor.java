package ca.unknown.bot.use_cases;

import ca.unknown.bot.entities.QuizMe;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.Objects;

public class StudyInteractor extends ListenerAdapter {

    private final QuizMe quizMe = new QuizMe();

    /**
     * Handles different study-related commands.
     *
     * @param event represents a SlashCommandInteraction event.
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        // Check what study option the user wants to use
        String option = Objects.requireNonNull(event.getOption("choice")).getAsString();

        switch (option) {
            case "resetnotes":
                quizMe.resetNotes(event);
                break;
            case "addquestion":
                handleContinuousAddQuestion(event);
                break;
            case "study":
                quizMe.study(event);
                break;
            default:
                event.reply("Invalid command.").queue();
                break;
        }
    }

    // Class to allow user to add multiple questions without having to call the bot again.
    private void handleContinuousAddQuestion(SlashCommandInteractionEvent event) {
        showAddQuestionModal(event, true);
    }


    // Displays inputs for user to add question, answer and hint
    private Modal createAddQuestionModal(boolean firstTime) {


        TextInput questionInput = TextInput.create("question", "Enter your question", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Type your question here...")
                .build();

        TextInput answerInput = TextInput.create("answer", "Enter the answer", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Type the answer here...")
                .build();

        TextInput hintInput = TextInput.create("hint", "Enter a hint (optional)", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Type the hint here...")
                .build();

        return Modal.create("input-modal", firstTime ? "Add Question and Answer" : "Add Another Question")
                .addActionRows(ActionRow.of(questionInput), ActionRow.of(answerInput), ActionRow.of(hintInput))
                .build();
    }

    // This is for the initial add question request
    private void showAddQuestionModal(SlashCommandInteractionEvent event) {
        Modal modal = createAddQuestionModal(true);
        event.replyModal(modal).queue();
    }

    // This is for subsequent requests, where a button is used
    private void showAddQuestionModal(ButtonInteractionEvent event, boolean firstTime) {
        Modal modal = createAddQuestionModal(false);
        event.replyModal(modal).queue();
    }

    // Adds question with answer and hint once inputs are made, then asks user if they would like to add more
    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("input-modal")) {
            String question = event.getValue("question").getAsString();
            String answer = event.getValue("answer").getAsString();
            String hint = event.getValue("hint") != null ? event.getValue("hint").getAsString() : "";

            quizMe.addQuestionAndAnswer(question, answer, hint);
            event.reply("Question and answer added:\nQuestion: " + question + "\nAnswer: " + answer + "\nHint: " + hint)
                    .addActionRow(Button.primary("add_another", "Add Another Question"), Button.secondary("done", "Done"))
                    .queue();
        }
    }


     // Controls button interactions


    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();
        if (buttonId != null) {
            if (buttonId.equals("add_another")) {
                showAddQuestionModal(event, false);
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
}
