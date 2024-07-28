package ca.unknown.bot.interface_adapter.timer;

import ca.unknown.bot.entities.timer.Pomodoro;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A use-case interactor class for the features needed for the timer feature
 */
public class TimerListener extends ListenerAdapter {

    /**
     * TimerInteractor is a class that is responsible for gathering data from the view and view model
     * (the Discord client) Whenever a command was made on a Discord server, it gets an instance of
     * RestAction to interact with TimerController and TimerPresenter to deal with various requests.
     * Many of the methods below involves getting information about the user who call a certain
     * command.
     * As of July 27, timer only uses two types of RestAction(event): SlashCommandInteractionEvent
     * or ButtonInteractionEvent.
     *
     * @param event represents an event, regardless of how end users invoked them.
     */

    private static Pomodoro timer;
    private static ArrayList<Pomodoro> timers;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("timer_create")) { // Slash command for creating a timer
            // This part of the entire if-branch organizes the user inputs about creating a timer
            // and invokes TimerController to create an appropriate data type.

            // the literal user who called /timer_create on Discord
            User user = event.getUser();

            // the new timer's name
            String name = Objects.requireNonNull(event.getOption("name")).getAsString();

            // the duration of work period
            double workTime = Objects.requireNonNull(event.getOption("work")).getAsDouble();

            // the duration of break period
            double breakTime = Objects.requireNonNull(event.getOption("break")).getAsDouble();

            // One iteration of work-break routine is called an interval (or cycle).
            int iteration = Objects.requireNonNull(event.getOption("iteration")).getAsInt();

            // Prevents users from creating timers with impossible configurations
            if (workTime < 0 || breakTime < 0 || iteration <= 0) {
                event.reply("You can't create a timer with negative numbers! Try again " +
                        "with positive real numbers.").queue();}

            TimerController.convertTimerInput(name, workTime, breakTime, iteration, user, event);
        }
        if (event.getName().equals("timer_delete")) {
            User user = event.getUser();
            String name = Objects.requireNonNull(event.getOption("name")).getAsString();
            TimerController.passDeleteInfo(name, user, event);
        }
        if (event.getName().equals("timer_list")) { // Command for loading a list of timers
            TimerPresenter.getTimers(event.getUser(), event);
        }
        if (event.getName().equals("timer_start")) {// Command for starting a timer
            if (timer != null) {
                event.reply("Timer is already running! Press Cancel before starting a new one.")
                        .queue();
            } else {
                User user = event.getUser();
                User one = event.getOption("invitee1", null, OptionMapping::getAsUser);
                User two = event.getOption("invitee2", null, OptionMapping::getAsUser);
                User three = event.getOption("invitee3", null, OptionMapping::getAsUser);
                String name = Objects.requireNonNull(event.getOption("name")).getAsString();
                Pomodoro timer = TimerPresenter.fetchTimer(name, user);
                timer.addUser(user);

                if (one != null) {
                    if (one.equals(user)) {
                        event.reply("You can't invite yourself!").queue();
                    } else {
                        timer.addUser(one);
                    }
                }
                if (two != null) {
                    if (two.equals(user)) {
                        event.reply("You can't invite yourself!").queue();
                    } else {
                        timer.addUser(two);
                    }
                }
                if (three != null) {
                    if (three.equals(user)) {
                        event.reply("You can't invite yourself!").queue();
                    } else {
                        timer.addUser(three);
                    }
                }

                if (timer == null) {
                    event.reply("The requested timer is not found.").queue();}
                else {
                    this.timer = timer;
                    event.reply(timer.getName() + " started. You will be notified through" +
                                    " a private channel.")
                            .addActionRow(Button.primary("cancel", "Cancel")).queue();
                    timer.startTimer();
                }
            }
        }
    }

    public void onButtonInteraction(ButtonInteractionEvent event) {
       if (event.getComponentId().equals("cancel")) {
           if (timer == null) {
               event.reply("There is no ongoing timer!").queue();
           } else {
               timer.removeUser(event.getUser());
               event.reply("You have successfully unsubscribed to " + timer.getName() +
                   " and you will not be notified.").queue();
               timer = null;
               }
           }
       if (event.getComponentId().equals("list")) {
           TimerPresenter.getTimersButton(event.getUser(), event);
       }
   }
}
