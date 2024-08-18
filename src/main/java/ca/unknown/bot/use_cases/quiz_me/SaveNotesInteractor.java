package ca.unknown.bot.use_cases.quiz_me;

import ca.unknown.bot.data_access.quiz_me.JSONQuizMeRepository;
import ca.unknown.bot.entities.quiz_me.QuizMe;
import ca.unknown.bot.use_cases.utils.ModalUtils;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

/**
 * Interactor for saving and displaying save notes option
 */
public class SaveNotesInteractor {

    private final QuizMe quizMe;
    private final JSONQuizMeRepository repository = new JSONQuizMeRepository();

    public SaveNotesInteractor(QuizMe quizMe) {
        this.quizMe = quizMe;
    }

    /**
     * Prompts the user to save notes.
     *
     * @param event represents a SlashCommandInteraction event.
     */
    public void promptSaveNotes(SlashCommandInteractionEvent event) {
        event.replyModal(ModalUtils.createSaveNotesModal()).queue();
    }

    /**
     * Saves notes to a specified file.
     *
     * @param event represents a ModalInteraction event.
     * @param filename the name of the file to save notes to.
     */
    public void saveNotesToFile(ModalInteractionEvent event, String filename) {
        repository.saveQuizMe(quizMe, filename + ".json");
        event.reply("Notes saved to " + filename + ".json").queue();
    }
}
