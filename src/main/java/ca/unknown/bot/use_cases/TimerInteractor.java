package ca.unknown.bot.use_cases;

import ca.unknown.bot.data_access.TimerDAO;
import ca.unknown.bot.data_access.GSONTypeAdapter; // may be used or not in the future
import ca.unknown.bot.interface_interactor.TimerController;
import ca.unknown.bot.interface_interactor.TimerPresenter;
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
     * TimerInteractor allows a bot to create, initiate, and cancel a timer. Many of the methods
     * below involves getting information about the user who call a certain command.
     *
     * @param event represents a SlashCommandInteraction event.
     */

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("timer_create")) { // Slash command for creating a timer
            try {
                // the user called /timer_create on Discord
                User user = event.getUser(); // Who called /timer_create on Discord

                // timer's name
                String name = Objects.requireNonNull(event.getOption("name")).getAsString();

                // the duration of work period
                double workTime = Objects.requireNonNull(event.getOption("work")).getAsDouble();

                // the duration of break period
                double breakTime = Objects.requireNonNull(event.getOption("break")).getAsDouble();

                // One iteration of work-break routine is called an interval.
                Integer iteration = Objects.requireNonNull(event.getOption("iteration")).getAsInt();

                // Object needed to check duplicate timers
                TimerDAO timerDAO = new TimerDAO();

                // Prevents users from creating timers with impossible settings
                if (workTime < 0 || breakTime < 0 || iteration <= 0) {
                    event.reply("You can't make a timer with negative numbers! Try again " +
                            "with positive real numbers.").queue();
                } else if (timerDAO.checkDuplicate(name, user.toString())) {
                    event.reply("Duplicate names are not allowed for timer instances. " +
                                    "Try again with a different name.").queue();
                } else {
                    TimerController.convertTimerInput(name, workTime, breakTime, iteration, user);
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
        if (event.getName().equals("timer_list")) { // Command for loading a list of timers
            event.reply(TimerPresenter.getTimers(event.getUser())).queue();
        }
        if (event.getName().equals("timer_start")) { // Command for starting a timer
            event.reply("Coming soon!").queue();
        }
        if (event.getName().equals("timer_cancel")) { // Command for cancelling a timer
            event.reply("Coming soon!").queue();
        }
    }
}
