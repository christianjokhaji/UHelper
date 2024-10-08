package ca.unknown.bot.use_cases.quiz_me;

import ca.unknown.bot.entities.quiz_me.QuizMe;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Primary interactor for quiz_me, calling the various methods as well as defining basic modal and button behaviour
 */
public class StudyInteractor extends ListenerAdapter {

    private final QuizMe quizMe = new QuizMe();
    private final JDA jda;

    public StudyInteractor(JDA jda) {
        this.jda = jda;
    }

    /**
     * Handles all study commands.
     *
     * @param event represents a SlashCommandInteraction event.
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if(event.getName().equals("study-help")) {
            String option = Objects.requireNonNull(event.getOption("choice")).getAsString();

            switch (option) {
                case "resetnotes":
                    quizMe.resetNotes(event);
                    break;
                case "addquestion":
                    new AddQuestionInteractor(quizMe).handleContinuousAddQuestion(event);
                    break;
                case "study":
                    quizMe.study(event);
                    break;
                case "savenotes":
                    new SaveNotesInteractor(quizMe).promptSaveNotes(event);
                    break;
                case "loadnotes":
                    new LoadNotesInteractor(quizMe).displaySavedQuizzes(event);
                    jda.addEventListener(new LoadNotesListener(jda, quizMe));
                    break;
                default:
                    event.reply("Invalid command.").queue();
                    break;
            }
        }
    }
    /**
     * Handles modal interactions.
     *
     * @param event represents a ModalInteraction event.
     */
    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("save-notes-modal")) {
            String filename = Objects.requireNonNull(event.getValue("filename")).getAsString();
            new SaveNotesInteractor(quizMe).saveNotesToFile(event, filename);
        } else if (event.getModalId().equals("input-modal")) {
            new AddQuestionInteractor(quizMe).handleModalInteraction(event);
        } else if (event.getModalId().startsWith("answer_question_")) {
            String[] parts = event.getModalId().split("_", 4);
            String question = parts[2];
            int currentIndex = Integer.parseInt(parts[3]);
            String userAnswer = Objects.requireNonNull(event.getValue("user_answer")).getAsString();
            String userId = event.getUser().getId();
            quizMe.checkAnswer(event, question, currentIndex, userAnswer, userId);
        }
    }

    /**
     * Controls button interactions.
     *
     * @param event represents a ButtonInteraction event.
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        new AddQuestionInteractor(quizMe).handleButtonInteraction(event);
    }
}