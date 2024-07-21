package ca.unknown.bot.use_cases.timer;

import ca.unknown.bot.entities.timer.Pomodoro;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
                event.reply(newTimer.toString()).queue();
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