package ca.unknown.bot.use_cases;

import ca.unknown.bot.entities.Pomodoro;
import ca.unknown.bot.data_access.TimerDAO;
import ca.unknown.bot.data_access.GSONTypeAdapter; // may be used or not in the future
import ca.unknown.bot.interface_interactor.TimerController;
import net.dv8tion.jda.api.entities.User;
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

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        // /timer_create {workTime} {breakTime} {iteration} {name}
        if (event.getName().equals("timer_create")) {
            try {
                User user = event.getUser();
                String name = Objects.requireNonNull(event.getOption("name")).getAsString();
                double workTime = Objects.requireNonNull(event.getOption("work")).getAsDouble();
                double breakTime = Objects.requireNonNull(event.getOption("break")).getAsDouble();
                Integer iteration = Objects.requireNonNull(event.getOption("iteration")).getAsInt();

                if (TimerController.checkDuplicateTimer(name, user)) {
                    event.reply("Duplicate names are not allowed for timer instances.")
                            .queue();
                } else {
                    TimerController.saveTimer(name, workTime, breakTime, iteration, user);
                    event.reply("A timer preset has been created. \"" + name +
                        "\" will repeat " + workTime + " minutes of work and " + breakTime
                + " minutes of break " + iteration + " times.").queue();
                }
            }
            catch (NumberFormatException e) {
                event.reply("Exception raised: NumberFormatException").queue();
            }
            catch (ArrayIndexOutOfBoundsException e) {
                event.reply("Exception raised: ArrayIndexOutOfBoundsException").queue();
            }
        }
        if (event.getName().equals("timer_list")) {

        }
        if (event.getName().equals("timer_start")) {
            event.reply("Timer " + Objects.requireNonNull(event.getOption("name")) +
                    " has started.").queue();
        }
        if (event.getName().equals("timer_cancel")) {
            event.reply("The current timer has been successfully cancelled.").queue();
        }
    }
}
