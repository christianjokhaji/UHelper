package ca.unknown.bot.use_cases;

import ca.unknown.bot.entities.Pomodoro;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

/**
 * A use-case interactor for timer and its features.
 */
public class TimerInteractor extends ListenerAdapter {

    /**
     * TimerInteractor allows a bot to create, initiate, and cancel a timer.
     *
     * @param event represents a SlashCommandInteraction event.
     */

//    public void onMessageReceived(MessageReceivedEvent event) {}

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        // !preset {worktime} {breaktime} {iteration} {name}
        if (event.getName().equals("preset")) {
            try {
                String name = Objects.requireNonNull(event.getOption("name")).getAsString();
                Integer workMinute = Objects.requireNonNull(event.getOption("work")).getAsInt();
                Integer breakMinute = Objects.requireNonNull(event.getOption("break")).getAsInt();
                Integer iteration = Objects.requireNonNull(event.getOption("iteration")).getAsInt();
                Pomodoro newTimer = new Pomodoro(workMinute, breakMinute,iteration, name);
                event.reply(newTimer.toString()).queue();
            }
            catch (NumberFormatException e) {
                event.reply("Exception raised: NumberFormatException").queue();
            }
            catch (ArrayIndexOutOfBoundsException e) {
                event.reply("Exception raised: ArrayIndexOutOfBoundsException").queue();
            }
        }
        if (event.getName().equals("timer")) {
            event.reply("Under construction!").queue();
        }
    }
}
