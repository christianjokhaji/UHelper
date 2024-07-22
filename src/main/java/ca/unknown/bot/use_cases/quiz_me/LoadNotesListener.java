package ca.unknown.bot.use_cases.quiz_me;

import ca.unknown.bot.entities.quiz_me.QuizMe;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;

public class LoadNotesListener extends ListenerAdapter {

    private final JDA jda;
    private final QuizMe quizMe;

    public LoadNotesListener(JDA jda, QuizMe quizMe) {
        this.jda = jda;
        this.quizMe = quizMe;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        String message = event.getMessage().getContentRaw();
        if (message.endsWith(".json")) {
            new LoadNotesInteractor(quizMe).loadNotesFromFile(message, event);
            jda.removeEventListener(this); // Unregister the listener after loading the notes
        }
    }
}
