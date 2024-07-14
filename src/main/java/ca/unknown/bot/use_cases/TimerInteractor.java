package ca.unknown.bot.use_cases;

import ca.unknown.bot.entities.Pomodoro;
import ca.unknown.bot.data_access.TimerDAO;
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
        // !preset {worktime} {breaktime} {iteration} {name}
        if (event.getName().equals("timer_create")) {
            try {
                String name = Objects.requireNonNull(event.getOption("name")).getAsString();
                double workTime = Objects.requireNonNull(event.getOption("work")).getAsDouble();
                double breakTime = Objects.requireNonNull(event.getOption("break")).getAsDouble();
                Integer iteration = Objects.requireNonNull(event.getOption("iteration")).getAsInt();
                Pomodoro newTimer = new Pomodoro(workTime, breakTime, iteration, name);

                TimerDAO timerDAO = new TimerDAO();
                User user = event.getUser();
                timerDAO.savePomodoro(newTimer, user,"timer_repository.json");

                event.reply("A timer preset has been created." + newTimer.toString()).queue();
            }
            catch (NumberFormatException e) {
                event.reply("Exception raised: NumberFormatException").queue();
            }
            catch (ArrayIndexOutOfBoundsException e) {
                event.reply("Exception raised: ArrayIndexOutOfBoundsException").queue();
            }
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
