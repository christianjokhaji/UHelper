package ca.unknown.bot.use_cases.schedule_reminder;

import ca.unknown.bot.data_access.schedule_reminder.ScheduledReminderDataAccessInterface;
import ca.unknown.bot.entities.schedule_reminder.ScheduledEvent;

import java.util.*;
import java.util.concurrent.*;

/**
 * A subordinate use case interactor which removes events from the user's schedule once they have passed.
 */
public class RemovePassedEventInteractor {
    /**
     * The current DAO in use by the program.
     */
    final ScheduledReminderDataAccessInterface scheduleDAO;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Class constructor
     * @param scheduleDAO the current data access object in use by the program
     */
    public RemovePassedEventInteractor(ScheduledReminderDataAccessInterface scheduleDAO){
        this.scheduleDAO = scheduleDAO;
    }

    /**
     * Executes a delayed action of removing an event from the user's schedule using ScheduledExecutorService.
     * @param user the discord username of the user whose event has passed
     * @param scheduledEvent the event which has passed
     */
    public void execute(String user, ScheduledEvent scheduledEvent){
        long currentDate = new Date().getTime();
        long eventDate = scheduledEvent.getEventDate().getTime();
        long delay = eventDate - currentDate;

        Runnable removeEvent = () -> this.remove(user, scheduledEvent);
        scheduler.schedule(removeEvent, delay, TimeUnit.MILLISECONDS);
    }

    private void remove(String user, ScheduledEvent s){
        if(scheduleDAO.getSchedule(user).hasEvent(s)){
            scheduleDAO.getSchedule(user).removePassedEvent(s);
        }
    }
}
