package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;

import java.util.*;

import java.util.concurrent.TimeUnit;

/**
 * A subordinate use case interactor which sends the user a private message reminder of their event, following
 * a specified time delay.
 */
public class SendReminderInteractor {
    /**
     * The current DAO in use by the program.
     */
    ScheduledReminderDataAccessInterface scheduleDAO;

    /**
     * Class constructor
     * @param scheduleDAO the current data access object in use by the program
     */
    public SendReminderInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        this.scheduleDAO = scheduleDAO;
    }

    /**
     * Calculates the delay associated with sending a direct message reminder alert for a user scheduled event.
     * @param user the user of this interaction
     * @param scheduledEvent the event that a reminder must be scheduled for
     */

    public void execute(User user, ScheduledEvent scheduledEvent){
        String content = scheduledEvent.reminderAlert();
        long currentDate = new Date().getTime();
        long eventDate = scheduledEvent.getEventDate().getTime();
        long delay;

        // delay is measured by the following principles:
        // A - if the scheduled event is more than 24 hrs away from today, then queue the reminder alert for 24 hrs
        // before the event
        // B - if the scheduled event is exactly 24 hrs away from the current time, then send a reminder alert now
        //   - if the scheduled event is less than an hour away, then send a reminder alert now
        // C - if the scheduled event is less than 24 hrs away from today but is still more than an hour away, only
        // send the reminder alert an hour before the event

        if(eventDate - currentDate > 86400000L){
            delay = (eventDate - 86400000L) - currentDate;
        }
        else if(eventDate - currentDate == 86400000L || eventDate - currentDate < 3600000L){
            delay = 0;
        }
        else{
            delay = (eventDate - 3600000L) - currentDate;
        }

        this.sendPrivateMessage(user, content, delay, scheduledEvent.getEventName());
    }

    /**
     * Queues a direct message to be sent to the user after a specified delay.
     * @param user the user of this interaction
     * @param content the reminder alert which is being messaged to the user
     * @param delay the reminder delay measured in milliseconds
     */
    private void sendPrivateMessage(User user, String content, long delay, String event){
        user.openPrivateChannel().queueAfter(delay, TimeUnit.MILLISECONDS,
                (channel) -> channel.sendMessage(content).setCheck(() -> scheduleDAO.getChecks(user.getName(), event)).queue());
    }
}
