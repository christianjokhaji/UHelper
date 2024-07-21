package ca.unknown.bot.use_cases.game;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * A use-case interactor for a trivia event listener.
 */
public class TriviaListener extends ListenerAdapter {
    public String answer;

    /**
     * The TriviaListener constructor method.
     *
     * @param answer represents the answer to the trivia question.
     */
    public TriviaListener(String answer) {
        this.answer = answer;
    }

    /**
     * Sends a message in the corresponding channel if a user chooses the correct answer.
     *
     * @param event represents a MessageReceivedEvent.
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();

        if (content.equalsIgnoreCase(answer)) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage(event.getAuthor().getGlobalName() + " has the correct answer!").queue();
            this.answer = null;
        }
    }
}
