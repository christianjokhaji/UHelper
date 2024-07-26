package ca.unknown.bot.use_cases.timer;

import ca.unknown.bot.data_access.timer.TimerDAO;
import ca.unknown.bot.entities.timer.Pomodoro;
import ca.unknown.bot.interface_adapter.timer.TimerController;
import ca.unknown.bot.interface_adapter.timer.TimerPresenter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * A use-case interactor for timer and its features.
 */
public class TimerInteractor extends ListenerAdapter {

    /**
     * TimerViewModel allows a bot to create, initiate, and cancel a timer. Many of the methods
     * below involves getting information about the user who call a certain command.
     *
     * @param event represents a SlashCommandInteraction event.
     */

    private static Pomodoro timer;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("timer_create")) { // Slash command for creating a timer
            try {
                // the user who called /timer_create on Discord
                User user = event.getUser();

                // the new timer's name
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
            User user = event.getUser();
            User one = event.getOption("invitee1",null, OptionMapping::getAsUser);
            User two = event.getOption("invitee2", null, OptionMapping::getAsUser);
            User three = event.getOption("invitee3", null, OptionMapping::getAsUser);
            String name = Objects.requireNonNull(event.getOption("name")).getAsString();
            Pomodoro timer = TimerPresenter.fetchTimer(name, user);
            timer.addUser(user);

            if (one != null) {
                if (one.equals(user)) {
                    event.reply("You can't invite yourself!").queue();
                }
                else {timer.addUser(one);}
            }
            if (two != null) {
                if (two.equals(user)) {
                    event.reply("You can't invite yourself!").queue();
                }
                else {timer.addUser(two);}
            }
            if (three != null) {
                if (three.equals(user)) {
                    event.reply("You can't invite yourself!").queue();
                }
                else {timer.addUser(three);}
            }

            if (timer == null) {
                event.reply("The requested timer is not found.").queue();
            } else {
                this.timer = timer;
                event.reply(timer.getName() + " is starting... Check your DM.")
                        .addActionRow(Button.primary("cancel", "Cancel")).queue();
//                timer.startTimer();
            }
        }
    }


    public void onButtonInteraction(ButtonInteractionEvent event) {
       if (event.getComponentId().equals("cancel")) {
           timer.removeUser(event.getUser());
           timer = null;
           event.reply("Timer Cancelled!").queue();
       }
   }

   public void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue((channel) -> {
            channel.sendMessage(content).queueAfter(20, TimeUnit.MINUTES);
        });
    }
}
