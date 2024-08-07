package ca.unknown.bot.use_cases.utils;

import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class ModalUtils {

    /**
     * Creates a modal for adding questions as it looks nice
     *
     * @param firstTime Indicates if this is the first time the modal is being shown.
     * @return the created modal.
     */
    public static Modal createAddQuestionModal(boolean firstTime) {
        TextInput questionInput = TextInput.create("question", "Enter your question", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Type your question here...")
                .build();

        TextInput answerInput = TextInput.create("answer", "Enter the answer", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Type the answer here...")
                .build();

        TextInput hintInput = TextInput.create("hint", "Enter a hint", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Type the hint here...")
                .build();

        return Modal.create("input-modal", firstTime ? "Add Question and Answer" : "Add Another Question")
                .addActionRows(ActionRow.of(questionInput), ActionRow.of(answerInput), ActionRow.of(hintInput))
                .build();
    }

    /**
     * Creates a modal for saving notes.
     *
     * @return the created modal.
     */
    public static Modal createSaveNotesModal() {
        TextInput filenameInput = TextInput.create("filename", "Enter a filename", TextInputStyle.SHORT)
                .setPlaceholder("Type the filename here...")
                .build();

        return Modal.create("save-notes-modal", "Save Notes")
                .addActionRows(ActionRow.of(filenameInput))
                .build();
    }

    public static Modal createAnswerQuestionModal(String question, int currentIndex) {
        TextInput answerInput = TextInput.create("user_answer", "Your Answer", TextInputStyle.SHORT)
                .setRequired(true)
                .build();

        return Modal.create("answer_question_" + question + "_" + currentIndex, "Answer the Question")
                .addActionRow(answerInput)
                .build();
    }
}
