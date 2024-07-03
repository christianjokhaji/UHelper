package ca.unknown.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import ca.unknown.entities.Pomodoro;

import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;

public class EventListener extends ListenerAdapter
{
    /**
     * A simple event listener template for testing purposes
     * Returns "Pong!" if the message content is "!ping"
     *
     * @param event a MessageReceivedEvent
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourselves
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("!ping"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
         if (event.getName().equals("timer")) {
             Pomodoro test = new Pomodoro(25, 5, 3, "lol");
             Timer timer = new Timer();
             TimerTask task = new TimerTask() {
                 public void run() {
                    event.reply("testing").queue();
            }
        };
             timer.scheduleAtFixedRate(task, 0, 3000);
         }
    }
}
