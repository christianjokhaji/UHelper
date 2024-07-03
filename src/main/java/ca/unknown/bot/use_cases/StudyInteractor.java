package ca.unknown.bot.use_cases;

import ca.unknown.bot.entities.QuizMe;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * A use-case interactor for study commands.
 */
public class StudyInteractor extends ListenerAdapter {

    /**
     * Handles different study-related commands.
     *
     * @param event represents a SlashCommandInteraction event.
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        QuizMe quizMe = new QuizMe();

        switch (event.getName()) {
            case "resetnotes":
                quizMe.resetNotes(event);
                break;
            case "addquestion":
                quizMe.addQuestion(event);
                break;
            case "addanswer":
                quizMe.addAnswer(event);
                break;
            case "study":
                quizMe.study(event);
                break;
            default:
                event.reply("Invalid command.").queue();
                break;
        }
    }
}
