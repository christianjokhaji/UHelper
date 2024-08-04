package ca.unknown.bot.use_cases.timer;

import ca.unknown.bot.interface_adapter.timer.TimerController;
import ca.unknown.bot.interface_adapter.timer.TimerPresenter;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Objects;

public class TimerInteractor extends ListenerAdapter {
    /**
     * TimerInteractor is a listener class that is responsible for gathering data from the view and view model
     * (the Discord client). Whenever a command was made on a Discord server, it gets an instance of
     * RestAction to interact with TimerController and TimerPresenter to deal with various requests.
     * Its methods mostly involves getting information about the user who call a certain
     * command.
     * Responsibility: listens to inputs and pass them to the appropriate classes
     *
     * @param event represents an event, regardless of how end users invoked them.
     */


    /**
     * onSlashCommandInteraction listens to the slash command interactions from
     * users and will process inputs as appropriate data types. Then, it will determine what method
     * of TimerController / TimerPresenter to use so that they can convert inputs accordingly.
     *
     * @param event represents an event, invoked by a slash command.
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("timer_create")) { // Slash command for creating a timer
            User user = event.getUser();
            String name = Objects.requireNonNull(event.getOption("name")).getAsString();
            double workTime = Objects.requireNonNull(event.getOption("work")).getAsDouble();
            double breakTime = Objects.requireNonNull(event.getOption("break")).getAsDouble();
            int iteration = Objects.requireNonNull(event.getOption("iteration")).getAsInt();

            // Prevents users from creating timers with impossible configurations
            if (workTime < 0 || breakTime < 0 || iteration <= 0) {
                TimerPresenter.sendReply(event, "You can't create a timer with negative" +
                        "numbers! Try again with positive real numbers.");}
            else {TimerController.convertCreateInput(name, workTime, breakTime, iteration, user, event);}
        }
        if (event.getName().equals("timer_delete")) { // Commanding a deleting a timer instance
            User user = event.getUser();
            String name = Objects.requireNonNull(event.getOption("name")).getAsString();
            TimerController.convertDeleteInput(name, user, event);
        }
        if (event.getName().equals("timer_list")) { // Command for loading a list of timers
            // Invokes TimerPresenter to reply with a list of timers, given the caller and event
            TimerPresenter.getTimers(event.getUser(), event);
        }
        if (event.getName().equals("timer_start")) {// Command for starting a timer
            User user = event.getUser();
            String timerName = Objects.requireNonNull(event.getOption("name")).getAsString();

            // The users who also want to be notified by the same timer; up to three people.
            User one = event.getOption("invitee1", null, OptionMapping::getAsUser);
            User two = event.getOption("invitee2", null, OptionMapping::getAsUser);
            User three = event.getOption("invitee3", null, OptionMapping::getAsUser);

            // Invokes TimerController to convert inputs
            TimerController.convertStartInput(timerName, user, one, two, three, event);
        }
        if (event.getName().equals("timer_cancel")) {
            User user = event.getUser();
            String timerName = Objects.requireNonNull(event.getOption("name")).getAsString();

            // Invokes TimerController to process inputs appropriately to the purpose of cancelling
            TimerController.convertCancelInput(timerName, user, event);
        }
    }

    /**
     * onButtonInteraction is similar to onSlashCommandInteraction, except for the fact that this
     * deals with the button interaction of Discord.
     *
     * @param event represents an event, invoked by a button.
     */
    public void onButtonInteraction(ButtonInteractionEvent event) {
       if (event.getComponentId().equals("list")) {
           // Invokes TimerPresenter to prepare a list of timers created by this particular user
           TimerPresenter.getTimersButton(event.getUser(), event);
       }
   }
}
